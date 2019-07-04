package com.aleengo.iconvert.ui.search;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.aleengo.iconvert.application.ConvertApp;
import com.aleengo.iconvert.di.search.SearchComponent;
import com.aleengo.iconvert.di.search.SearchModule;
import com.aleengo.iconvert.ui.search.dagger.DaggerSearchActivityComponent;
import com.aleengo.peach.toolbox.commons.common.PeachConfig;
import com.aleengo.peach.toolbox.ui.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import lombok.Getter;

public class SearchCurrencyActivity extends BaseActivity {

    @Inject
    SearchTemplate mMvpView;
    @Inject @Getter
    SearchPresenter presenter;

    @Getter
    private SearchComponent daggerComponent;

    private static WeakReference<SearchCurrencyActivity> activityWeakRef;

    @Override
    public String logTag() {
        return "SearchCurrencyActivity";
    }

    @Override
    public View getLayoutView() {
        return mMvpView;
    }

    @Override
    public void daggerConfiguration() {

        activityWeakRef = new WeakReference<>(this);

        // Dagger config
        daggerComponent = DaggerSearchActivityComponent.builder()
                .plus(ConvertApp.getApplication(activityWeakRef.get()).appComponent())
                .searchActivityModule(new SearchModule(activityWeakRef.get()))
                .build();
        daggerComponent.inject(activityWeakRef.get());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMvpView.init();
        presenter.attach(mMvpView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(mMvpView);
        presenter.loadCurrencies();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(mMvpView);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (PeachConfig.isDebug()) {
            ConvertApp.getRefWatcher().watch(daggerComponent);
            ConvertApp.getRefWatcher().watch(mMvpView);
            ConvertApp.getRefWatcher().watch(presenter);
            ConvertApp.getRefWatcher().watch(activityWeakRef);
        }

        mMvpView.clear();
        presenter.clear();
        daggerComponent = null;
        activityWeakRef.clear();
        activityWeakRef = null;
    }
}
