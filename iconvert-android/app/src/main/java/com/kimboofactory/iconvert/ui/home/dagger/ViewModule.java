package com.kimboofactory.iconvert.ui.home.dagger;

import com.kimboofactory.iconvert.application.dagger.PerView;
import com.kimboofactory.iconvert.ui.home.views.FavoritesAdapter;
import com.kimboofactory.iconvert.ui.home.views.MainActivity;

import java.util.LinkedList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CK_ALEENGO on 08/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Module
public class ViewModule {

    public ViewModule() {}

    @Provides
    @PerView
    public static FavoritesAdapter adapter(MainActivity activity) {
        return new FavoritesAdapter(activity, new LinkedList<>());
    }
}
