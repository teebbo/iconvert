package com.kimboofactory.iconvert.persistence.local;

import com.kimboofactory.iconvert.persistence.DataSource;
import com.kimboofactory.iconvert.persistence.model.CurrencyData;
import com.kimboofactory.iconvert.util.SingletonUtil;

import java.util.List;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class LocalDataSource implements DataSource {

    private static LocalDataSource instance;
    private CurrencyDAO currencyDAO;

    private LocalDataSource() {
    }

    private static class LazyHolder {
        private static final LocalDataSource INSTANCE = new LocalDataSource();
    }

    public static LocalDataSource getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public void getCurrencyByCode(String code, GetCurrencyCallback callback) {

    }

    @Override
    public void getCurrencies(GetCurrenciesCallback callback) {

    }

    public boolean isEmpty() {
        //return currencyDAO.get("USD") == null;
        return false;
    }


    public void saveAll(List<CurrencyData> currencies) {
        //currencyDAO.saveAll(currencies);
    }
}
