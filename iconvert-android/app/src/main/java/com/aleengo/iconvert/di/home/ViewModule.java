package com.aleengo.iconvert.di.home;

import com.aleengo.iconvert.di.common.PerView;
import com.aleengo.iconvert.ui.favorite.FavoritesAdapter;
import com.aleengo.iconvert.ui.home.ActivityHome;

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
    public static FavoritesAdapter adapter(ActivityHome activity) {
        return new FavoritesAdapter(activity, new LinkedList<>());
    }
}
