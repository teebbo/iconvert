package com.kimboofactory.iconvert.ui.search.dagger;

import com.kimboofactory.iconvert.common.Constant;
import com.kimboofactory.iconvert.ui.search.presentation.MvpSearchView;
import com.kimboofactory.iconvert.ui.search.views.SearchCurrencyActivity;
import com.kimboofactory.iconvert.ui.search.views.SearchCurrencyAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CK_ALEENGO on 08/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Module
public class SearchViewModule {

    private final MvpSearchView view;

    public SearchViewModule(MvpSearchView view) {
        this.view = view;
    }

    @Provides
    @SubSearchScope
    public Integer requestCode(SearchCurrencyActivity activity) {
        return activity.getIntent()
                .getIntExtra(Constant.REQUEST_CODE, MvpSearchView.NO_EXTRA);
    }

    @Provides
    @SubSearchScope
    public SearchCurrencyAdapter adapter(SearchCurrencyActivity activity, Integer requestCode) {
        return new SearchCurrencyAdapter(activity, Constant.CURRENCY_IHMS_EMPTY_LIST, requestCode);
    }

   /* @Provides
    @SubSearchScope
    public SearchPresenter presenter(CurrencyRepository repository) {
        return new SearchPresenter(repository);
    }*/
}
