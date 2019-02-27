package com.kimboofactory.iconvert.persistence.local;

import com.aleengo.peach.toolbox.commons.common.NamedCallable;
import com.aleengo.peach.toolbox.commons.common.NamedRunnable;
import com.aleengo.peach.toolbox.commons.factory.Singleton;
import com.aleengo.peach.toolbox.commons.model.Response;
import com.aleengo.peach.toolbox.commons.util.AppExecutors;
import com.kimboofactory.iconvert.domain.model.CurrencyEntity;
import com.kimboofactory.iconvert.persistence.CurrencyDataSource;
import com.kimboofactory.iconvert.persistence.model.CurrencyData;
import com.kimboofactory.iconvert.persistence.model.RateData;

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
    public void getCurrency(String query, GetCurrencyCallback callback) {
        final NamedRunnable command = new NamedRunnable("%s.%s", "LocalDataSource", "getCurrency") {
            @Override
            protected void execute() {
                final CurrencyEntity currency = dao.getCurrency(query);
                callback.onReceived(new Response(currency, null));
            }
        };
        appExecutors.diskIO().execute(command);
    }

    @Override
    public void getCurrencies(GetCurrenciesCallback callback) {
        final NamedRunnable command = new NamedRunnable("%s", "LocalDataSource.loadCurrencies") {
            @Override
            protected void execute() {
                final List<CurrencyEntity> currencies = dao.getAllCurrencies();
                callback.onCurrenciesReceived(new Response(currencies, null));
            }
        };
        appExecutors.diskIO().execute(command);
    }

    public boolean isEmpty() {
        final String BASE_CODE = "USD";
        final NamedCallable<Boolean> callable = new NamedCallable<Boolean>("%s", "LocalDataSource.isEmpty") {
            @Override
            protected Boolean execute() {
                return dao.getCurrency(BASE_CODE) == null;
            }
        };
        return appExecutors.diskIO().execute(callable);
    }

    public void saveAllCurrencies(List<CurrencyData> currencies) {
        if (currencies != null && currencies.size() > 0) {
            final NamedRunnable command = new NamedRunnable("%s", "LocalDataSource.saveAllCurrencies") {
                @Override
                protected void execute() {
                    dao.saveAllCurrencies(currencies);
                }
            };
            appExecutors.diskIO().execute(command);
        }
    }

    public void saveAllRates(List<RateData> rates) {
        if (rates != null && rates.size() > 0) {
            final NamedRunnable command = new NamedRunnable("%s", "LocalDataSource.saveAllRates") {
                @Override
                protected void execute() {
                    dao.saveAllRates(rates);
                }
            };
            appExecutors.diskIO().execute(command);
        }
    }
}
