package com.aleengo.iconvert.persistence.repository;

import com.aleengo.iconvert.domain.Repository;
import com.aleengo.iconvert.domain.model.FavoriteEntity;
import com.aleengo.iconvert.persistence.model.CurrencyData;

import java.util.List;

/**
 * Copyright (c) Aleengo, 2019. All rights reserved.
 * Created by bau.cj on 04/07/2019.
 */
public class FavoriteRepository implements Repository {

    @Override
    public void search(String query, SearchCallback callback) {

    }

    @Override
    public void getCurrency(String query, GetCallback callback) {

    }

    @Override
    public void addAll(List<CurrencyData> currencies) {

    }

    @Override
    public void getCurrencies(GetCallback callback) {

    }

    @Override
    public void getRates(GetCallback callback) {

    }

    @Override
    public void addRatesAndCurrencies() {

    }

    @Override
    public void getFavorites(GetCallback callback) {

    }

    @Override
    public void addFavorite(FavoriteEntity favorite, AddCallback callback) {

    }

    @Override
    public void addAllFavorites(List<FavoriteEntity> favorites, AddCallback callback) {

    }

    @Override
    public void removeFavorites() {

    }

    @Override
    public void removeFavorite(FavoriteEntity favorite) {

    }
}
