package com.kimboofactory.iconvert.ui.main;

import com.kimboofactory.iconvert.common.BasePresenter;
import com.kimboofactory.iconvert.common.BaseView;
import com.kimboofactory.iconvert.domain.model.FavoriteEntity;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.persistence.model.FavoriteData;

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
    }

    interface View extends BaseView {
        void showSearchActivity(int requestCode);
        //void chooseSourceCurrency();

        void updateFavoritesList(List<CurrencyIHM> currencies);
        void updateSourceCurrency(CurrencyIHM item);
    }
}
