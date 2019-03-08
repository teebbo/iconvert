package com.kimboofactory.iconvert.ui.search.dagger;

import com.kimboofactory.iconvert.application.dagger.PerActivity;
import com.kimboofactory.iconvert.ui.search.presentation.MvpSearchView;
import com.kimboofactory.iconvert.ui.search.views.SearchCurrencyActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CK_ALEENGO on 02/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Module(subcomponents = ViewComponent.class)
public class SearchActivityModule {

    private final SearchCurrencyActivity activity;

    public SearchActivityModule(SearchCurrencyActivity activity) {
        this.activity = activity;
    }

    @PerActivity
    @Provides
    public SearchCurrencyActivity searchCurrencyActivity() {
        return activity;
    }

    @Provides
    @PerActivity
    public static MvpSearchView view(SearchCurrencyActivity activity) {
        return new MvpSearchView(activity);
    }
}
