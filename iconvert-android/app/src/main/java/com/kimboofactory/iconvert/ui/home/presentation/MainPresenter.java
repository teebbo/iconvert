package com.kimboofactory.iconvert.ui.home.presentation;

import com.aleengo.peach.toolbox.commons.model.Response;
import com.aleengo.peach.toolbox.commons.model.Result;
import com.kimboofactory.iconvert.common.AbstractPresenter;
import com.kimboofactory.iconvert.common.Constant;
import com.kimboofactory.iconvert.domain.Repository;
import com.kimboofactory.iconvert.domain.model.FavoriteEntity;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.events.GetFavoriteEvent;
import com.kimboofactory.iconvert.persistence.repository.CurrencyRepository;
import com.kimboofactory.iconvert.util.Helper;

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
        repository.removeFavorites();
        getMvpView().updateFavoritesList(new ArrayList<>(0));
    }

    @Override
    public void removeFavorite(int position) {
        // update the ui
        final CurrencyIHM itemToDelete = getMvpView().removeItem(position);

        // update the database
        final FavoriteEntity favorite = new FavoriteEntity(null, itemToDelete.getEntity(),
                itemToDelete.getComputeRate(), itemToDelete.getAmount());
       repository.removeFavorite(favorite);
    }

    @Override
    public void updateFavorites(int resultCode, Serializable data) {
        final HashMap<Integer, CurrencyIHM> currencies = (HashMap<Integer, CurrencyIHM>) data;

        final List<CurrencyIHM> list = new ArrayList<>(currencies.values());

        list.addAll(list.size(), getMvpView().getAdapter().getItems());
        getMvpView().updateFavoritesList(list);

        // save the new ones
        saveFavorites(getMvpView().getAdapter().getItems());
    }

    @Override
    public void updateSourceCurrency(int resultCode, Serializable data) {
        final CurrencyIHM item = (CurrencyIHM) data;

        // update the source and the adapter based on the new source
        getMvpView().updateSourceCurrency(item);

        // update the database based on the new source
        saveFavorites(getMvpView().getAdapter().getItems());
    }

    @Override
    public void loadCurrency(CurrencyIHM currencyIHM) {
    }

    @Override
    public void loadDefaultCurrency() {
    }

    @Override
    public void loadCurrencies() {
    }

    @Override
    public void loadRatesAndCurrencies(boolean forceUpdate) {
        if (forceUpdate || mFirstLoad) {
            repository.addRatesAndCurrencies();
            mFirstLoad = false;
        }
    }

    @Override
    public void loadFavorites() {
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
                    final Float cachedAmount = Float.valueOf(Helper.unformatNumber(f.getComputedAmount())) / Float.valueOf(f.getComputedRate());
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
        repository.addAllFavorites(favorites, this::doNothing);
    }

    @Override
    public void saveFavorite(CurrencyIHM currencyIHM) {
        final CurrencyIHM source = getMvpView().getCurrencySource();

        final FavoriteEntity favorite = new FavoriteEntity(source.getEntity(),
                currencyIHM.getEntity(), currencyIHM.getComputeRate(), currencyIHM.getAmount());
        repository.addFavorite(favorite, this::doNothing);
    }

    private void doNothing(Result result) {
        /* do nothing */
    }

    private void doNothing(Response response) {
        /* do nothing */
    }
}
