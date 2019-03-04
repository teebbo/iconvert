package com.kimboofactory.iconvert.di.modules;

import android.content.Context;

import com.kimboofactory.iconvert.di.component.MainActivityComponent;
import com.kimboofactory.iconvert.di.component.SearchActivityComponent;
import com.kimboofactory.iconvert.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CK_ALEENGO on 04/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Module(subcomponents = {
        MainActivityComponent.class,
        SearchActivityComponent.class
})
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @ApplicationScope
    // application's context
    public Context context() {
        return context;
    }
}
