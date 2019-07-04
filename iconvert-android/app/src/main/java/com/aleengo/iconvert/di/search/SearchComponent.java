package com.aleengo.iconvert.di.search;

import com.aleengo.iconvert.di.common.PerActivity;
import com.aleengo.iconvert.ui.search.SearchCurrencyActivity;
import com.aleengo.iconvert.ui.search.SearchTemplate;

import dagger.Subcomponent;

/**
 * Created by CK_ALEENGO on 04/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@PerActivity
@Subcomponent(modules = {SearchModule.class})
public interface SearchComponent {

    void inject(SearchCurrencyActivity activity);
    void inject(SearchTemplate template);
}
