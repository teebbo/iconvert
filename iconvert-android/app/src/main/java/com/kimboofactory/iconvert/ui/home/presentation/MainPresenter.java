package com.kimboofactory.iconvert.ui.home.presentation;

import com.aleengo.peach.toolbox.commons.model.Response;
import com.aleengo.peach.toolbox.commons.model.Result;
import com.kimboofactory.iconvert.GetFavoriteEvent;
import com.kimboofactory.iconvert.common.AbstractPresenter;
import com.kimboofactory.iconvert.common.Constant;
import com.kimboofactory.iconvert.domain.Repository;
import com.kimboofactory.iconvert.domain.UseCase;
import com.kimboofactory.iconvert.domain.UseCaseHandler;
import com.kimboofactory.iconvert.domain.common.QueryValue;
import com.kimboofactory.iconvert.domain.model.FavoriteEntity;
import com.kimboofactory.iconvert.domain.usecases.DeleteFavorite;
import com.kimboofactory.iconvert.domain.usecases.DeleteFavorites;
import com.kimboofactory.iconvert.domain.usecases.GetCurrencies;
import com.kimboofactory.iconvert.domain.usecases.GetCurrency;
import com.kimboofactory.iconvert.domain.usecases.GetFavorites;
import com.kimboofactory.iconvert.domain.usecases.GetRate;
import com.kimboofactory.iconvert.domain.usecases.GetRatesAndCurrencies;
import com.kimboofactory.iconvert.domain.usecases.SaveFavorite;
import com.kimboofactory.iconvert.domain.usecases.SaveFavorites;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.persistence.repository.CurrencyRepository;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.inject.Inject;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class MainPresenter extends AbstractPresenter<MvpHomeView>
        implements FavoriteContract.Presenter {

    private Repository repository;
    private GetRate mGetRateUseCase;
    private GetCurrencies mGetCurrenciesUseCase;
    private GetCurrency mGetCurrencyUseCase;
    private GetRatesAndCurrencies mGetRatesAndCurrenciesUseCase;
    private GetFavorites getFavoritesUseCase;
    private SaveFavorite saveFavoriteUseCase;
    private SaveFavorites saveFavoritesUseCase;
    private DeleteFavorites deleteFavorites;
    private DeleteFavorite deleteFavorite;

    private UseCaseHandler mUseCaseHandler;

    private boolean mFirstLoad = true;

    @Inject
    public MainPresenter(CurrencyRepository repository) {
       this.repository = repository;
    }

    @Override
    public void addFavorite(int requestCode) {
        getMvpView().showSearchActivity(requestCode);
    }

    @Override
    public void removeFavorites() {

        /*mUseCaseHandler.setUseCase(Helper.NO_QUERY, deleteFavorites);
        mUseCaseHandler.execute(this::doNothing);*/
        repository.removeFavorites();
        getMvpView().updateFavoritesList(new ArrayList<>(0));
    }

    @Override
    public void removeFavorite(int position) {
        // update the ui
        final CurrencyIHM itemToDelete = getMvpView().removeFavoriteAt(position);

        // update the database
        final FavoriteEntity favorite = new FavoriteEntity(null, itemToDelete.getEntity(),
                itemToDelete.getComputeRate(), itemToDelete.getAmount());

       /* mUseCaseHandler.setUseCase(new DeleteFavorite.QueryVal(favorite), deleteFavorite);
        mUseCaseHandler.execute(this::doNothing);*/
       repository.removeFavorite(favorite);
    }

    @Override
    public void updateFavorites(int resultCode, Serializable data) {
        final HashMap<Integer, CurrencyIHM> currencies = (HashMap<Integer, CurrencyIHM>) data;

        final List<CurrencyIHM> list = new ArrayList<>(currencies.values());

        list.addAll(list.size(), getMvpView().getAdapterItems());
        getMvpView().updateFavoritesList(list);

        // save the new ones
        saveFavorites(getMvpView().getAdapterItems());
    }

    @Override
    public void updateSourceCurrency(int resultCode, Serializable data) {
        final CurrencyIHM item = (CurrencyIHM) data;

        // update the source and the adapter based on the new source
        getMvpView().updateSourceCurrency(item);

        // update the database based on the new source
        saveFavorites(getMvpView().getAdapterItems());
    }

    @Override
    public void loadCurrency(CurrencyIHM currencyIHM) {
        final QueryValue<String> query = new QueryValue<>(currencyIHM.getEntity().getCode());
        mUseCaseHandler.setUseCase(query, mGetCurrencyUseCase);
        mUseCaseHandler.execute(result -> EventBus.getDefault().post(result));
    }

    @Override
    public void loadDefaultCurrency() {
        mUseCaseHandler.setUseCase(Constant.DEFAULT_QUERY, mGetCurrencyUseCase);
        mUseCaseHandler.execute(result -> EventBus.getDefault().post(result));
    }

    @Override
    public void loadCurrencies() {
        mUseCaseHandler.setUseCase(Constant.NO_QUERY, mGetCurrenciesUseCase);
        mUseCaseHandler.execute(new UseCase.Callback() {
            @Override
            public void onResult(Result result) {

            }
        });
    }

    @Override
    public void loadRatesAndCurrencies(boolean forceUpdate) {
       /* final QueryValue queryVal =
                new GetRatesAndCurrencies.QueryVal(forceUpdate || mFirstLoad, null);
        mFirstLoad = false;*/

        if (forceUpdate || mFirstLoad) {
            repository.addRatesAndCurrencies();
            mFirstLoad = false;
        }
        /*mUseCaseHandler.setUseCase(queryVal, mGetRatesAndCurrenciesUseCase);
        mUseCaseHandler.execute(this::doNothing);*/
    }

    @Override
    public void loadFavorites() {
        /*mUseCaseHandler.setUseCase(Helper.NO_QUERY, getFavoritesUseCase);
        mUseCaseHandler.execute(new UseCase.Callback() {
            @Override
            public void onResult(Result result) {

            }
        });*/

        repository.getFavorites( response -> {
            final List<FavoriteEntity> list = (List<FavoriteEntity>) response.getValue();
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
                    source.setAmount(String.format(Locale.US, Constant.FORMAT_AMOUNT, cachedAmount.floatValue()));
                } else {
                    source.setAmount(Constant.DEFAULT_AMOUNT);
                }
                EventBus.getDefault().post(new GetFavoriteEvent(source, currencies));
            }
        });
    }

    @Override
    public void saveFavorites(List<CurrencyIHM> currencies) {
        final CurrencyIHM source = getMvpView().getCurrencySource();

        final List<FavoriteEntity> favorites = currencies.stream()
                .map(currencyIHM ->  new FavoriteEntity(source.getEntity(),
                        currencyIHM.getEntity(), currencyIHM.getComputeRate(), currencyIHM.getAmount()))
                .collect(Collectors.toList());

        /*final SaveFavorites.QueryVal values = new SaveFavorites.QueryVal(favorites);
        mUseCaseHandler.setUseCase(values, saveFavoritesUseCase);
        mUseCaseHandler.execute(this::doNothing);*/
        repository.addAllFavorites(favorites, this::doNothing);
    }

    @Override
    public void saveFavorite(CurrencyIHM currencyIHM) {
        final CurrencyIHM source = getMvpView().getCurrencySource();

        final FavoriteEntity favorite = new FavoriteEntity(source.getEntity(),
                currencyIHM.getEntity(), currencyIHM.getComputeRate(), currencyIHM.getAmount());

        /*final SaveFavorite.QueryVal value = new SaveFavorite.QueryVal(favorite);
        mUseCaseHandler.setUseCase(value, saveFavoriteUseCase);
        mUseCaseHandler.execute(this::doNothing);*/
        repository.addFavorite(favorite, this::doNothing);
    }

    private void doNothing(Result result) {
        /* do nothing */
    }

    private void doNothing(Response response) {
        /* do nothing */
    }
}
