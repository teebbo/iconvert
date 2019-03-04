package com.kimboofactory.iconvert.di.modules;

import com.kimboofactory.iconvert.di.scope.ActivityScope;
import com.kimboofactory.iconvert.ui.main.MainActivity;
import com.kimboofactory.iconvert.ui.search.SearchCurrencyActivity;
import com.kimboofactory.iconvert.ui.search.SearchCurrencyAdapter;
import com.kimboofactory.iconvert.util.Helper;

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

    @ActivityScope
    @Provides
    public SearchCurrencyActivity searchCurrencyActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    public Integer requestCode(SearchCurrencyActivity activity) {
        return activity.getIntent()
                .getIntExtra(MainActivity.REQUEST_CODE,
                        SearchCurrencyActivity.NO_EXTRA);
    }

    @Provides
    @ActivityScope
    public SearchCurrencyAdapter adapter(SearchCurrencyActivity activity, Integer requestCode) {
        return new SearchCurrencyAdapter(activity, Helper.CURRENCY_IHMS_EMPTY_LIST, requestCode);
    }
}
