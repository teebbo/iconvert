package com.aleengo.iconvert.di.home;

import com.aleengo.iconvert.di.common.PerActivity;
import com.aleengo.iconvert.ui.home.ActivityHome;
import com.aleengo.iconvert.ui.home.HomeTemplate;

import dagger.Subcomponent;

/**
 * Created by CK_ALEENGO on 04/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */

@PerActivity
@Subcomponent(modules = {HomeModule.class})
public interface HomeComponent {

    void inject(ActivityHome activity);
    void inject(HomeTemplate template);
}
