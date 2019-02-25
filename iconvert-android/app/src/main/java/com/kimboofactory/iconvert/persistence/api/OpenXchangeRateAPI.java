package com.kimboofactory.iconvert.persistence.api;


import com.aleengo.peach.toolbox.commons.common.OnCompleteCallback;
import com.aleengo.peach.toolbox.commons.concurrent.HTTPService;
import com.aleengo.peach.toolbox.commons.concurrent.ResponseCallback;
import com.aleengo.peach.toolbox.commons.factory.Singleton;
import com.aleengo.peach.toolbox.commons.net.HttpClient;
import com.aleengo.peach.toolbox.commons.net.RequestConfig;
import com.aleengo.peach.toolbox.commons.net.RequestWrapper;

import okhttp3.OkHttpClient;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class OpenXchangeRateAPI {

    public static final String BASE_URL = "https://openexchangerates.org/api/";
    public static final String RATE_END_POINT = "latest.json";
    public static final String CURRENCIES_END_POINT = "currencies.json";

    private static OpenXchangeRateAPI instance;
    private static OkHttpClient client;

    private OpenXchangeRateAPI() {
        client = configureClient();
    }

    public void getCurrencies(OnCompleteCallback callback) {
        final RequestConfig currenciesConfig = new RequestConfig.Builder(new ResponseCallback())
                .baseUrl(BASE_URL)
                .endPoint(CURRENCIES_END_POINT)
                .build();
        final RequestWrapper request = new RequestWrapper(currenciesConfig);
        HTTPService.execute(request, callback);
    }

    private OkHttpClient configureClient() {
        return Singleton.of(HttpClient.class).get()
                .newBuilder()
                .build();
    }
}
