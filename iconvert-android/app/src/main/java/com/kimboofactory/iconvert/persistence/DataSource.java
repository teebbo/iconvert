package com.kimboofactory.iconvert.persistence;

import com.kimboofactory.iconvert.dto.CurrencyIHM;

public interface DataSource {

    interface GetCurrencyCallback {
        void currencyLoaded(CurrencyIHM currency);
    }

    interface GetCurrenciesCallback {
        void currenciesLoaded();
    }

    void getCurrencyByCode(String code, GetCurrencyCallback callback);
    void getCurrencies(GetCurrenciesCallback callback);
}
