package com.kimboofactory.iconvert.persistence.remote;

import com.aleengo.peach.toolbox.commons.factory.Singleton;
import com.kimboofactory.iconvert.dto.ResponseDTO;
import com.kimboofactory.iconvert.persistence.CurrencyDataSource;
import com.kimboofactory.iconvert.persistence.api.API;
import com.kimboofactory.iconvert.persistence.api.OpenXchangeRateAPI;
import com.kimboofactory.iconvert.util.AppExecutors;

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
        final Runnable command = () -> ((OpenXchangeRateAPI) api)
                .getCurrencies(callback::currenciesLoaded);
        appExecutors.networkIO().execute(command);
    }

    public void getRatesAndCurrencies(CurrencyDataSource.GetCallback callback) {
        final Runnable currenciesCommand = () -> ((OpenXchangeRateAPI) api)
                .getCurrencies(response ->
                        callback.onFinished(new ResponseDTO("CURRENCIES", response)));

        final Runnable rateCommand = () -> ((OpenXchangeRateAPI) api)
                .getRates(response ->
                        callback.onFinished(new ResponseDTO("RATES", response)));

        appExecutors.networkIO().execute(currenciesCommand);
        appExecutors.networkIO().execute(rateCommand);
    }
}
