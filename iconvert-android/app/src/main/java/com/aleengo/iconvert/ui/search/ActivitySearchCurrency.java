package com.aleengo.iconvert.ui.search;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.aleengo.iconvert.R;
import com.aleengo.iconvert.application.ConvertApp;
import com.aleengo.peach.toolbox.commons.common.PeachConfig;
import com.aleengo.peach.toolbox.ui.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import lombok.Getter;

public class ActivitySearchCurrency extends BaseActivity {

    @Inject
    SearchTemplate template;
    @Inject @Getter
    SearchPresenter presenter;

    @Override
    public String logTag() {
        return "ActivitySearchCurrency";
    }

    @Override
    public View getLayoutView() {
        template.inflate(R.layout.activity_search_list);
        return template;
    }

    @Override
    public void daggerConfiguration() {
        ConvertApp.app(this)
                .searchComponent(this)
                .inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        template.initialize();
        presenter.attach(template);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(template);
        presenter.loadCurrencies();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(template);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (PeachConfig.isDebug()) {
            ConvertApp.getRefWatcher().watch(template);
            ConvertApp.getRefWatcher().watch(presenter);
        }

        template.clear();
        presenter.clear();
        ConvertApp.app(this).releaseSearchComponent();
    }
}
