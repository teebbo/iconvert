package com.kimboofactory.iconvert.persistence.remote;

import com.aleengo.peach.toolbox.commons.factory.Singleton;
import com.kimboofactory.iconvert.persistence.CurrencyDataSource;
import com.kimboofactory.iconvert.persistence.api.OpenXchangeRateAPI;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class RemoteDataSource implements CurrencyDataSource {

    private static RemoteDataSource instance;

    private RemoteDataSource() {
    }

    @Override
    public void getCurrencyByCode(String code, GetCurrencyCallback callback) {

    }

    @Override
    public void getCurrencies(GetCurrenciesCallback callback) {
        Singleton.of(OpenXchangeRateAPI.class).getCurrencies(callback::currenciesLoaded);
    }
}
