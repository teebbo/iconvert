package com.aleengo.iconvert.di.search;

import com.aleengo.iconvert.application.Constant;
import com.aleengo.iconvert.di.common.PerActivity;
import com.aleengo.iconvert.ui.search.SearchCurrencyActivity;
import com.aleengo.iconvert.ui.search.SearchCurrencyAdapter;
import com.aleengo.iconvert.ui.search.SearchTemplate;

import java.util.LinkedList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CK_ALEENGO on 02/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Module
public class SearchModule {

    private final SearchCurrencyActivity activity;

    public SearchModule(SearchCurrencyActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    public SearchCurrencyActivity activity() {
        return activity;
    }

    @Provides
    @PerActivity
    public static SearchTemplate view(SearchCurrencyActivity activity) {
        return new SearchTemplate(activity);
    }

    @Provides
    @PerActivity
    public static Integer requestCode(SearchCurrencyActivity activity) {
        return activity.getIntent()
                .getIntExtra(Constant.RC_SEARCH, Constant.NO_EXTRA);
    }

    @Provides
    @PerActivity
    public static SearchCurrencyAdapter adapter(SearchCurrencyActivity activity, Integer requestCode) {
        return new SearchCurrencyAdapter(activity, new LinkedList<>(), requestCode);
    }
}
