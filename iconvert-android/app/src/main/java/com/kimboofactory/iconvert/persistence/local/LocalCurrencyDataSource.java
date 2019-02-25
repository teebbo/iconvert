package com.kimboofactory.iconvert.persistence.local;

import android.util.Log;

import com.aleengo.peach.toolbox.commons.factory.Singleton;
import com.aleengo.peach.toolbox.commons.model.Response;
import com.kimboofactory.iconvert.persistence.CurrencyDataSource;
import com.kimboofactory.iconvert.persistence.model.CurrencyData;
import com.kimboofactory.iconvert.persistence.model.RateData;
import com.kimboofactory.iconvert.util.AppExecutors;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class LocalCurrencyDataSource implements CurrencyDataSource {

    public static final String TAG = LocalCurrencyDataSource.class.getSimpleName();

    private static LocalCurrencyDataSource instance;

    @Getter @Setter
    private IConvertDAO dao;
    @Getter @Setter
    private AppExecutors appExecutors;

    private LocalCurrencyDataSource() {}

    public static LocalCurrencyDataSource getInstance(AppExecutors appExecutors, IConvertDAO dao) {
        if (instance == null) {
            instance = Singleton.of(LocalCurrencyDataSource.class, instance);
            instance.setAppExecutors(appExecutors);
            instance.setDao(dao);
        }
        return instance;
    }

    @Override
    public void getCurrencyByCode(String code, GetCurrencyCallback callback) {
        Log.d(TAG, "LocalDataSource.getCurrencyByCode " + Thread.currentThread().getName());

        final Runnable task = () -> {
            Log.d(TAG, "LocalDataSource.runnable " + Thread.currentThread().getName());
            CurrencyData currency = dao.getCurrencyByCode(code);
            callback.currencyLoaded(new Response(currency, null));
        };
        appExecutors.diskIO().execute(task);
    }

    @Override
    public void getCurrencies(GetCurrenciesCallback callback) {
        Log.d(TAG, "LocalDataSource.loadCurrencies " + Thread.currentThread().getName());
        final Runnable command = () -> {
            final List<CurrencyData> currencies = dao.getAllCurrencies();
            callback.currenciesLoaded(new Response(currencies, null));
        };
        appExecutors.diskIO().execute(command);
    }

    public boolean isEmpty() {
        Log.d(TAG, "LocalDataSource.isEmpty " + Thread.currentThread().getName());
        final String USD_CODE = "USD";
        return dao.getCurrencyByCode(USD_CODE) == null;
    }

    public void saveAllCurrencies(List<CurrencyData> currencies) {
        Log.d(TAG, "LocalDataSource.saveAllCurrencies " + Thread.currentThread().getName());
        if (currencies != null && currencies.size() > 0) {
            final Runnable command = () -> dao.saveAllCurrencies(currencies);
            appExecutors.diskIO().execute(command);
        }
    }

    public void saveAllRates(List<RateData> rates) {
        Log.d(TAG, "LocalDataSource.saveAllRates " + Thread.currentThread().getName());
        if (rates != null && rates.size() > 0) {
            final Runnable command = () -> dao.saveAllRates(rates);
            appExecutors.diskIO().execute(command);
        }
    }
}
