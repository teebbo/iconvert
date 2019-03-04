package com.kimboofactory.iconvert.di.component;

import com.kimboofactory.iconvert.di.modules.AppModule;
import com.kimboofactory.iconvert.di.modules.NetworkModule;
import com.kimboofactory.iconvert.di.modules.PersistenceModule;
import com.kimboofactory.iconvert.di.scope.ApplicationScope;

import dagger.Component;

/**
 * Created by CK_ALEENGO on 03/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@ApplicationScope
@Component(modules = {
        AppModule.class,
        NetworkModule.class,
        PersistenceModule.class
})
public interface AppComponent {
    MainActivityComponent.Builder mainActivityComponentBuilder();
    SearchActivityComponent.Builder searchActivityComponentBuilder();
}
