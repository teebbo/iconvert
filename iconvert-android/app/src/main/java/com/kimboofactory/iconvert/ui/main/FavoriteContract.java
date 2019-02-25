package com.kimboofactory.iconvert.ui.main;

import com.kimboofactory.iconvert.common.BasePresenter;
import com.kimboofactory.iconvert.common.BaseView;
import com.kimboofactory.iconvert.dto.CurrencyIHM;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public interface FavoriteContract {

    interface Presenter extends BasePresenter {
        void loadCurrency(CurrencyIHM currencyIHM);
        void loadCurrencies();

        void updateListView(int resultCode, Serializable data);
        void addCurrency();
    }

    interface View extends BaseView {
        void showSearchActivity();

        void updateFavoriteList(List<CurrencyIHM> currencies);
    }
}
