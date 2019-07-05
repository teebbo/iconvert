package com.aleengo.iconvert.domain.usecases;

import com.aleengo.iconvert.domain.Repository;
import com.aleengo.iconvert.domain.UseCase;
import com.aleengo.iconvert.domain.common.QueryValue;

import javax.inject.Inject;

/**
 * Created by CK_ALEENGO on 25/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class GetRatesAndCurrencies extends UseCase<GetRatesAndCurrencies.QueryVal> {

    @Inject
    public GetRatesAndCurrencies(Repository repository) {
        super(repository);
    }

    @Override
    protected void executeUseCase() {
        final QueryVal queryValue = getQueryValue();
        if(queryValue.isForceUpdate()) {
            getRepository().loadRatesAndCurrencies();
        }
    }

    public static class QueryVal extends QueryValue {

        private boolean forceUpdate;
        public QueryVal(boolean forceUpdate, String value) {
            super(value);
            this.forceUpdate = forceUpdate;
        }

        public boolean isForceUpdate() {
            return forceUpdate;
        }

        public void setForceUpdate(boolean forceUpdate) {
            this.forceUpdate = forceUpdate;
        }
    }
}
