package com.kimboofactory.iconvert.ui.search.dagger;

import com.kimboofactory.iconvert.application.dagger.PerView;
import com.kimboofactory.iconvert.common.Constant;
import com.kimboofactory.iconvert.ui.search.views.SearchCurrencyActivity;
import com.kimboofactory.iconvert.ui.search.views.SearchCurrencyAdapter;

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
                .getIntExtra(Constant.REQUEST_CODE, Constant.NO_EXTRA);
    }

    @Provides
    @PerView
    public static SearchCurrencyAdapter adapter(SearchCurrencyActivity activity, Integer requestCode) {
        return new SearchCurrencyAdapter(activity, new LinkedList<>(), requestCode);
    }
}
