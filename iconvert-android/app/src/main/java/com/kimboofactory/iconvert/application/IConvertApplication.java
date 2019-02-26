package com.kimboofactory.iconvert.application;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by CK_ALEENGO on 26/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class IConvertApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        final Stetho.Initializer stethoInitializer = Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build();
        Stetho.initialize(stethoInitializer);
    }
}
