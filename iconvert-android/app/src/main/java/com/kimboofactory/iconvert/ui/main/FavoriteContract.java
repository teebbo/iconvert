package com.kimboofactory.iconvert.ui.main;

import com.kimboofactory.iconvert.common.BasePresenter;
import com.kimboofactory.iconvert.common.BaseView;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.model.RateData;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public interface FavoriteContract {

    interface Presenter extends BasePresenter {
        void loadRate();
        void getCurrencies();

        void result(int resultCode, Serializable data);
        void addCurrency();
    }

    interface View extends BaseView {
        void showSearchActivity();

        void updateFavoriteList(List<CurrencyIHM> currencies);
    }
}
