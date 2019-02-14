package com.kimboofactory.iconvert.persistence.remote;

import com.kimboofactory.iconvert.persistence.DataSource;
import com.kimboofactory.iconvert.persistence.api.OpenXchangeRateAPI;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RemoteDataSource implements DataSource {

    private OpenXchangeRateAPI oxr;

    public RemoteDataSource(OpenXchangeRateAPI oxr) {
        this.oxr = oxr;
    }

    @Override
    public void getCurrencyByCode(String code, GetCurrencyCallback callback) {

    }

    @Override
    public void getCurrencies(GetCurrenciesCallback callback) {
        oxr.getCurrencies(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String body = response.body().string();
                callback.currenciesLoaded();
            }
        });
    }
}
