package com.kimboofactory.iconvert.persistence.api;

import com.aleengo.peach.toolbox.commons.net.OkHttpSingleton;
import com.aleengo.peach.toolbox.commons.net.PeachRequest;
import com.kimboofactory.iconvert.net.KFYRequest;
import com.kimboofactory.iconvert.util.SingletonUtil;

import okhttp3.Callback;
import okhttp3.Dispatcher;
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

    private static class LazyHolder {
        private static final OpenXchangeRateAPI INSTANCE = new OpenXchangeRateAPI();
    }

    public static OpenXchangeRateAPI getInstance() {
        return LazyHolder.INSTANCE;
    }


    public void getCurrencies(Callback callback) {
        /*final KFYRequest request = KFYRequest.builder()
                .baseUrl(BASE_URL)
                .endpoint(CURRENCIES_END_POINT)
                .build();*/

        final PeachRequest request = new PeachRequest.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .build();

        //request.execute(callback);
        request.get(CURRENCIES_END_POINT, callback);
        request.get(RATE_END_POINT, callback);


    }

    private OkHttpClient configureClient() {
        final Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(5);

        return OkHttpSingleton.getInstance()
                .getClient()
                .newBuilder()
                .dispatcher(dispatcher)
                .build();
    }
}
