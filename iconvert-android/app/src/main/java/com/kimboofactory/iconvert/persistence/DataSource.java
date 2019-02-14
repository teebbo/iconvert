package com.kimboofactory.iconvert.persistence;

import com.kimboofactory.iconvert.dto.CurrencyIHM;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public interface DataSource {

    interface GetCurrencyCallback {
        void currencyLoaded(String error, String currency);
    }

    interface GetCurrenciesCallback {
        void currenciesLoaded(String error, String currencies);
    }

    void getCurrencyByCode(String code, GetCurrencyCallback callback);
    void getCurrencies(GetCurrenciesCallback callback);
}
