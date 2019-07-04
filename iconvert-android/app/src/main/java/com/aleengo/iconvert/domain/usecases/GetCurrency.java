package com.aleengo.iconvert.domain.usecases;

import com.aleengo.peach.toolbox.commons.model.Result;
import com.aleengo.iconvert.domain.Repository;
import com.aleengo.iconvert.domain.UseCase;
import com.aleengo.iconvert.domain.common.QueryValue;
import com.aleengo.iconvert.domain.model.CurrencyEntity;

import javax.inject.Inject;

/**
 * Created by CK_ALEENGO on 27/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class GetCurrency extends UseCase<QueryValue> {

    @Inject
    public GetCurrency(Repository repository) {
        super(repository);
    }

    @Override
    protected void executeUseCase() {
        final String query = (String) getQueryValue().getValue();
        getRepository().getCurrency(query, response -> {

            if (response.getError() != null) {
                getUseCaseCallback().onResult(new Result(null, response.getError()));
                return;
            }

            final CurrencyEntity currencyEntity = (CurrencyEntity) response.getValue();
            getUseCaseCallback().onResult(new Result(currencyEntity, response.getError()));
        });
    }
}
