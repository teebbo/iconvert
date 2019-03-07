package com.kimboofactory.iconvert.ui.search.presentation;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class SearchCurrencyView
        //implements SearchContract.View
{
/*
    private SearchCurrencyActivity activity;

    @Inject
    public SearchCurrencyView(SearchCurrencyActivity activity) {
        this.activity = activity;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Result event) {
        if (event.getError() != null) {
            // show an error message
            showMessage(R.string.error_message_load_currencies);
            activity.getSwipeRefreshLayout().setRefreshing(false);
            return;
        }

        final List<CurrencyEntity> currenciesEntity = (List<CurrencyEntity>) event.getValue();
        activity.getAdapter().clear();
        activity.getAdapter().updateItems(
                currenciesEntity.stream()
                        .map(entity -> {
                            final CurrencyIHM item = new CurrencyIHM(entity);
                            item.setCode(activity.getRequestCode());
                            return item;
                        })
                        .collect(Collectors.toList())
        );
        // stop the refresh
        activity.getSwipeRefreshLayout().setRefreshing(false);
    }

    @Override
    public void showMessage(int resId) {
        final Snackbar snackbar = Snackbar
                .make(activity.getCoordinatorLayout(), resId, Snackbar.LENGTH_INDEFINITE);

        snackbar.setActionTextColor(Color.CYAN)
                .setAction(R.string.snackbar_retry_action, (View v) -> {
                    activity.getPresenter().loadCurrencies();
                    snackbar.dismiss();
                }).show();
    }

    @Override
    public void attachUi(Object activity) {
        this.activity = (SearchCurrencyActivity) activity;
    }

    @Override
    public void filter(String query) {
        getFilter().filter(query);
    }

    @Override
    public void toggleSnackbar(boolean show, final List<CurrencyIHM> items) {

        if (show) {
            final String message = activity.getResources()
                    .getString(R.string.snackbar_message, "" + items.size());

            activity.getSnackbar().setText(message);
            activity.getSnackbar().setAction(R.string.snackbar_add_action, (View v) -> performedClick(items));
            activity.getSnackbar().setActionTextColor(Color.CYAN);
            activity.getSnackbar().show();
        } else {
            activity.getSnackbar().dismiss();
        }
    }

    @Override
    public void showCurrency(CurrencyIHM item) {
        final Intent data = new Intent();
        data.putExtra(Helper.EXTRA_SELECTED_ITEM, item);

        activity.setResult(Activity.RESULT_OK, data);
        activity.finish();
    }

    private void performedClick(List<CurrencyIHM> items) {
        final Intent data = new Intent();

        final HashMap<Integer, CurrencyIHM> finalItems = (HashMap<Integer, CurrencyIHM>)
                items.stream()
                        .collect(Collectors.toMap(item -> items.indexOf(item), item -> item));

        data.putExtra(Helper.EXTRA_SELECTED_ITEMS, finalItems);
        activity.setResult(Activity.RESULT_OK, data);
        activity.finish();
    }

    private Filter getFilter() {
        return this.activity.getAdapter().getFilter();
    }*/

}
