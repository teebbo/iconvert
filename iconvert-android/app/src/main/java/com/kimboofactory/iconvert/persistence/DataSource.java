package com.kimboofactory.iconvert.persistence;

import com.aleengo.peach.toolbox.commons.common.Response;

import java.util.List;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public interface DataSource {

    interface GetCurrencyCallback {
        void currencyLoaded(Response<String> response);
    }

    interface GetCurrenciesCallback {
        void currenciesLoaded(Response<String> response);
    }

    void getCurrencyByCode(String code, GetCurrencyCallback callback);
    void getCurrencies(GetCurrenciesCallback callback);
}
