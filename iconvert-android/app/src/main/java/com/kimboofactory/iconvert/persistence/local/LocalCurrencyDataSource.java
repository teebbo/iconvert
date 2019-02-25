package com.kimboofactory.iconvert.persistence.local;

import android.util.Log;

import com.aleengo.peach.toolbox.commons.model.Response;
import com.kimboofactory.iconvert.persistence.CurrencyDataSource;
import com.kimboofactory.iconvert.persistence.model.CurrencyData;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class LocalCurrencyDataSource implements CurrencyDataSource {

    public static final String TAG = LocalCurrencyDataSource.class.getSimpleName();

    @Getter @Setter
    private CurrencyDAO currencyDAO;
    @Getter @Setter
    private FavoriteDAO favoriteDAO;

    public LocalCurrencyDataSource() {
    }

    @Override
    public void getCurrencyByCode(String code, GetCurrencyCallback callback) {
        Log.d(TAG, "LocalDataSource.getCurrencyByCode " + Thread.currentThread().getName());
        CurrencyData currency = currencyDAO.getCurrencyByCode(code);
        callback.currencyLoaded(new Response(currency, null));
    }

    @Override
    public void getCurrencies(GetCurrenciesCallback callback) {
        Log.d(TAG, "LocalDataSource.loadCurrencies " + Thread.currentThread().getName());
        final List<CurrencyData> currencies = currencyDAO.getAll();
        callback.currenciesLoaded(new Response(currencies, null));
    }

    public boolean isEmpty() {
        Log.d(TAG, "LocalDataSource.isEmpty " + Thread.currentThread().getName());
        final String USD_CODE = "USD";
        return currencyDAO.getCurrencyByCode(USD_CODE) == null;
    }

    public void saveAll(List<CurrencyData> currencies) {
        Log.d(TAG, "LocalDataSource.saveAll " + Thread.currentThread().getName());
        currencyDAO.saveAll(currencies);
    }
}
