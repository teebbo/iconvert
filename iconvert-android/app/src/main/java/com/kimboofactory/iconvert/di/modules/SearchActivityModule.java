package com.kimboofactory.iconvert.di.modules;

import com.kimboofactory.iconvert.di.scope.ActivityScope;
import com.kimboofactory.iconvert.ui.search.SearchCurrencyActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CK_ALEENGO on 02/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Module
public class SearchActivityModule {

    private final SearchCurrencyActivity activity;

    public SearchActivityModule(SearchCurrencyActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    public SearchCurrencyActivity searchCurrencyActivity() {
        return activity;
    }
}
