package com.kimboofactory.iconvert.domain;

import com.aleengo.peach.toolbox.commons.model.Response;
import com.kimboofactory.iconvert.persistence.model.CurrencyData;

import java.util.List;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public interface Repository {
    interface GetCallback {
        void onFinished(Response response);
    }

    interface SearchCallback {
        void onDataLoaded(Response response);
    }

    void search(String query, SearchCallback callback);
    void addAll(List<CurrencyData> currencies);
    void getCurrencies(GetCallback callback);
    void getRates(GetCallback callback);
    void addRatesAndCurrencies();

}
