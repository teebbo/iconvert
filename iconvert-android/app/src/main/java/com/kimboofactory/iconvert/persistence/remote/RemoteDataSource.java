package com.kimboofactory.iconvert.persistence.remote;

import com.kimboofactory.iconvert.persistence.DataSource;
import com.kimboofactory.iconvert.persistence.api.OpenXchangeRateAPI;
import com.kimboofactory.iconvert.util.SingletonUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
        OpenXchangeRateAPI.getInstance().getCurrencies(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.currenciesLoaded(e.getMessage(), null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String body = response.body().string();
                callback.currenciesLoaded(null, body);
            }
        });
    }
}
