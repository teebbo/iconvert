package com.kimboofactory.iconvert.domain;

import com.kimboofactory.iconvert.dto.CurrencyIHM;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public interface Repository {

    interface GetCallback {
        void onLoaded(CurrencyIHM currencyIHM);
    }

    void get(String code, GetCallback callback);
    void getAll();
}
