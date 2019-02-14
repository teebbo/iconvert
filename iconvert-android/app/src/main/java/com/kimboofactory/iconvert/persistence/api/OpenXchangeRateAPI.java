package com.kimboofactory.iconvert.persistence.api;

import com.kimboofactory.iconvert.net.KFYRequest;
import com.kimboofactory.iconvert.util.SingletonUtil;

import okhttp3.Callback;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class OpenXchangeRateAPI {

    public static final String BASE_URL = "https://openexchangerates.org/api/";
    public static final String RATE_END_POINT = "latest.json";
    public static final String CURRENCIES_END_POINT = "currencies.json";

    private static OpenXchangeRateAPI instance;

    public static OpenXchangeRateAPI getInstance() {
        instance = SingletonUtil.getInstance(OpenXchangeRateAPI.class, instance);
        return instance;
    }


    public void getCurrencies(Callback callback) {
        final KFYRequest request = KFYRequest.builder()
                .baseUrl(BASE_URL)
                .endpoint(CURRENCIES_END_POINT)
                .build();

        request.execute(callback);
    }
}
