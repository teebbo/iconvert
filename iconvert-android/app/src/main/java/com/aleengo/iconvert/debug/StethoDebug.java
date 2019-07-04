package com.aleengo.iconvert.debug;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by CK_ALEENGO on 21/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public final class StethoDebug {

    public static void installStetho(Application application) {
        final Stetho.Initializer stethoInitializer = Stetho.newInitializerBuilder(application)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(application))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(application))
                .build();
        Stetho.initialize(stethoInitializer);
    }
}
