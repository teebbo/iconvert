package com.kimboofactory.iconvert.application;

import android.app.Application;
import android.content.Context;

import com.aleengo.peach.toolbox.commons.common.PeachConfig;
import com.facebook.stetho.Stetho;
import com.kimboofactory.iconvert.application.dagger.AppComponent;
import com.kimboofactory.iconvert.application.dagger.AppModule;
import com.kimboofactory.iconvert.application.dagger.DaggerAppComponent;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by CK_ALEENGO on 26/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class IConvertApplication extends Application {

    private RefWatcher refWatcher;
    private AppComponent appComponent;

    public static IConvertApplication getApplication(Context context) {
        return (IConvertApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        PeachConfig.setDebug(true);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        this.refWatcher = LeakCanary.install(this);

        final Stetho.Initializer stethoInitializer = Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build();
        Stetho.initialize(stethoInitializer);

        // Dagger initialization
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

    }

    public AppComponent appComponent() {
        return appComponent;
    }

    public static RefWatcher getRefWatcher(Context context) {
        return getApplication(context).refWatcher;
    }
}
