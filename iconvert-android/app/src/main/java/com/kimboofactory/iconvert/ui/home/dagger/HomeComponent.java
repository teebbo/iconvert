package com.kimboofactory.iconvert.ui.home.dagger;

import com.kimboofactory.iconvert.ui.home.views.MainActivity;

import dagger.Subcomponent;

/**
 * Created by CK_ALEENGO on 04/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */

@HomeScope
@Subcomponent(modules = {HomeModule.class})
public interface HomeComponent {

    void inject(MainActivity activity);

    @Subcomponent.Builder
    interface Builder {
        Builder homeModule(HomeModule module);
        HomeComponent build();
    }
}
