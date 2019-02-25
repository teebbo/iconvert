package com.kimboofactory.iconvert.ui.main;

import com.aleengo.peach.toolbox.commons.factory.Singleton;
import com.aleengo.peach.toolbox.commons.model.Result;
import com.kimboofactory.iconvert.common.AbstractPresenter;
import com.kimboofactory.iconvert.domain.UseCase;
import com.kimboofactory.iconvert.domain.UseCaseHandler;
import com.kimboofactory.iconvert.domain.usecases.GetCurrencies;
import com.kimboofactory.iconvert.domain.usecases.GetRate;
import com.kimboofactory.iconvert.dto.CurrencyIHM;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

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
    public void updateListView(int resultCode, Serializable data) {
        final HashMap<Integer, CurrencyIHM> currencies = (HashMap<Integer, CurrencyIHM>) data;
        ((FavoriteView) getMvpView()).updateFavoriteList(new ArrayList<>(currencies.values()));
    }

    @Override
    public void loadCurrency(CurrencyIHM currencyIHM) {
        getRate = new GetRate();
        //getRate.setRepository(new);
    }

    @Override
    public void loadCurrencies() {
        getCurrencies = new GetCurrencies();


        Singleton.of(UseCaseHandler.class).setUseCase(null, getCurrencies);
        Singleton.of(UseCaseHandler.class).execute(new UseCase.Callback() {
            @Override
            public void onResult(Result result) {

            }
        });
    }
}
