package com.kimboofactory.iconvert.domain.usecases;

import com.aleengo.peach.toolbox.commons.model.RawJSON;
import com.aleengo.peach.toolbox.commons.model.Response;
import com.aleengo.peach.toolbox.commons.model.Result;
import com.aleengo.peach.toolbox.commons.strategy.RawJSONDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.kimboofactory.iconvert.domain.UseCase;
import com.kimboofactory.iconvert.domain.common.QueryValue;
import com.kimboofactory.iconvert.dto.CurrencyIHM;

import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class GetRate extends UseCase<QueryValue> {

    public GetRate() {
    }

    private void onDataLoaded(Response response) {

        if (response.getError() != null) {
            getUseCaseCallback().onResult(new Result<String>(null, response.getError()));
            return;
        }

        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(RawJSON.class, new RawJSONDeserializer())
                .create();

        final String json = (String) response.getValue();
        final RawJSON rawData = gson.fromJson(new JsonReader(new StringReader(json)),
                RawJSON.class);

        final List<CurrencyIHM> list = rawData.getItems().entrySet()
                .stream()
                .map(entrySet -> new CurrencyIHM(entrySet.getKey(), entrySet.getValue(), false))
                .collect(Collectors.toList());

        getUseCaseCallback().onResult(new Result(list, null));
    }

    @Override
    protected void executeUseCase() {
        final String query = getQueryValue().getValue();
        getRepository().find(query, this::onDataLoaded);
    }
}
