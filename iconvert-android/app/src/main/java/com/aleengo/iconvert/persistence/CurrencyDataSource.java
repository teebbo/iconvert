package com.aleengo.iconvert.persistence;


import com.aleengo.peach.toolbox.commons.model.Response;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public interface CurrencyDataSource {

    interface GetCurrencyCallback {
        void onReceived(Response response);
    }

    interface GetCurrenciesCallback {
        void onCurrenciesReceived(Response response);
    }

    interface GetCallback {
        void onReceived(Response response);
    }

    void getCurrency(String code, GetCurrencyCallback callback);
    void getCurrencies(GetCurrenciesCallback callback);
}
