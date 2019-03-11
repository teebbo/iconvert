package com.kimboofactory.iconvert.ui.home.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aleengo.peach.toolbox.commons.common.PeachConfig;
import com.aleengo.peach.toolbox.ui.BaseActivity;
import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.application.IConvertApplication;
import com.kimboofactory.iconvert.common.Constant;
import com.kimboofactory.iconvert.di.Injector;
import com.kimboofactory.iconvert.ui.home.presentation.MainPresenter;
import com.kimboofactory.iconvert.ui.home.presentation.MvpHomeView;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import lombok.Getter;


public class MainActivity extends BaseActivity {

    @Inject
    MvpHomeView mMvpView;
    @Inject @Getter
    MainPresenter presenter;

    @Override
    public String logTag() {
        return "MainActivity";
    }

    @Override
    public void daggerConfiguration() {
        Injector.instance().inject(this);
    }

    @Override
    public View getLayoutView() {
        return mMvpView;
    }

    @Override
    protected void initialize(@Nullable Bundle savedInstanceState) {
        //ButterKnife.bind(mMvpView);
        mMvpView.init(savedInstanceState);
        presenter.attach(mMvpView);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constant.SEARCH_CURRENCY_REQUEST_CODE &&
                resultCode == Activity.RESULT_OK) {
            final Serializable extras = data.getSerializableExtra(Constant.EXTRA_SELECTED_ITEMS);
            presenter.updateFavorites(resultCode, extras);
        } else if (requestCode == Constant.CHOOSE_CURRENCY_REQUEST_CODE &&
                resultCode == Activity.RESULT_OK) {
            final Serializable extra = data.getSerializableExtra(Constant.EXTRA_SELECTED_ITEM);
            presenter.updateSourceCurrency(resultCode, extra);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(mMvpView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMvpView.start();
        presenter.loadFavorites();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(mMvpView);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mMvpView.clear();
        presenter.detach();

        if (PeachConfig.isDebug()) {
            IConvertApplication.getRefWatcher(MainActivity.this).watch(MainActivity.this);
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the ConversionFragment/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
               return true;
            case R.id.action_refresh:
                final String appId = getString(R.string.openexchangerates_app_id);
                return true;
            case R.id.action_clear:
                presenter.removeFavorites();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
