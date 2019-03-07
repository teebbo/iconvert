package com.kimboofactory.iconvert.application.dagger;

import com.kimboofactory.iconvert.ui.home.dagger.HomeComponent;
import com.kimboofactory.iconvert.ui.search.dagger.SearchComponent;

import dagger.Component;

/**
 * Created by CK_ALEENGO on 03/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@ApplicationScope
@Component(modules = {
        AppModule.class,
        NetworkModule.class
})
public interface AppComponent {

    HomeComponent.Builder homeComponentBuilder();
    SearchComponent.Builder searchComponentBuilder();

    @Component.Builder
    interface Builder {
        AppComponent build();
        Builder appModule(AppModule module);
    }
}
