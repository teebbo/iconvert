package com.aleengo.iconvert.di.search;

import com.aleengo.iconvert.ui.search.ActivitySearchCurrency;
import com.aleengo.iconvert.ui.search.SearchTemplate;
import com.aleengo.peank.core.annotations.dagger.Activity;

import dagger.Subcomponent;

/**
 * Created by CK_ALEENGO on 04/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Activity
@Subcomponent(modules = {SearchModule.class})
public interface SearchComponent {

    void inject(ActivitySearchCurrency activity);
    void inject(SearchTemplate template);
}
