package com.kimboofactory.iconvert.ui.main;

import com.aleengo.peach.toolbox.commons.model.Result;
import com.kimboofactory.iconvert.common.AbstractPresenter;
import com.kimboofactory.iconvert.domain.UseCase;
import com.kimboofactory.iconvert.domain.UseCaseHandler;
import com.kimboofactory.iconvert.domain.common.QueryValue;
import com.kimboofactory.iconvert.domain.usecases.GetCurrencies;
import com.kimboofactory.iconvert.domain.usecases.GetRate;
import com.kimboofactory.iconvert.domain.usecases.GetRatesAndCurrencies;
import com.kimboofactory.iconvert.dto.CurrencyIHM;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class MainPresenter extends AbstractPresenter
        implements FavoriteContract.Presenter {

    private GetRate mGetRateUseCase;
    private GetCurrencies mGetCurrenciesUseCase;
    private GetRatesAndCurrencies mGetRatesAndCurrenciesUseCase;
    private UseCaseHandler mUseCaseHandler;

    private boolean mFirstLoad = true;

    public MainPresenter(UseCaseHandler useCaseHandler,
                         GetCurrencies getCurrencies, GetRate getRate,
                         GetRatesAndCurrencies getRatesAndCurrencies) {
        mUseCaseHandler = useCaseHandler;
        mGetCurrenciesUseCase = getCurrencies;
        mGetRateUseCase = getRate;
        mGetRatesAndCurrenciesUseCase = getRatesAndCurrencies;
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
        //getRate.setRepository(new);
    }

    @Override
    public void loadCurrencies() {
        mUseCaseHandler.setUseCase(null, mGetCurrenciesUseCase  );
        mUseCaseHandler.execute(new UseCase.Callback() {
            @Override
            public void onResult(Result result) {

            }
        });
    }

    @Override
    public void loadRatesAndCurrencies(boolean forceUpdate) {
        final QueryValue queryVal =
                new GetRatesAndCurrencies.QueryVal(forceUpdate || mFirstLoad, null);
        mFirstLoad = false;

        mUseCaseHandler.setUseCase(queryVal, mGetRatesAndCurrenciesUseCase);
        mUseCaseHandler.execute(this::onResult);
    }

    private void onResult(Result result) {
        /* do nothing */
    }
}
