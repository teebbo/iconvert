package com.kimboofactory.iconvert.persistence;


import com.aleengo.peach.toolbox.commons.model.Response;

import java.util.List;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public interface CurrencyDataSource {

    interface GetCurrencyCallback {
        void currencyLoaded(Response response);
    }

    interface GetCurrenciesCallback {
        void currenciesLoaded(Response response);
    }

    void getCurrencyByCode(String code, GetCurrencyCallback callback);
    void getCurrencies(GetCurrenciesCallback callback);
}
