package com.aleengo.iconvert.persistence.repository;

import com.aleengo.iconvert.domain.Repository;
import com.aleengo.iconvert.domain.model.FavoriteEntity;
import com.aleengo.iconvert.persistence.api.OpenXchangeRateAPI;
import com.aleengo.iconvert.persistence.local.LocalCurrencyDataSource;
import com.aleengo.iconvert.persistence.model.db.CurrencyData;
import com.aleengo.iconvert.persistence.remote.RemoteDataSource;
import com.aleengo.iconvert.util.Mapper;
import com.aleengo.peach.toolbox.commons.model.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class CurrencyRepository implements Repository {

    public static final String TAG = "CurrencyRepository";

    private LocalCurrencyDataSource localDataSource;
    private RemoteDataSource remoteDataSource;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public CurrencyRepository(LocalCurrencyDataSource localDataSource, RemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    // favorite
    @Override
    public void getFavorites(GetCallback callback) {

        if (localDataSource.isEmpty()) {
            final Mapper mapper = new Mapper();
            remoteDataSource.getRatesAndCurrencies(response -> {
                if (response.getError() != null) {
                    throw new RuntimeException(response.getError());
                }

                final Map<String, String> map = (Map<String, String>) response.getValue();
                map.keySet().forEach(key -> {
                    if (OpenXchangeRateAPI.REQUEST_CURRENCY.equals(key)) {
                        localDataSource.saveAllCurrencies(mapper.map2Currencies(map.get(key)));
                    } else if (OpenXchangeRateAPI.REQUEST_RATE.equals(key)) {
                        localDataSource.saveAllRates(mapper.map2Rates(map.get(key)));
                    }
                });
            });
        }
        final List<FavoriteEntity> favoriteEntities = localDataSource.getFavorites();
        callback.onReceived(new Response(favoriteEntities, null));
    }

    @Override
    public void addFavorite(FavoriteEntity favorite, AddCallback callback) {
        localDataSource.saveFavorite(favorite);
        callback.onAdded(new Response(favorite, null));
    }

    @Override
    public void addAllFavorites(List<FavoriteEntity> favorites, AddCallback callback) {
        localDataSource.saveFavorites(favorites);
        callback.onAdded(new Response(favorites, null));
    }

    @Override
    public void removeFavorites() {
        localDataSource.deleteAllFavorites();
    }

    @Override
    public void removeFavorite(FavoriteEntity favorite) {
        localDataSource.deleteFavorite(favorite.getDest().getCode());
    }

    @Override
    public void search(final String query, SearchCallback callback) {

    }

    @Override
    public void getCurrency(String query, GetCallback callback) {
        localDataSource.getCurrency(query, callback::onReceived);
    }

    @Override
    public void addAll(List<CurrencyData> currencies) {
        localDataSource.saveAllCurrencies(currencies);
    }

    @Override
    public void getCurrencies(GetCallback callback) {
        if (localDataSource.isEmpty()) {
            remoteDataSource.getCurrencies(response -> {
                callback.onReceived(response);
                if (response.getError() == null) {
                    localDataSource.saveAllCurrencies(new ArrayList<>(0));
                }
            });
        } else {
            localDataSource.getCurrencies(callback::onReceived);
        }
    }

    @Override
    public void getRates(GetCallback callback) {

    }


    @Override
    public void loadRatesAndCurrencies() {

        disposables.add(remoteDataSource.getCurrencies()
                .doOnSuccess(rateData -> System.out.println("success"))
                .subscribeOn(Schedulers.io())
                .subscribe(localDataSource::saveAllCurrencies));

       disposables.add(remoteDataSource.getRates()
               .doOnSuccess(rateData -> System.out.println("success"))
               .subscribeOn(Schedulers.io())
               .subscribe(localDataSource::saveAllRates));
    }
}
