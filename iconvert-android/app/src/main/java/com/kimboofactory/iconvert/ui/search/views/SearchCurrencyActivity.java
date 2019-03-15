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
        daggerComponent = DaggerSearchActivityComponent.builder()
                .plus(IConvertApplication.getApplication(this).appComponent())
                .searchActivityModule(new SearchActivityModule(this))
                .build();
        daggerComponent.inject(this);
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
        mMvpView.clear();
        presenter.clear();
        daggerComponent = null;
        if(PeachConfig.isDebug()) {
            IConvertApplication.getRefWatcher(this).watch(this);
        }
    }
}
