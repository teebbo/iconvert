package com.aleengo.iconvert.di.search;

import com.aleengo.iconvert.di.common.PerView;
import com.aleengo.iconvert.application.Constant;
import com.aleengo.iconvert.ui.search.SearchCurrencyActivity;
import com.aleengo.iconvert.ui.search.SearchCurrencyAdapter;

import java.util.LinkedList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CK_ALEENGO on 08/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Module
public class ViewModule {

    public ViewModule() {
    }

    @Provides
    @PerView
    public static Integer requestCode(SearchCurrencyActivity activity) {
        return activity.getIntent()
                .getIntExtra(Constant.RC_SEARCH, Constant.NO_EXTRA);
    }

    @Provides
    @PerView
    public static SearchCurrencyAdapter adapter(SearchCurrencyActivity activity, Integer requestCode) {
        return new SearchCurrencyAdapter(activity, new LinkedList<>(), requestCode);
    }
}
