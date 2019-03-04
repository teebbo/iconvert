package com.kimboofactory.iconvert.di.modules;

import com.aleengo.peach.toolbox.commons.net.HttpClient;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.kimboofactory.iconvert.di.scope.ApplicationScope;
import com.kimboofactory.iconvert.persistence.api.OpenXchangeRateAPI;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by CK_ALEENGO on 03/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Module
public class NetworkModule {

    @Provides
    @ApplicationScope
    public StethoInterceptor stethoInterceptor() {
        return new StethoInterceptor();
    }

    @Provides
    @ApplicationScope
    public OkHttpClient okHttpClient(StethoInterceptor stethoInterceptor) {
        return HttpClient.getInstance().get()
                .newBuilder()
                .addNetworkInterceptor(stethoInterceptor)
                .build();
    }

    @Provides
    @ApplicationScope
    public OpenXchangeRateAPI openXchangeRateAPI(OkHttpClient client) {
        return new OpenXchangeRateAPI(client);
    }
}
