package com.kimboofactory.iconvert.ui.search;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class SearchView implements SearchContract.View {

    private SearchCurrencyActivity activity;

    public SearchView() {
    }


    @Override
    public void attachUi(Object activity) {
        this.activity = (SearchCurrencyActivity) activity;
    }

    @Override
    public void notification(boolean show) {

    }
}
