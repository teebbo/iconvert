package com.aleengo.iconvert.application;

import android.app.Activity;
import android.app.Application;

import com.aleengo.iconvert.debug.LeakLoggerService;
import com.aleengo.iconvert.debug.StethoDebug;
import com.aleengo.iconvert.di.common.AppComponent;
import com.aleengo.iconvert.di.common.AppModule;
import com.aleengo.iconvert.di.common.DaggerAppComponent;
import com.aleengo.iconvert.di.home.HomeComponent;
import com.aleengo.iconvert.di.home.HomeModule;
import com.aleengo.iconvert.di.search.SearchComponent;
import com.aleengo.iconvert.di.search.SearchModule;
import com.aleengo.iconvert.ui.home.ActivityHome;
import com.aleengo.iconvert.ui.search.ActivitySearchCurrency;
import com.aleengo.peach.toolbox.commons.common.PeachConfig;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by CK_ALEENGO on 26/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class ConvertApp extends Application implements ConvertContract {

    private AppComponent appComponent;
    private HomeComponent homeComponent;
    private SearchComponent searchComponent;

    public static ConvertApp app(Activity context) {
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

    @Override
    public void releaseHomeComponent() {
        homeComponent = null;
    }

    @Override
    public void releaseSearchComponent() {
        searchComponent = null;
    }

    @Override
    public HomeComponent homeComponent(ActivityHome activity) {
        if (homeComponent == null) {
            homeComponent = appComponent.plus(new HomeModule(activity));
        }
        return homeComponent;
    }

    @Override
    public SearchComponent searchComponent(ActivitySearchCurrency activity) {
        if (searchComponent == null) {
            searchComponent = appComponent.plus(new SearchModule(activity));
        }
        return searchComponent;
    }
}
