package com.kimboofactory.iconvert.ui.search.views;

import android.os.Bundle;
import android.view.View;

import com.aleengo.peach.toolbox.commons.common.PeachConfig;
import com.aleengo.peach.toolbox.ui.BaseActivity;
import com.kimboofactory.iconvert.application.IConvertApplication;
import com.kimboofactory.iconvert.di.Injector;
import com.kimboofactory.iconvert.ui.search.presentation.MvpSearchView;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import androidx.annotation.Nullable;

public class SearchCurrencyActivity extends BaseActivity {

    @Inject
    MvpSearchView mMvpView;

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
    protected void initialize(@Nullable Bundle savedInstanceState) {
        //ButterKnife.bind(mMvpView);
        mMvpView.init(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(mMvpView);
        mMvpView.start();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(mMvpView);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mMvpView.clear();
        if(PeachConfig.isDebug()) {
            IConvertApplication.getRefWatcher(this).watch(this);
        }
        super.onDestroy();
    }
}
