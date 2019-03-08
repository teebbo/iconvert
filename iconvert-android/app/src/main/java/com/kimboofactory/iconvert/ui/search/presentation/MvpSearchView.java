package com.kimboofactory.iconvert.ui.search.presentation;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.aleengo.peach.toolbox.commons.model.Result;
import com.aleengo.peach.toolbox.widget.PeachToolbar;
import com.google.android.material.snackbar.Snackbar;
import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.common.Constant;
import com.kimboofactory.iconvert.domain.model.CurrencyEntity;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.ui.search.views.SearchCurrencyActivity;
import com.kimboofactory.iconvert.ui.search.views.SearchCurrencyAdapter;
import com.kimboofactory.iconvert.ui.search.views.SearchItemView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;

import static android.content.Context.SEARCH_SERVICE;

/**
 * Created by CK_ALEENGO on 07/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class MvpSearchView extends FrameLayout implements SearchContract.View, SearchView.OnQueryTextListener {

    public static final int NO_EXTRA = -1;

    @Getter
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @Getter
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.search_toolbar)
    PeachToolbar toolbar;
    @BindView(R.id.iv_back)
    ImageView mBack;
    @BindView(R.id.sv_search)
    SearchView mSearchView;
    @BindView(R.id.lv_currencies)
    ListView searchCurrencyLV;

    @Getter
    private Snackbar snackbar;

    @Inject @Getter
    SearchCurrencyAdapter adapter;
    @Inject
    Integer requestCode;
    @Inject @Getter
    SearchPresenter presenter;

    private SearchCurrencyActivity activity;

    @Inject
    public MvpSearchView(SearchCurrencyActivity activity) {
        super(activity);
        this.activity = activity;
        inflate(getContext(), R.layout.activity_search_list, this);

        presenter.attach(this);
    }

    public void swipeRefresh(boolean refresh) {
        swipeRefreshLayout.setRefreshing(refresh);
    }

    public void start() {
        swipeRefresh(true);
        connect2EventBus();
        presenter.start();
    }

    // free resources
    public void clear() {
        snackbar = null;
        adapter = null;
        presenter.clear();
    }

    public void init(@Nullable Bundle savedInstanceState) {

        // dagger init
        ButterKnife.bind(this);

        activity.setSupportActionBar(toolbar);

        requestCode = activity.getIntent().getIntExtra(Constant.REQUEST_CODE, NO_EXTRA);

        //adapter = new SearchCurrencyAdapter(activity, new LinkedList<>(), requestCode);
        searchCurrencyLV.setAdapter(adapter);
        searchCurrencyLV.setOnItemClickListener(this::OnItemClick);

        setupSearchView();

        mBack.setOnClickListener( (View v) -> activity.onBackPressed());
        snackbar = Snackbar.make(coordinatorLayout, Constant.EMPTY_STRING, Snackbar.LENGTH_INDEFINITE);

        // setting SwipeRefresh
        setupSwipeRefreshContainer();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Result event) {
        if (event.getError() != null) {
            // show an error message
            showMessage(R.string.error_message_load_currencies);
            swipeRefresh(false);
            return;
        }

        final List<CurrencyEntity> currenciesEntity = (List<CurrencyEntity>) event.getValue();
        adapter.clear();
        adapter.updateItems(
                currenciesEntity.stream()
                        .map(entity -> {
                            final CurrencyIHM item = new CurrencyIHM(entity);
                            item.setCode(requestCode);
                            return item;
                        })
                        .collect(Collectors.toList())
        );
        // stop the refresh
        swipeRefresh(false);
    }

    @Override
    public void showMessage(int resId) {
        final Snackbar snackbar = Snackbar
                .make(getCoordinatorLayout(), resId, Snackbar.LENGTH_INDEFINITE);

        snackbar.setActionTextColor(Color.CYAN)
                .setAction(R.string.snackbar_retry_action, (View v) -> {
                    presenter.loadCurrencies();
                    snackbar.dismiss();
                }).show();
    }

    @Override
    public void attachUi(Object activity) {
        this.activity = (SearchCurrencyActivity) activity;
    }

    @Override
    public void filter(String query) {
        adapter.getFilter().filter(query);
    }

    @Override
    public void toggleSnackbar(boolean show, final List<CurrencyIHM> items) {

        if (show) {
            final String message = activity.getResources()
                    .getString(R.string.snackbar_message, "" + items.size());

            snackbar.setText(message);
            snackbar.setAction(R.string.snackbar_add_action, (View v) -> performedClick(items));
            snackbar.setActionTextColor(Color.CYAN);
            snackbar.show();
        } else {
            snackbar.dismiss();
        }
    }

    @Override
    public void showCurrency(CurrencyIHM item) {
        final Intent data = new Intent();
        data.putExtra(Constant.EXTRA_SELECTED_ITEM, item);

        activity.setResult(Activity.RESULT_OK, data);
        activity.finish();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }

    private void setupSwipeRefreshContainer() {

        final Runnable runnable = presenter::loadCurrencies;

        final SwipeRefreshLayout.OnRefreshListener onRefreshListener =
                () -> (new Handler()).postDelayed(runnable, Constant.DELAY_MILLIS_2000);

        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        // configure the refreshing colors
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void setupSearchView() {
        final SearchManager searchManager = (SearchManager) getContext().getSystemService(SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(activity.getComponentName()));

        mSearchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mSearchView.setOnQueryTextListener(this);
    }

    private void performedClick(List<CurrencyIHM> items) {
        final Intent data = new Intent();

        final HashMap<Integer, CurrencyIHM> finalItems = (HashMap<Integer, CurrencyIHM>)
                items.stream()
                        .collect(Collectors.toMap(items::indexOf, item -> item));

        data.putExtra(Constant.EXTRA_SELECTED_ITEMS, finalItems);
        activity.setResult(Activity.RESULT_OK, data);
        activity.finish();
    }

    private void OnItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        final SearchItemView itemView = (SearchItemView) view;
        itemView.getCheckBox().setChecked(!itemView.getCheckBox().isChecked());
        itemView.getRadioButton().setChecked(!itemView.getRadioButton().isChecked());

        final CurrencyIHM currencyIHM = (CurrencyIHM) adapter.getItem(position);
        currencyIHM.setCheckboxChecked(itemView.getCheckBox().isChecked());
        currencyIHM.setRadioButtonChecked(itemView.getRadioButton().isChecked());

        adapter.notifyDataSetChanged();

        switch (requestCode) {
            case Constant.SEARCH_CURRENCY_REQUEST_CODE:
                presenter.itemSelectedCheckbox(currencyIHM);
                break;
            case Constant.CHOOSE_CURRENCY_REQUEST_CODE:
                presenter.itemSelectedRadioButton(currencyIHM);
                break;
            default:
                break;
        }
    }
}
