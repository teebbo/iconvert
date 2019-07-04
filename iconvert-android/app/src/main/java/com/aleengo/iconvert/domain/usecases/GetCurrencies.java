package com.aleengo.iconvert.domain.usecases;

import com.aleengo.peach.toolbox.commons.model.Response;
import com.aleengo.peach.toolbox.commons.model.Result;
import com.aleengo.iconvert.domain.Repository;
import com.aleengo.iconvert.domain.UseCase;
import com.aleengo.iconvert.domain.common.QueryValue;
import com.aleengo.iconvert.domain.model.CurrencyEntity;

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
