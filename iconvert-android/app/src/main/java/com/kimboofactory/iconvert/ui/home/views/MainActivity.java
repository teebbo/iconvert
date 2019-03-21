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
import com.kimboofactory.iconvert.ui.home.dagger.DaggerMainActivityComponent;
import com.kimboofactory.iconvert.ui.home.dagger.MainActivityComponent;
import com.kimboofactory.iconvert.ui.home.dagger.MainActivityModule;
import com.kimboofactory.iconvert.ui.home.presentation.MainPresenter;
import com.kimboofactory.iconvert.ui.home.presentation.MvpHomeView;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.lang.ref.WeakReference;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import lombok.Getter;


public class MainActivity extends BaseActivity {

    @Inject
    MvpHomeView mMvpView;
    @Inject @Getter
    MainPresenter presenter;

    @Getter
    private MainActivityComponent daggerComponent;

    private static WeakReference<MainActivity> activityWeakRef;

    @Override
    public String logTag() {
        return "MainActivity";
    }

    @Override
    public void daggerConfiguration() {

        activityWeakRef = new WeakReference<>(this);

        daggerComponent = DaggerMainActivityComponent.builder()
                .plus(IConvertApplication.getApplication(activityWeakRef.get()).appComponent())
                .mainActivityModule(new MainActivityModule(activityWeakRef.get()))
                .build();
        daggerComponent.inject(activityWeakRef.get());
    }

    @Override
    public View getLayoutView() {
        return mMvpView;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMvpView.init();
        presenter.attach(mMvpView);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == Constant.SEARCH_CURRENCY_REQUEST_CODE &&
                resultCode == Activity.RESULT_OK) {
            final Serializable extras = data.getSerializableExtra(Constant.EXTRA_SELECTED_ITEMS);
            presenter.updateFavorites(resultCode, extras);
        }

        if (requestCode == Constant.CHOOSE_CURRENCY_REQUEST_CODE &&
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
        super.onDestroy();

        if (PeachConfig.isDebug()) {
            IConvertApplication.getRefWatcher().watch(daggerComponent);
            IConvertApplication.getRefWatcher().watch(mMvpView);
            IConvertApplication.getRefWatcher().watch(presenter);
            IConvertApplication.getRefWatcher().watch(activityWeakRef);
        }

        mMvpView.clear();
        presenter.detach();
        daggerComponent = null;
        activityWeakRef.clear();
        activityWeakRef = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
