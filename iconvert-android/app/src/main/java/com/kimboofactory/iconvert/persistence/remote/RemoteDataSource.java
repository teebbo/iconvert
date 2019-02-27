package com.kimboofactory.iconvert.persistence.remote;

import com.aleengo.peach.toolbox.commons.common.NamedRunnable;
import com.aleengo.peach.toolbox.commons.factory.Singleton;
import com.aleengo.peach.toolbox.commons.util.AppExecutors;
import com.kimboofactory.iconvert.persistence.CurrencyDataSource;
import com.kimboofactory.iconvert.persistence.api.API;
import com.kimboofactory.iconvert.persistence.api.OpenXchangeRateAPI;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class RemoteDataSource implements CurrencyDataSource {

    @Getter @Setter
    private API api;
    @Getter @Setter
    private AppExecutors appExecutors;

    private static RemoteDataSource instance;

    private RemoteDataSource() {
    }

    public static RemoteDataSource getInstance(AppExecutors appExecutors, API api) {
        if (instance == null) {
            instance = Singleton.of(RemoteDataSource.class, instance);
            instance.setApi(api);
            instance.setAppExecutors(appExecutors);
        }
        return instance;
    }
    @Override
    public void getCurrencyByCode(String code, GetCurrencyCallback callback) {

    }

    @Override
    public void getCurrencies(GetCurrenciesCallback callback) {
        final NamedRunnable command = new NamedRunnable("%s.%s", "RemoteDataSource", "getCurrencies") {
            @Override
            protected void execute() {
                ((OpenXchangeRateAPI) api).getCurrencies(callback::onCurrenciesReceived);
            }
        };
        appExecutors.networkIO().execute(command);
    }

    public void getRatesAndCurrencies(CurrencyDataSource.GetCallback callback) {
        final NamedRunnable command = new NamedRunnable("%s.%s", "RemoteDataSource", "getRatesAndCurrencies") {
            @Override
            protected void execute() {
                ((OpenXchangeRateAPI) api).getRatesAndCurrencies(callback::onReceived);
            }
        };
        appExecutors.networkIO().execute(command);
    }
}
