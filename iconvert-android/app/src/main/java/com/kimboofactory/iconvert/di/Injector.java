package com.kimboofactory.iconvert.di;

import com.kimboofactory.iconvert.application.dagger.AppComponent;
import com.kimboofactory.iconvert.ui.home.dagger.DaggerMainActivityComponent;
import com.kimboofactory.iconvert.ui.home.dagger.MainActivityComponent;
import com.kimboofactory.iconvert.ui.home.dagger.MainActivityModule;
import com.kimboofactory.iconvert.ui.home.presentation.MvpHomeView;
import com.kimboofactory.iconvert.ui.home.views.MainActivity;
import com.kimboofactory.iconvert.ui.search.dagger.DaggerSearchActivityComponent;
import com.kimboofactory.iconvert.ui.search.dagger.SearchActivityComponent;
import com.kimboofactory.iconvert.ui.search.dagger.SearchActivityModule;
import com.kimboofactory.iconvert.ui.search.presentation.MvpSearchView;
import com.kimboofactory.iconvert.ui.search.views.SearchCurrencyActivity;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by CK_ALEENGO on 25/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public final class Injector {

    @Getter @Setter
    private AppComponent appComponent;
    @Getter @Setter
    private MainActivityComponent homeComponent;
    @Getter @Setter
    private SearchActivityComponent searchComponent;

    private Injector() {}

    public static Injector instance() {
        return LazyHolder.INSTANCE;
    }

    public void inject(MainActivity activity) {
        setHomeComponent(DaggerMainActivityComponent.builder()
                .plusAppComponent(getAppComponent())
                .mainActivityModule(new MainActivityModule(activity))
                .build());
        getHomeComponent().inject(activity);
    }

    public void inject(SearchCurrencyActivity activity) {
        setSearchComponent(DaggerSearchActivityComponent.builder()
                .plusAppComponent(getAppComponent())
                .searchActivityModule(new SearchActivityModule(activity))
                .build());
        getSearchComponent().inject(activity);
    }

    public void inject(MvpHomeView view) {
        getHomeComponent()
                .viewComponentBuilder()
                .viewModule(new com.kimboofactory.iconvert.ui.home.dagger.ViewModule())
                .build()
                .inject(view);
    }

    public void inject(MvpSearchView view) {
        getSearchComponent()
                .viewComponentBuilder()
                .viewModule(new com.kimboofactory.iconvert.ui.search.dagger.ViewModule())
                .build()
                .inject(view);
    }

    private static class LazyHolder {
        private static final Injector INSTANCE = new Injector();
    }
}
