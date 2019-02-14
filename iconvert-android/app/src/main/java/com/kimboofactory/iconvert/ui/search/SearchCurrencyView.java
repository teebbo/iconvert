package com.kimboofactory.iconvert.ui.search;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.util.Helper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class SearchCurrencyView implements SearchContract.View {

    private SearchCurrencyActivity activity;

    public SearchCurrencyView() {
    }


    @Override
    public void attachUi(Object activity) {
        this.activity = (SearchCurrencyActivity) activity;
    }

    @Override
    public void filter(String query) {
        this.activity.getAdapter().getFilter().filter(query);
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

    private void performedClick(List<CurrencyIHM> items) {
        final Intent data = new Intent();

        final HashMap<Integer, CurrencyIHM> finalItems = new LinkedHashMap<>();
        for (int ix = 0; ix < items.size(); ix++) {
            finalItems.put(ix, items.get(ix));
        }

        data.putExtra(Helper.EXTRA_SELECTED_ITEM, finalItems);
        activity.setResult(Activity.RESULT_OK, data);
        activity.finish();
    }
}
