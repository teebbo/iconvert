package com.aleengo.iconvert.domain;

import com.aleengo.iconvert.domain.model.CurrencyEntity;
import com.aleengo.iconvert.domain.model.FavoriteEntity;
import com.aleengo.iconvert.persistence.model.db.CurrencyData;
import com.aleengo.peach.toolbox.commons.model.Response;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public interface Repository {

    interface GetCallback {
        void onReceived(Response response);
    }

    interface SearchCallback {
        void onDataLoaded(Response response);
    }

    interface AddCallback {
        void onAdded(Response response);
    }

    void search(String query, SearchCallback callback);

    void getCurrency(String query, GetCallback callback);

    void addAll(List<CurrencyData> currencies);

    void getCurrencies(GetCallback callback);

    Observable<List<CurrencyEntity>> getCurrencies();

    void getRates(GetCallback callback);

    void loadRatesAndCurrencies();

    // favorite
    void getFavorites(GetCallback callback);

    void addFavorite(FavoriteEntity favorite, AddCallback callback);

    void addAllFavorites(List<FavoriteEntity> favorites, AddCallback callback);

    void removeFavorites();

    void removeFavorite(FavoriteEntity favorite);

}
