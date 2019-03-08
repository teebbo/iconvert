package com.kimboofactory.iconvert.ui.search.dagger;

import com.kimboofactory.iconvert.application.dagger.PerActivity;
import com.kimboofactory.iconvert.application.dagger.AppComponent;
import com.kimboofactory.iconvert.ui.search.views.SearchCurrencyActivity;

import dagger.Component;

/**
 * Created by CK_ALEENGO on 04/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {SearchActivityModule.class})
public interface SearchActivityComponent {

    void inject(SearchCurrencyActivity activity);
    ViewComponent.Builder viewComponentBuilder();

    @Component.Builder
    interface Builder {
        Builder plusAppComponent(AppComponent component);
        Builder searchActivityModule(SearchActivityModule module);
        SearchActivityComponent build();
    }
}
