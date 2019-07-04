package com.aleengo.iconvert.di.common;

import com.aleengo.iconvert.di.home.HomeComponent;
import com.aleengo.iconvert.di.home.HomeModule;
import com.aleengo.iconvert.di.search.SearchComponent;
import com.aleengo.iconvert.di.search.SearchModule;
import com.aleengo.iconvert.persistence.repository.CurrencyRepository;

import dagger.Component;

/**
 * Created by CK_ALEENGO on 03/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@ApplicationScope
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    CurrencyRepository repository();
    HomeComponent plus(HomeModule module);
    SearchComponent plus(SearchModule module);

    @Component.Builder
    interface Builder {
        Builder appModule(AppModule module);
        AppComponent build();
    }
}
