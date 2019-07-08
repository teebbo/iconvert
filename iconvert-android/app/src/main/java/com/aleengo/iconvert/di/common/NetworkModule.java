package com.aleengo.iconvert.di.common;

import com.aleengo.iconvert.persistence.api.OpenXchangeRateAPI;
import com.aleengo.iconvert.persistence.api.XchangeRateAPI;
import com.aleengo.iconvert.persistence.deserializer.CurrencyWrapperAdapter;
import com.aleengo.iconvert.persistence.deserializer.CurrencyWrapperList;
import com.aleengo.peank.core.annotations.dagger.Application;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by CK_ALEENGO on 03/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Module
public abstract class NetworkModule {

    @Provides
    @Application
    public static StethoInterceptor stethoInterceptor() {
        return new StethoInterceptor();
    }

    @Provides
    @Application
    public static OkHttpClient okhttp3(StethoInterceptor stethoInterceptor) {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(stethoInterceptor)
                .build();
    }

    @Provides
    @Application
    public static OpenXchangeRateAPI openXchangeRateAPI(OkHttpClient client) {
        return new OpenXchangeRateAPI(client);
    }

    @Application
    @Provides
    public static Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(CurrencyWrapperList.class, new CurrencyWrapperAdapter())
                .create();
    }

    @Application
    @Provides
    public static Converter.Factory converterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Application
    @Provides
    public static Retrofit retrofit(OkHttpClient client, Converter.Factory converter) {
        return new Retrofit.Builder()
                .baseUrl(XchangeRateAPI.BASE_URL)
                .client(client)
                .addConverterFactory(converter)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Application
    @Provides
    public static XchangeRateAPI oxr(Retrofit retrofit) {
        return retrofit.create(XchangeRateAPI.class);
    }
}
