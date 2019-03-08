package com.kimboofactory.iconvert.ui.search.dagger;

import com.kimboofactory.iconvert.application.dagger.PerView;
import com.kimboofactory.iconvert.ui.search.presentation.MvpSearchView;

import dagger.Subcomponent;

/**
 * Created by CK_ALEENGO on 08/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@PerView
@Subcomponent(modules = ViewModule.class)
public interface ViewComponent {

    void inject(MvpSearchView view);

    @Subcomponent.Builder
    interface Builder {
        Builder viewModule(ViewModule module);
        ViewComponent build();
    }
}
