package com.kimboofactory.iconvert.ui.home.dagger;

import com.kimboofactory.iconvert.application.dagger.PerActivity;
import com.kimboofactory.iconvert.ui.home.presentation.MvpHomeView;
import com.kimboofactory.iconvert.ui.home.views.MainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CK_ALEENGO on 02/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Module(subcomponents = ViewComponent.class)
public class MainActivityModule {

    private final MainActivity activity;

    public MainActivityModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    public MainActivity mainActivity() {
        return activity;
    }

    @Provides
    @PerActivity
    public static MvpHomeView view(MainActivity activity) {
        return new MvpHomeView(activity);
    }
}
