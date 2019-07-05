package com.aleengo.iconvert.di.common;

import android.content.Context;

import com.aleengo.peank.core.annotations.dagger.Application;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CK_ALEENGO on 04/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Module
public class AppModule {

    private Context context;
    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Application
    public Context context() {
        return context;
    }
}
