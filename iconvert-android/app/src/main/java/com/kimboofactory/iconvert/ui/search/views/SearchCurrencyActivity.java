package com.kimboofactory.iconvert.ui.search.views;

import android.os.Bundle;
import android.view.View;

import com.aleengo.peach.toolbox.commons.common.PeachConfig;
import com.aleengo.peach.toolbox.ui.BaseActivity;
import com.kimboofactory.iconvert.application.IConvertApplication;
import com.kimboofactory.iconvert.di.Injector;
import com.kimboofactory.iconvert.ui.search.presentation.MvpSearchView;
import com.kimboofactory.iconvert.ui.search.presentation.SearchPresenter;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import lombok.Getter;

public class SearchCurrencyActivity extends BaseActivity {

    @Inject
    MvpSearchView mMvpView;
    @Inject @Getter
    SearchPresenter presenter;

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
        // Dagger config
        Injector.instance().inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMvpView.init(savedInstanceState);
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
        mMvpView.clear();
        presenter.clear();
        if(PeachConfig.isDebug()) {
            IConvertApplication.getRefWatcher(this).watch(this);
        }
        super.onDestroy();
    }
}
