package com.kimboofactory.iconvert.application;

import android.app.Activity;
import android.app.Application;

import com.aleengo.peach.toolbox.commons.common.PeachConfig;
import com.kimboofactory.iconvert.application.dagger.AppComponent;
import com.kimboofactory.iconvert.application.dagger.AppModule;
import com.kimboofactory.iconvert.application.dagger.DaggerAppComponent;
import com.kimboofactory.iconvert.debug.LeakLoggerService;
import com.kimboofactory.iconvert.debug.StethoDebug;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by CK_ALEENGO on 26/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class IConvertApplication extends Application {

    private AppComponent appComponent;

    public static IConvertApplication getApplication(Activity context) {
        return (IConvertApplication) context.getApplication();
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
