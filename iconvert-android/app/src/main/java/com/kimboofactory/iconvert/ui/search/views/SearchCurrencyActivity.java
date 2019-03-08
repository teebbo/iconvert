package com.kimboofactory.iconvert.ui.search.views;

import android.os.Bundle;
import android.view.View;

import com.aleengo.peach.toolbox.commons.common.PeachConfig;
import com.aleengo.peach.toolbox.ui.BaseActivity;
import com.kimboofactory.iconvert.application.IConvertApplication;
import com.kimboofactory.iconvert.ui.search.dagger.SearchModule;
import com.kimboofactory.iconvert.ui.search.presentation.MvpSearchView;

import javax.inject.Inject;

import androidx.annotation.Nullable;

public class SearchCurrencyActivity extends BaseActivity {

    @Inject
    MvpSearchView mMvpView;
    /*@Inject @Getter
    SearchPresenter presenter;*/

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
        IConvertApplication.getApplication(this)
                .daggerAppComponent()
                .searchComponentBuilder()
                .searchActivityModule(new SearchModule(this))
                //.searchViewModule(new SearchViewModule(mMvpView))
                .build()
                .inject(this);
    }

    @Override
    protected void initialize(@Nullable Bundle savedInstanceState) {
       mMvpView.init(savedInstanceState);
       //presenter.attach(mMvpView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMvpView.start();
    }

    @Override
    protected void onStop() {
        mMvpView.disconnect2EventBus();
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
