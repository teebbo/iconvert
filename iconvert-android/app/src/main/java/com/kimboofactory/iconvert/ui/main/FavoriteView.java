package com.kimboofactory.iconvert.ui.main;

import android.content.Intent;

import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.ui.search.SearchCurrencyActivity;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class FavoriteView implements FavoriteContract.View {

    private MainActivity activity;

    public FavoriteView() {
    }

    @Override
    public void attachUi(Object activity) {
        this.activity = (MainActivity) activity;
    }

    @Override
    public void showSearchActivity() {
        final Intent intent = new Intent(this.activity, SearchCurrencyActivity.class);
        this.activity.startActivityForResult(intent, MainActivity.SEARCH_CURRENCY_REQUEST_CODE);
    }

    @Override
    public void updateFavoriteList(List<CurrencyIHM> currencies) {
        //activity.getFavoritesAdapter().clear();
        activity.getFavoritesAdapter().updateDataSet(currencies);
    }
}
