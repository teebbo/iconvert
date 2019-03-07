package com.kimboofactory.iconvert.ui.home.dagger;

import com.kimboofactory.iconvert.ui.home.presentation.MvpHomeView;
import com.kimboofactory.iconvert.ui.home.views.FavoritesAdapter;
import com.kimboofactory.iconvert.ui.home.views.HomeViewListener;
import com.kimboofactory.iconvert.ui.home.views.MainActivity;

import java.util.LinkedList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CK_ALEENGO on 02/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Module
public class HomeModule {

    private final MainActivity activity;

    public HomeModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    @HomeScope
    public MvpHomeView view() {
        return new MvpHomeView(activity);
    }

    @Provides
    @HomeScope
    public FavoritesAdapter adapter() {
        return new FavoritesAdapter(activity, new LinkedList<>());
    }

    @Provides
    @HomeScope
    public HomeViewListener listener(MvpHomeView view) {
        return new HomeViewListener(view);
    }
}
