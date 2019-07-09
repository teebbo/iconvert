package com.aleengo.iconvert.persistence.api;

import com.aleengo.iconvert.persistence.deserializer.CurrencyWrapperList;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Copyright (c) Aleengo, 2019. All rights reserved.
 * Created by bau.cj on 05/07/2019.
 */
public interface XchangeRateAPI {

    String BASE_URL = "https://openexchangerates.org/api/";
    String APP_ID = "d1ff258466994304854884d3600a75aa";

    String REQ_CURRENCY = "com.aleengo.iconvert.REQ_CURRENCY";
    String REQ_RATE = "com.aleengo.iconvert.REQ_RATE";

    @GET(value = "latest.json?app_id=" + APP_ID)
    Observable<CurrencyWrapperList> latestRates();

    @GET(value = "currencies.json")
    Observable<CurrencyWrapperList> currencies();
}
