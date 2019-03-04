package com.kimboofactory.iconvert.di.component;

import com.kimboofactory.iconvert.di.modules.MainActivityModule;
import com.kimboofactory.iconvert.di.scope.ActivityScope;
import com.kimboofactory.iconvert.ui.main.MainActivity;

import dagger.Subcomponent;

/**
 * Created by CK_ALEENGO on 04/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */

@ActivityScope
@Subcomponent(modules = {MainActivityModule.class})
public interface MainActivityComponent {
    void inject(MainActivity activity);

    @Subcomponent.Builder
    interface Builder {
        Builder mainActivityModule(MainActivityModule module);
        MainActivityComponent build();
    }
}
