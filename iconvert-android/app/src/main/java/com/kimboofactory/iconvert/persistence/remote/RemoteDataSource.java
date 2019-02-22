package com.kimboofactory.iconvert.persistence.remote;

import com.kimboofactory.iconvert.persistence.DataSource;
import com.kimboofactory.iconvert.persistence.api.OpenXchangeRateAPI;
import com.kimboofactory.iconvert.util.SingletonUtil;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class RemoteDataSource implements DataSource {

    private static RemoteDataSource instance;

    private RemoteDataSource() {
    }

    public static RemoteDataSource getInstance() {
        instance = SingletonUtil.getInstance(RemoteDataSource.class, instance);
        return instance;
    }

    @Override
    public void getCurrencyByCode(String code, GetCurrencyCallback callback) {

    }

    @Override
    public void getCurrencies(GetCurrenciesCallback callback) {
        OpenXchangeRateAPI.getInstance().getCurrencies(callback::currenciesLoaded);
    }
}
