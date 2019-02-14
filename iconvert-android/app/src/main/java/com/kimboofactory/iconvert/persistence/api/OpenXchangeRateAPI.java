package com.kimboofactory.iconvert.persistence.api;

import com.kimboofactory.iconvert.net.KFYRequest;

import okhttp3.Callback;

public class OpenXchangeRateAPI {

    public static final String BASE_URL = "";
    public static final String RATE_END_POINT = "latest.json";
    public static final String CURRENCIES_END_POINT = "currencies.json";

    public void getCurrencies(Callback callback) {
        final KFYRequest request = KFYRequest.builder()
                .baseUrl(BASE_URL)
                .endpoint(CURRENCIES_END_POINT)
                .build();

        request.make(callback);
    }
}
