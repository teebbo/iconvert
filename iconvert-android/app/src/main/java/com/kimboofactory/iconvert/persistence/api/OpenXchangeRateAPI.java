package com.kimboofactory.iconvert.persistence.api;


import com.aleengo.peach.toolbox.commons.common.OnCompleteCallback;
import com.aleengo.peach.toolbox.commons.concurrent.HTTPService;
import com.aleengo.peach.toolbox.commons.concurrent.ResponseCallback;
import com.aleengo.peach.toolbox.commons.net.HttpClient;
import com.aleengo.peach.toolbox.commons.net.RequestConfig;
import com.aleengo.peach.toolbox.commons.net.RequestWrapper;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.Arrays;

import okhttp3.OkHttpClient;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class OpenXchangeRateAPI implements API {

    public static final String BASE_URL = "https://openexchangerates.org/api/";
    public static final String RATE_END_POINT = "latest.json?app_id=d1ff258466994304854884d3600a75aa";
    public static final String CURRENCIES_END_POINT = "currencies.json";

    public static final String REQUEST_RATE = "REQUEST_RATE";
    public static final String REQUEST_CURRENCY = "REQUEST_CURRENCY";

    private static OkHttpClient client;

    private final RequestConfig rateConfig;
    private final RequestConfig currencyConfig;

    private OpenXchangeRateAPI() {
        client = configureClient();

        final RequestConfig.Builder builder = new RequestConfig.Builder(new ResponseCallback())
                .baseUrl(BASE_URL);
        rateConfig = builder.endPoint(RATE_END_POINT)
                .callback(new ResponseCallback())
                .build();
        currencyConfig = builder.endPoint(CURRENCIES_END_POINT)
                .callback(new ResponseCallback())
                .build();
    }

    public static OpenXchangeRateAPI getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void getCurrencies(OnCompleteCallback callback) {
        final RequestWrapper request = new RequestWrapper(REQUEST_CURRENCY, client, currencyConfig);
        HTTPService.execute(request, callback);
    }

    public void getRates(OnCompleteCallback callback) {
        final RequestWrapper request = new RequestWrapper(REQUEST_RATE, client, rateConfig);
        HTTPService.execute(request, callback);
    }

    public void getRatesAndCurrencies(OnCompleteCallback callback) {
        final RequestWrapper rateRequest = new RequestWrapper(REQUEST_RATE, client, rateConfig);
        final RequestWrapper currencyRequest = new RequestWrapper(REQUEST_CURRENCY, client, currencyConfig);

        HTTPService.execute(Arrays.asList(currencyRequest, rateRequest), callback);
    }

    private OkHttpClient configureClient() {
        return HttpClient.getInstance().get()
                .newBuilder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    private static class LazyHolder {
        public static final OpenXchangeRateAPI INSTANCE = new OpenXchangeRateAPI();
    }
}
