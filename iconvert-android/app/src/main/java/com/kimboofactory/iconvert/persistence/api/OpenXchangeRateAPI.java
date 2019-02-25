package com.kimboofactory.iconvert.persistence.api;


import com.aleengo.peach.toolbox.commons.common.OnCompleteCallback;
import com.aleengo.peach.toolbox.commons.concurrent.HTTPService;
import com.aleengo.peach.toolbox.commons.concurrent.ResponseCallback;
import com.aleengo.peach.toolbox.commons.net.HttpClient;
import com.aleengo.peach.toolbox.commons.net.RequestConfig;
import com.aleengo.peach.toolbox.commons.net.RequestWrapper;

import okhttp3.OkHttpClient;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class OpenXchangeRateAPI implements API {

    public static final String BASE_URL = "https://openexchangerates.org/api/";
    public static final String RATE_END_POINT = "latest.json";
    public static final String CURRENCIES_END_POINT = "currencies.json";

    private static OkHttpClient client;

    private OpenXchangeRateAPI() {
        client = configureClient();
    }

    public static OpenXchangeRateAPI getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void getCurrencies(OnCompleteCallback callback) {
        final RequestConfig config = new RequestConfig.Builder(new ResponseCallback())
                .baseUrl(BASE_URL)
                .endPoint(CURRENCIES_END_POINT)
                .build();
        final RequestWrapper request = new RequestWrapper(client, config);
        HTTPService.execute(request, callback);
    }

    public void getRates(OnCompleteCallback callback) {
        final RequestConfig config = new RequestConfig.Builder(new ResponseCallback())
                .baseUrl(BASE_URL)
                .endPoint(RATE_END_POINT)
                .build();
        final RequestWrapper request = new RequestWrapper(client, config);
        HTTPService.execute(request, callback);
    }

    private OkHttpClient configureClient() {
        return HttpClient.getInstance().get()
                .newBuilder()
                .build();
    }

    private static class LazyHolder {
        public static final OpenXchangeRateAPI INSTANCE = new OpenXchangeRateAPI();
    }
}
