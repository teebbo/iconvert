package com.aleengo.iconvert.application;

import com.aleengo.iconvert.di.home.HomeComponent;
import com.aleengo.iconvert.di.search.SearchComponent;
import com.aleengo.iconvert.ui.home.ActivityHome;
import com.aleengo.iconvert.ui.search.ActivitySearchCurrency;

/**
 * Copyright (c) Aleengo, 2019. All rights reserved.
 * Created by bau.cj on 04/07/2019.
 */
public interface ConvertContract {

    void releaseHomeComponent();
    void releaseSearchComponent();

    HomeComponent homeComponent(ActivityHome activity);
    SearchComponent searchComponent(ActivitySearchCurrency activity);
}
