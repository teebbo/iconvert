package com.kimboofactory.iconvert.application.dagger;

import com.kimboofactory.iconvert.persistence.repository.CurrencyRepository;

import dagger.Component;

/**
 * Created by CK_ALEENGO on 03/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@ApplicationScope
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    CurrencyRepository repository();

    @Component.Builder
    interface Builder {
        Builder appModule(AppModule module);
        AppComponent build();
    }
}
