package com.kimboofactory.iconvert.persistence.local;

import android.util.Log;

import com.aleengo.peach.toolbox.commons.factory.Singleton;
import com.aleengo.peach.toolbox.commons.model.Response;
import com.kimboofactory.iconvert.domain.model.CurrencyEntity;
import com.kimboofactory.iconvert.persistence.CurrencyDataSource;
import com.kimboofactory.iconvert.persistence.model.CurrencyData;
import com.kimboofactory.iconvert.persistence.model.RateData;
import com.kimboofactory.iconvert.util.AppExecutors;
import com.kimboofactory.iconvert.util.NamedCallable;
import com.kimboofactory.iconvert.util.NamedRunnable;

import java.util.List;
import java.util.concurrent.Callable;

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
        final Runnable task = () -> {
            Log.d(TAG, "LocalDataSource.getCurrencyByCode " + Thread.currentThread().getName());
            CurrencyEntity currency = dao.getCurrencyByCode(code);
            callback.currencyLoaded(new Response(currency, null));
        };
        final NamedRunnable runnable = new NamedRunnable("LocalDataSource.getCurrencyByCode") {
            @Override
            protected void execute() {
                CurrencyEntity currency = dao.getCurrencyByCode(code);
                callback.currencyLoaded(new Response(currency, null));
            }
        };
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getCurrencies(GetCurrenciesCallback callback) {
        final Runnable command = () -> {
            Log.d(TAG, "LocalDataSource.loadCurrencies " + Thread.currentThread().getName());
            final List<CurrencyEntity> currencies = dao.getAllCurrencies();
            callback.currenciesLoaded(new Response(currencies, null));
        };

        appExecutors.diskIO().execute(command);
    }

    public boolean isEmpty() {
        final String BASE_CODE = "USD";
        final Callable<Boolean> command = () -> {
            Log.d(TAG, "LocalDataSource.isEmpty " + Thread.currentThread().getName());
            return dao.getCurrencyByCode(BASE_CODE) == null;
        };

        final NamedCallable<Boolean> callable = new NamedCallable<Boolean>("LocalDataSource.isEmpty") {
            @Override
            protected Boolean execute() {
                return dao.getCurrencyByCode(BASE_CODE) == null;
            }
        };
        return appExecutors.diskIO().execute(callable);
    }

    public void saveAllCurrencies(List<CurrencyData> currencies) {
        if (currencies != null && currencies.size() > 0) {
            final Runnable command = () -> {
                Log.d(TAG, "LocalDataSource.saveAllCurrencies " + Thread.currentThread().getName());
                dao.saveAllCurrencies(currencies);
            };
            appExecutors.diskIO().execute(command);
        }
    }

    public void saveAllRates(List<RateData> rates) {
        if (rates != null && rates.size() > 0) {
            final Runnable command = () -> {
                Log.d(TAG, "LocalDataSource.saveAllRates " + Thread.currentThread().getName());
                dao.saveAllRates(rates);
            };
            appExecutors.diskIO().execute(command);
        }
    }
}
