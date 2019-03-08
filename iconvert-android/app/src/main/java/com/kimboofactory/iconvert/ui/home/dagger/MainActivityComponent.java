package com.kimboofactory.iconvert.ui.home.dagger;

import com.kimboofactory.iconvert.application.dagger.PerActivity;
import com.kimboofactory.iconvert.application.dagger.AppComponent;
import com.kimboofactory.iconvert.ui.home.views.MainActivity;

import dagger.Component;

/**
 * Created by CK_ALEENGO on 04/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */

@PerActivity
@Component(dependencies = AppComponent.class, modules = {MainActivityModule.class})
public interface MainActivityComponent {

    void inject(MainActivity activity);
    ViewComponent.Builder viewComponentBuilder();

    @Component.Builder
    interface Builder {
        Builder plusAppComponent(AppComponent component);
        Builder mainActivityModule(MainActivityModule module);
        MainActivityComponent build();
    }
}
