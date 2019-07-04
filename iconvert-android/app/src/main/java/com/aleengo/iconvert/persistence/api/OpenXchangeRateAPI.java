package com.aleengo.iconvert.persistence.api;


import com.aleengo.peach.toolbox.commons.common.OnCompleteCallback;
import com.aleengo.peach.toolbox.commons.concurrent.HTTPService;
import com.aleengo.peach.toolbox.commons.concurrent.ResponseCallback;
import com.aleengo.peach.toolbox.commons.net.RequestConfig;
import com.aleengo.peach.toolbox.commons.net.RequestWrapper;
import com.aleengo.iconvert.application.Constant;

import java.util.Arrays;

import okhttp3.OkHttpClient;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class OpenXchangeRateAPI implements API {

    public static final String REQUEST_RATE = "REQUEST_RATE";
    public static final String REQUEST_CURRENCY = "REQUEST_CURRENCY";

    private OkHttpClient client;

    private final RequestConfig rateConfig;
    private final RequestConfig currencyConfig;

    public OpenXchangeRateAPI(OkHttpClient client) {

        this.client = client;

        final RequestConfig.Builder builder = new RequestConfig.Builder(new ResponseCallback())
                .baseUrl(Constant.BASE_URL);
        rateConfig = builder.endPoint(Constant.LATEST)
                .callback(new ResponseCallback())
                .build();
        currencyConfig = builder.endPoint(Constant.CURRENCIES)
                .callback(new ResponseCallback())
                .build();
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

}
