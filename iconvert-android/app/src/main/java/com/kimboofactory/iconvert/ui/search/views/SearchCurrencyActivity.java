package com.kimboofactory.iconvert.ui.search.views;

import android.os.Bundle;
import android.view.View;

import com.aleengo.peach.toolbox.commons.common.PeachConfig;
import com.aleengo.peach.toolbox.ui.BaseActivity;
import com.kimboofactory.iconvert.application.IConvertApplication;
import com.kimboofactory.iconvert.ui.search.dagger.DaggerSearchActivityComponent;
import com.kimboofactory.iconvert.ui.search.dagger.SearchActivityComponent;
import com.kimboofactory.iconvert.ui.search.dagger.SearchActivityModule;
import com.kimboofactory.iconvert.ui.search.presentation.MvpSearchView;
import com.kimboofactory.iconvert.ui.search.presentation.SearchPresenter;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import lombok.Getter;

public class SearchCurrencyActivity extends BaseActivity {

    @Inject
    MvpSearchView mMvpView;
    @Inject @Getter
    SearchPresenter presenter;

    @Getter
    private SearchActivityComponent daggerComponent;

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
                .plus(IConvertApplication.getApplication(activityWeakRef.get()).appComponent())
                .searchActivityModule(new SearchActivityModule(activityWeakRef.get()))
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
            IConvertApplication.getRefWatcher().watch(daggerComponent);
            IConvertApplication.getRefWatcher().watch(mMvpView);
            IConvertApplication.getRefWatcher().watch(presenter);
            IConvertApplication.getRefWatcher().watch(activityWeakRef);
        }

        mMvpView.clear();
        presenter.clear();
        daggerComponent = null;
        activityWeakRef.clear();
        activityWeakRef = null;
    }
}
