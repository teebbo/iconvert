package com.kimboofactory.iconvert.persistence.local;

import com.kimboofactory.iconvert.persistence.model.CurrencyData;
import com.kimboofactory.iconvert.persistence.model.FavoriteData;
import com.kimboofactory.iconvert.persistence.model.RateData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * Created by CK_ALEENGO on 25/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Dao
public interface IConvertDAO {

    // rates
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAllRates(List<RateData> rates);

    @Query("DELETE FROM rate")
    void deleteAllRates();

    // currencies
    @Query("SELECT * FROM currency")
    List<CurrencyData> getAllCurrencies();

    @Query("SELECT * FROM currency WHERE code = :code")
    CurrencyData getCurrencyByCode(String code);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAllCurrencies(List<CurrencyData> currencies);

    @Query("DELETE FROM currency")
    void deleteAllCurrencies();

    // Favorites
    @Query("SELECT * FROM favorite")
    List<FavoriteData> getFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveFavorite(FavoriteData currency);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveFavorites(List<FavoriteData> favorites);

    @Query("DELETE FROM favorite WHERE _id = :favorite")
    void deleteFavorite(long favorite);
}
