package com.aleengo.iconvert.ui.search;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.aleengo.iconvert.R;
import com.aleengo.iconvert.application.Constant;
import com.aleengo.iconvert.dto.CurrencyIHM;
import com.aleengo.iconvert.events.CurrenciesEvent;
import com.aleengo.iconvert.ui.base.MvpView;
import com.aleengo.peach.toolbox.widget.PeachToolbar;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import butterknife.BindView;
import lombok.Getter;

import static android.content.Context.SEARCH_SERVICE;

/**
 * Created by CK_ALEENGO on 07/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class SearchTemplate extends MvpView
        implements SearchContract.Template, SearchView.OnQueryTextListener {

    @BindView(R.id.coordinator_layout)
    @Getter
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.swipe_refresh_layout)
    @Getter
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
    @Inject @Getter
    Integer requestCode;

    private WeakReference<SearchCurrencyActivity> activityWeakRef;

    @Inject
    public SearchTemplate(SearchCurrencyActivity activity) {
        super(activity);
        this.activityWeakRef = new WeakReference<>(activity);
        inflate(getContext(), R.layout.activity_search_list, this);
    }

    public void swipeRefresh(boolean refresh) {
        swipeRefreshLayout.setRefreshing(refresh);
    }

    // free resources
    @Override
    public void clear() {
        super.clear();
        snackbar = null;
        activityWeakRef.clear();
        activityWeakRef = null;
    }

    @Override
    public void initialize() {
        activityWeakRef.get().getDaggerComponent().viewComponentBuilder()
                .viewModule(new ViewModule())
                .build()
                .inject(this);

        activityWeakRef.get().setSupportActionBar(toolbar);

        searchCurrencyLV.setAdapter(adapter);
        searchCurrencyLV.setOnItemClickListener(this::OnItemClick);

        setupSearchView();

        mBack.setOnClickListener( (View v) -> activityWeakRef.get().onBackPressed());
        snackbar = Snackbar.make(coordinatorLayout, Constant.EMPTY_STRING, Snackbar.LENGTH_INDEFINITE);

        // setting SwipeRefresh
        setupSwipeRefreshContainer();

        swipeRefresh(true);
    }

    @Override
    public void inflate(int resid) {
        inflate(getContext(), resid, this);
    }

    public void init() {
        // dagger init
        activityWeakRef.get().getDaggerComponent().viewComponentBuilder()
                .viewModule(new ViewModule())
                .build()
                .inject(this);

        activityWeakRef.get().setSupportActionBar(toolbar);

        searchCurrencyLV.setAdapter(adapter);
        searchCurrencyLV.setOnItemClickListener(this::OnItemClick);

        setupSearchView();

        mBack.setOnClickListener( (View v) -> activityWeakRef.get().onBackPressed());
        snackbar = Snackbar.make(coordinatorLayout, Constant.EMPTY_STRING, Snackbar.LENGTH_INDEFINITE);

        // setting SwipeRefresh
        setupSwipeRefreshContainer();

        swipeRefresh(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CurrenciesEvent event) {
        if (event.getError() != null) {
            // show an error message
            showMessage(R.string.error_message_load_currencies);
            swipeRefresh(false);
            return;
        }
        adapter.clear();
        adapter.updateItems(event.getData());
        // stop the refresh
        swipeRefresh(false);
    }

    @Override
    public void showMessage(int resId) {
        final Snackbar snackbar = Snackbar
                .make(getCoordinatorLayout(), resId, Snackbar.LENGTH_INDEFINITE);

        snackbar.setActionTextColor(Color.CYAN)
                .setAction(R.string.snackbar_retry_action, (View v) -> {
                    activityWeakRef.get().getPresenter().loadCurrencies();
                    snackbar.dismiss();
                }).show();
    }

    @Override
    public void filter(String query) {
        adapter.getFilter().filter(query);
    }

    @Override
    public void toggleSnackbar(boolean show, final List<CurrencyIHM> items) {

        if (show) {
            final String message = activityWeakRef.get().getResources()
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

        activityWeakRef.get().setResult(Activity.RESULT_OK, data);
        activityWeakRef.get().finish();
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

        final Runnable runnable = activityWeakRef.get().getPresenter()::loadCurrencies;

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
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(activityWeakRef.get().getComponentName()));

        mSearchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mSearchView.setOnQueryTextListener(this);
    }

    private void performedClick(List<CurrencyIHM> items) {
        final Intent data = new Intent();

        final HashMap<Integer, CurrencyIHM> finalItems = (HashMap<Integer, CurrencyIHM>)
                items.stream()
                        .collect(Collectors.toMap(items::indexOf, item -> item));

        data.putExtra(Constant.EXTRA_SELECTED_ITEMS, finalItems);
        activityWeakRef.get().setResult(Activity.RESULT_OK, data);
        activityWeakRef.get().finish();
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
                activityWeakRef.get().getPresenter().itemSelectedCheckbox(currencyIHM);
                break;
            case Constant.CHOOSE_CURRENCY_REQUEST_CODE:
                activityWeakRef.get().getPresenter().itemSelectedRadioButton(currencyIHM);
                break;
            default:
                break;
        }
    }
}
