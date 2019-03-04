package com.kimboofactory.iconvert.di.component;

import com.kimboofactory.iconvert.di.modules.SearchActivityModule;
import com.kimboofactory.iconvert.di.scope.ActivityScope;
import com.kimboofactory.iconvert.ui.search.SearchCurrencyActivity;

import dagger.Subcomponent;

/**
 * Created by CK_ALEENGO on 04/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@ActivityScope
@Subcomponent(modules = {SearchActivityModule.class})
public interface SearchActivityComponent {
    void inject(SearchCurrencyActivity activity);

    @Subcomponent.Builder
    interface Builder {
        Builder searchActivityModule(SearchActivityModule module);
        SearchActivityComponent build();
    }
}
