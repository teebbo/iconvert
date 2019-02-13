package com.kimboofactory.iconvert.ui.main;

import com.kimboofactory.iconvert.common.AbstractPresenter;
import com.kimboofactory.iconvert.domain.usecases.GetCurrencies;
import com.kimboofactory.iconvert.domain.usecases.GetRate;
import com.kimboofactory.iconvert.dto.CurrencyIHM;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class FavoritePresenter extends AbstractPresenter
        implements FavoriteContract.Presenter {

    private GetRate getRate;
    private GetCurrencies getCurrencies;

    public FavoritePresenter() {
    }

    @Override
    public void addCurrency() {
        ((FavoriteView) getMvpView()).showSearchActivity();
    }

    @Override
    public void result(int resultCode, Serializable data) {
        final HashMap<Integer, CurrencyIHM> currencies = (HashMap<Integer, CurrencyIHM>) data;
        ((FavoriteView) getMvpView()).updateFavoriteList(new ArrayList<>(currencies.values()));
    }

    @Override
    public void loadRate() {
        getRate = new GetRate();
    }

    @Override
    public void getCurrencies() {
        getCurrencies = new GetCurrencies();
    }
}
