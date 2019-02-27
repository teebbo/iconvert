package com.kimboofactory.iconvert.ui.main;

import com.aleengo.peach.toolbox.commons.model.Result;
import com.kimboofactory.iconvert.common.AbstractPresenter;
import com.kimboofactory.iconvert.domain.UseCase;
import com.kimboofactory.iconvert.domain.UseCaseHandler;
import com.kimboofactory.iconvert.domain.common.QueryValue;
import com.kimboofactory.iconvert.domain.usecases.GetCurrencies;
import com.kimboofactory.iconvert.domain.usecases.GetCurrency;
import com.kimboofactory.iconvert.domain.usecases.GetRate;
import com.kimboofactory.iconvert.domain.usecases.GetRatesAndCurrencies;
import com.kimboofactory.iconvert.dto.CurrencyIHM;

import org.greenrobot.eventbus.EventBus;

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
    private GetCurrency mGetCurrencyUseCase;
    private GetRatesAndCurrencies mGetRatesAndCurrenciesUseCase;
    private UseCaseHandler mUseCaseHandler;

    private boolean mFirstLoad = true;

    public MainPresenter(UseCaseHandler useCaseHandler,
                         GetCurrencies getCurrencies,
                         GetCurrency getCurrency,
                         GetRate getRate,
                         GetRatesAndCurrencies getRatesAndCurrencies) {
        mUseCaseHandler = useCaseHandler;
        mGetCurrenciesUseCase = getCurrencies;
        mGetCurrencyUseCase = getCurrency;
        mGetRateUseCase = getRate;
        mGetRatesAndCurrenciesUseCase = getRatesAndCurrencies;
    }

    @Override
    public void addFavorite(int requestCode) {
        ((MainView) getMvpView()).showSearchActivity(requestCode);
    }

    @Override
    public void updateFavorites(int resultCode, Serializable data) {
        final HashMap<Integer, CurrencyIHM> currencies = (HashMap<Integer, CurrencyIHM>) data;
        ((MainView) getMvpView()).updateFavoritesList(new ArrayList<>(currencies.values()));
    }

    @Override
    public void updateSourceCurrency(int resultCode, Serializable data) {
        final CurrencyIHM item = (CurrencyIHM) data;
        ((MainView) getMvpView()).updateSourceCurrency(item);
    }

    @Override
    public void loadCurrency(CurrencyIHM currencyIHM) {
        //getRate.setRepository(new);
    }

    @Override
    public void loadDefaultCurrency() {
        mUseCaseHandler.setUseCase(new QueryValue("USD"), mGetCurrencyUseCase);
        mUseCaseHandler.execute(result -> EventBus.getDefault().post(result));
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
