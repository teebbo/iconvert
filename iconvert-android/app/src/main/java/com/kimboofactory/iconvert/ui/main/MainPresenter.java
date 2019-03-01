package com.kimboofactory.iconvert.ui.main;

import com.aleengo.peach.toolbox.commons.model.Result;
import com.kimboofactory.iconvert.GetFavoriteEvent;
import com.kimboofactory.iconvert.common.AbstractPresenter;
import com.kimboofactory.iconvert.domain.UseCase;
import com.kimboofactory.iconvert.domain.UseCaseHandler;
import com.kimboofactory.iconvert.domain.common.QueryValue;
import com.kimboofactory.iconvert.domain.model.CurrencyEntity;
import com.kimboofactory.iconvert.domain.model.FavoriteEntity;
import com.kimboofactory.iconvert.domain.usecases.DeleteFavorites;
import com.kimboofactory.iconvert.domain.usecases.GetCurrencies;
import com.kimboofactory.iconvert.domain.usecases.GetCurrency;
import com.kimboofactory.iconvert.domain.usecases.GetFavorites;
import com.kimboofactory.iconvert.domain.usecases.GetRate;
import com.kimboofactory.iconvert.domain.usecases.GetRatesAndCurrencies;
import com.kimboofactory.iconvert.domain.usecases.SaveFavorite;
import com.kimboofactory.iconvert.domain.usecases.SaveFavorites;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.util.Helper;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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
    private GetFavorites getFavoritesUseCase;
    private SaveFavorite saveFavoriteUseCase;
    private SaveFavorites saveFavoritesUseCase;
    private DeleteFavorites deleteFavorites;

    private UseCaseHandler mUseCaseHandler;

    private boolean mFirstLoad = true;

    public MainPresenter(UseCaseHandler useCaseHandler,
                         GetCurrencies getCurrencies,
                         GetCurrency getCurrency,
                         GetRate getRate,
                         GetRatesAndCurrencies getRatesAndCurrencies,
                         GetFavorites getFavorites,
                         SaveFavorite saveFavorite,
                         SaveFavorites saveFavorites,
                         DeleteFavorites deleteFavorites) {
        mUseCaseHandler = useCaseHandler;
        mGetCurrenciesUseCase = getCurrencies;
        mGetCurrencyUseCase = getCurrency;
        mGetRateUseCase = getRate;
        mGetRatesAndCurrenciesUseCase = getRatesAndCurrencies;
        this.getFavoritesUseCase = getFavorites;
        this.saveFavoritesUseCase = saveFavorites;
        this.saveFavoriteUseCase = saveFavorite;
        this.deleteFavorites = deleteFavorites;
    }

    @Override
    public void addFavorite(int requestCode) {
        ((MainView) getMvpView()).showSearchActivity(requestCode);
    }

    @Override
    public void removeFavorites() {
        ((MainView) getMvpView()).updateFavoritesList(Helper.CURRENCY_IHMS_EMPTY_LIST);

        mUseCaseHandler.setUseCase(Helper.NO_QUERY, deleteFavorites);
        mUseCaseHandler.execute(this::doNothing);
    }

    @Override
    public void updateFavorites(int resultCode, Serializable data) {
        final HashMap<Integer, CurrencyIHM> currencies = (HashMap<Integer, CurrencyIHM>) data;

        final List<CurrencyIHM> list = new ArrayList<>(currencies.values());
        ((MainView) getMvpView()).updateFavoritesList(list);

        // clean the favorite table
        removeFavorites();

        // save the new ones
        saveFavorites(((MainView) getMvpView()).getActivity().getFavoritesAdapter().getItems());
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
        mUseCaseHandler.setUseCase(Helper.DEFAULT_QUERY, mGetCurrencyUseCase);
        mUseCaseHandler.execute(result -> EventBus.getDefault().post(result));
    }

    @Override
    public void loadCurrencies() {
        mUseCaseHandler.setUseCase(Helper.NO_QUERY, mGetCurrenciesUseCase);
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
        mUseCaseHandler.execute(this::doNothing);
    }

    @Override
    public void loadFavorites() {
        mUseCaseHandler.setUseCase(Helper.NO_QUERY, getFavoritesUseCase);
        mUseCaseHandler.execute(new UseCase.Callback() {
            @Override
            public void onResult(Result result) {
                final List<FavoriteEntity> list = (List<FavoriteEntity>) result.getValue();
                if (list.size() > 0) {
                    final List<CurrencyIHM> currencies = list.stream()
                            .map(e -> {
                                final CurrencyIHM currencyIHM = new CurrencyIHM(e.getDest());
                                currencyIHM.setComputeRate(e.getComputedRate());
                                currencyIHM.setAmount(e.getComputedAmount());
                                return currencyIHM;
                            }).collect(Collectors.toList());

                    final FavoriteEntity f = list.get(0);
                    final CurrencyIHM source = new CurrencyIHM(f.getSource());
                    if (f.getComputedRate() != null) {
                        final Float cachedAmount = Float.valueOf(f.getComputedAmount()) / Float.valueOf(f.getComputedRate());
                        source.setAmount(String.format(Locale.US, "%.2f", cachedAmount.floatValue()));
                    } else {
                        source.setAmount("1");
                    }
                    EventBus.getDefault().post(new GetFavoriteEvent(source, currencies));
                }
            }
        });
    }

    @Override
    public void saveFavorites(List<CurrencyIHM> currencies) {
        final CurrencyIHM source = ((MainView) getMvpView()).getActivity().getCurrencySource();

        final List<FavoriteEntity> favorites = currencies.stream()
                .map(currencyIHM ->  new FavoriteEntity(source.getEntity(),
                        currencyIHM.getEntity(), currencyIHM.getComputeRate(), currencyIHM.getAmount()))
                .collect(Collectors.toList());

        final SaveFavorites.QueryVal values = new SaveFavorites.QueryVal(favorites);
        mUseCaseHandler.setUseCase(values, saveFavoritesUseCase);
        mUseCaseHandler.execute(this::doNothing);
    }

    @Override
    public void saveFavorite(CurrencyIHM currencyIHM) {
        final CurrencyIHM source = ((MainView) getMvpView()).getActivity().getCurrencySource();

        final FavoriteEntity favorite = new FavoriteEntity(source.getEntity(),
                currencyIHM.getEntity(), currencyIHM.getComputeRate(), currencyIHM.getAmount());

        final SaveFavorite.QueryVal value = new SaveFavorite.QueryVal(favorite);
        mUseCaseHandler.setUseCase(value, saveFavoriteUseCase);
        mUseCaseHandler.execute(this::doNothing);
    }

    private void doNothing(Result result) {
        /* do nothing */
    }
}
