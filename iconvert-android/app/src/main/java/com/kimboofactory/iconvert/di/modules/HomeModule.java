package com.kimboofactory.iconvert.di.modules;

import com.kimboofactory.iconvert.di.scope.ActivityScope;
import com.kimboofactory.iconvert.ui.main.FavoritesAdapter;
import com.kimboofactory.iconvert.ui.main.ListViewListener;
import com.kimboofactory.iconvert.ui.main.MainActivity;

import java.lang.ref.WeakReference;
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
    @ActivityScope
    public MainActivity mainActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    public ListViewListener listener(MainActivity activity) {
        return new ListViewListener(new WeakReference<>(activity));
    }

    @Provides
    @ActivityScope
    public FavoritesAdapter adapter(MainActivity mainActivity) {
        return new FavoritesAdapter(mainActivity, new LinkedList<>());
    }
}
