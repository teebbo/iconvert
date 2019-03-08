package com.kimboofactory.iconvert.ui.search.dagger;

import com.kimboofactory.iconvert.ui.search.presentation.MvpSearchView;

import dagger.Subcomponent;

/**
 * Created by CK_ALEENGO on 08/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@SubSearchScope
@Subcomponent(modules = SearchViewModule.class)
public interface SearchViewComponent {

    void inject(MvpSearchView view);

    @Subcomponent.Builder
    interface Builder {
        Builder searchViewModule(SearchViewModule module);
        SearchViewComponent build();
    }
}
