package com.aleengo.iconvert.application;

import android.app.Activity;
import android.app.Application;

import com.aleengo.peach.toolbox.commons.common.PeachConfig;
import com.aleengo.iconvert.di.common.AppComponent;
import com.aleengo.iconvert.di.common.AppModule;
import com.aleengo.iconvert.application.dagger.DaggerAppComponent;
import com.aleengo.iconvert.debug.LeakLoggerService;
import com.aleengo.iconvert.debug.StethoDebug;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by CK_ALEENGO on 26/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class ConvertApp extends Application {

    private AppComponent appComponent;

    public static ConvertApp getApplication(Activity context) {
        return (ConvertApp) context.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        PeachConfig.setDebug(true);

        // Dagger initialization
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        if (PeachConfig.isDebug()) {
            LeakLoggerService.installLeakCanary(this);
            StethoDebug.installStetho(this);
            LeakCanary.installedRefWatcher().watch(appComponent, "Dagger#AppComponent");
        }
    }

    public AppComponent appComponent() {
        return appComponent;
    }

    public static RefWatcher getRefWatcher() {
        return LeakCanary.installedRefWatcher();
    }
}
