package com.aleengo.iconvert.di.home;

import com.aleengo.iconvert.ui.home.ActivityHome;
import com.aleengo.iconvert.ui.home.HomeTemplate;
import com.aleengo.peank.core.annotations.dagger.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CK_ALEENGO on 02/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Module
public class HomeModule {

    private final ActivityHome activity;

    public HomeModule(ActivityHome activity) {
        this.activity = activity;
    }

    @Provides
    @Activity
    public ActivityHome activity() {
        return activity;
    }

    @Provides
    @Activity
    public static HomeTemplate view(ActivityHome activity) {
        return new HomeTemplate(activity);
    }
}
