package com.kimboofactory.iconvert.ui.search.dagger;

import com.kimboofactory.iconvert.ui.search.views.SearchCurrencyActivity;

import dagger.Subcomponent;

/**
 * Created by CK_ALEENGO on 04/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@SearchScope
@Subcomponent(modules = {SearchModule.class})
public interface SearchComponent {

    void inject(SearchCurrencyActivity activity);

    @Subcomponent.Builder
    interface Builder {
        Builder searchActivityModule(SearchModule module);
        SearchComponent build();
    }
}
