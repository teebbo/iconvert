package com.aleengo.iconvert.ui.favorite;

import com.aleengo.iconvert.ui.base.BasePresenter;
import com.aleengo.iconvert.ui.base.BaseTemplate;
import com.aleengo.iconvert.dto.CurrencyIHM;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public interface FavoriteContract {

    interface Presenter extends BasePresenter {
        void loadCurrency(CurrencyIHM currencyIHM);
        void loadCurrencies();

        void updateFavorites(int resultCode, Serializable data);
        void updateSourceCurrency(int resultCode, Serializable data);
        void addFavorite(int requestCode);

        void loadRatesAndCurrencies(boolean forceUpdate);
        void loadDefaultCurrency();

        void loadFavorites();
        void saveFavorites(List<CurrencyIHM> favorites);
        void saveFavorite(CurrencyIHM favorite);

        void removeFavorites();
        void removeFavorite(int position);
    }

    interface Template extends BaseTemplate {
        void showSearchActivity(int requestCode);
        void updateFavoritesList(List<CurrencyIHM> currencies);
        void updateSourceCurrency(CurrencyIHM item);
        CurrencyIHM getDefaultCurrency();
        CurrencyIHM removeItem(int position);
    }
}
