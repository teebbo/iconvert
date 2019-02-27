package com.kimboofactory.iconvert.domain.usecases;

import com.aleengo.peach.toolbox.commons.model.Result;
import com.kimboofactory.iconvert.domain.Repository;
import com.kimboofactory.iconvert.domain.UseCase;
import com.kimboofactory.iconvert.domain.common.QueryValue;
import com.kimboofactory.iconvert.domain.model.CurrencyEntity;

/**
 * Created by CK_ALEENGO on 27/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class GetCurrency extends UseCase<QueryValue> {

    public GetCurrency(Repository repository) {
        super(repository);
    }

    @Override
    protected void executeUseCase() {
        final String query = getQueryValue().getValue();
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
