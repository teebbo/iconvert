package com.kimboofactory.iconvert.persistence.repository;

import android.util.Log;

import com.kimboofactory.iconvert.domain.Repository;
import com.kimboofactory.iconvert.persistence.local.LocalCurrencyDataSource;
import com.kimboofactory.iconvert.persistence.model.CurrencyData;
import com.kimboofactory.iconvert.persistence.remote.RemoteDataSource;
import com.kimboofactory.iconvert.util.Mapper;

import java.util.ArrayList;
import java.util.List;

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
      if (localDataSource.isEmpty()) {
          Log.d(TAG, "CurrencyRepository.search " + Thread.currentThread().getName());
          remoteDataSource.getCurrencies(response -> {
              Log.d(TAG, "CurrencyRepository.search " + Thread.currentThread().getName());
              callback.onDataLoaded(response);
              if (response.getError() == null) {
                  localDataSource.saveAllCurrencies(new ArrayList<>(0));
              }
          });
      } else {
          Log.d(TAG, "CurrencyRepository.search " + Thread.currentThread().getName());
          localDataSource.getCurrencies(callback::onDataLoaded);
      }
    }

    @Override
    public void addAll(List<CurrencyData> currencies) {
        localDataSource.saveAllCurrencies(currencies);
    }

    @Override
    public void getCurrencies(GetCallback callback) {

    }

    @Override
    public void getRates(GetCallback callback) {

    }

    @Override
    public void addRatesAndCurrencies() {
        final Mapper mapper = new Mapper();
        remoteDataSource.getRatesAndCurrencies(responseDTO -> {
            if ("CURRENCIES".equals(responseDTO.getRequestId())) {
                localDataSource.saveAllCurrencies(mapper.map2Currencies(responseDTO.getResponse()));
            } else if ("RATES".equals(responseDTO.getRequestId())) {
                localDataSource.saveAllRates(mapper.map2Rates(responseDTO.getResponse()));
            }
        });
    }
}
