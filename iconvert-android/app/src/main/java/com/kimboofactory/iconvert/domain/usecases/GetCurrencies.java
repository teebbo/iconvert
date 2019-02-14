package com.kimboofactory.iconvert.domain.usecases;

import com.kimboofactory.iconvert.domain.UseCase;
import com.kimboofactory.iconvert.domain.common.QueryValue;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.dto.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class GetCurrencies extends UseCase<QueryValue, Result> {

    public GetCurrencies() {
    }

    @Override
    protected void executeUseCase() {
        final String query = getQueryValue() == null ?
                null : getQueryValue().getValue();
        getRepository().find(query, this::onDataLoaded);
    }

    private void onDataLoaded(Result<String> result) {
        if (result.getError().isPresent()) {
            getUsecaseCallback().onResult(result);
            return;
        }

        try {
            final List<CurrencyIHM> items = new LinkedList<>();
            final JSONObject json = new JSONObject(result.getValue().get());

            json.keys().forEachRemaining(s -> {
                try {
                    final String code = s;
                    final String libelle = json.getString(s);
                    items.add(new CurrencyIHM(code, libelle, false));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            });

            final Result<List<CurrencyIHM>> newResult =
                    new Result<>(result.getError(), Optional.of(items));
            getUsecaseCallback().onResult(newResult);

        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
