package com.kimboofactory.iconvert.domain.usecases;

import com.aleengo.peach.toolbox.commons.model.Response;
import com.aleengo.peach.toolbox.commons.model.Result;
import com.kimboofactory.iconvert.domain.Repository;
import com.kimboofactory.iconvert.domain.UseCase;
import com.kimboofactory.iconvert.domain.common.QueryValue;
import com.kimboofactory.iconvert.domain.model.CurrencyEntity;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class GetCurrencies extends UseCase<QueryValue> {

    @Inject
    public GetCurrencies(Repository repository) {
        super(repository);
    }

    @Override
    protected void executeUseCase() {
        getRepository().getCurrencies(this::onDataLoaded);
    }

    private void onDataLoaded(Response response) {
        if (response.getError() != null) {
            getUseCaseCallback().onResult(new Result(null, response.getError()));
            return;
        }
        final List<CurrencyEntity> data = (List<CurrencyEntity>) response.getValue();
        getUseCaseCallback().onResult(new Result(data, null));
    }
}
