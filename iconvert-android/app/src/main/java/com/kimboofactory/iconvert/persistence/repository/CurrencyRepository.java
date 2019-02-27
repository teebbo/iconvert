package com.kimboofactory.iconvert.persistence.repository;

import com.kimboofactory.iconvert.domain.Repository;
import com.kimboofactory.iconvert.persistence.api.OpenXchangeRateAPI;
import com.kimboofactory.iconvert.persistence.local.LocalCurrencyDataSource;
import com.kimboofactory.iconvert.persistence.model.CurrencyData;
import com.kimboofactory.iconvert.persistence.remote.RemoteDataSource;
import com.kimboofactory.iconvert.util.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class CurrencyRepository implements Repository {

    public static final String TAG = "CurrencyRepository";

    private LocalCurrencyDataSource localDataSource;
    private RemoteDataSource remoteDataSource;

    public CurrencyRepository(LocalCurrencyDataSource localDataSource, RemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void search(final String query, SearchCallback callback) {
    }

    @Override
    public void addAll(List<CurrencyData> currencies) {
        localDataSource.saveAllCurrencies(currencies);
    }

    @Override
    public void getCurrencies(GetCallback callback) {
        if (localDataSource.isEmpty()) {
            remoteDataSource.getCurrencies(response -> {
                callback.onFinished(response);
                if (response.getError() == null) {
                    localDataSource.saveAllCurrencies(new ArrayList<>(0));
                }
            });
        } else {
            localDataSource.getCurrencies(callback::onFinished);
        }
    }

    @Override
    public void getRates(GetCallback callback) {

    }

    @Override
    public void addRatesAndCurrencies() {
        if (localDataSource.isEmpty()) {

            final Mapper mapper = new Mapper();

            remoteDataSource.getRatesAndCurrencies(response -> {

                if (response.getError() != null) {
                    throw new RuntimeException(response.getError());
                }

                final Map<String, String> map = (Map<String, String>) response.getValue();
                map.keySet().forEach(key -> {
                    if (OpenXchangeRateAPI.REQUEST_CURRENCY.equals(key)) {
                        localDataSource.saveAllCurrencies(mapper.map2Currencies(map.get(key)));
                    } else if (OpenXchangeRateAPI.REQUEST_RATE.equals(key)) {
                        localDataSource.saveAllRates(mapper.map2Rates(map.get(key)));
                    }
                });
            });
        }
    }
}
