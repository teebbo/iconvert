package com.kimboofactory.iconvert.di.modules;

import com.kimboofactory.iconvert.di.scope.ActivityScope;
import com.kimboofactory.iconvert.ui.main.MainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CK_ALEENGO on 02/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Module
public class MainActivityModule {

    private final MainActivity activity;

    public MainActivityModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    public MainActivity mainActivity() {
        return activity;
    }
}
