package com.aleengo.iconvert.di.search;

import com.aleengo.iconvert.di.common.PerView;
import com.aleengo.iconvert.ui.search.SearchTemplate;

import dagger.Subcomponent;

/**
 * Created by CK_ALEENGO on 08/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@PerView
@Subcomponent(modules = ViewModule.class)
public interface ViewComponent {

    void inject(SearchTemplate view);

    @Subcomponent.Builder
    interface Builder {
        Builder viewModule(ViewModule module);
        ViewComponent build();
    }
}
