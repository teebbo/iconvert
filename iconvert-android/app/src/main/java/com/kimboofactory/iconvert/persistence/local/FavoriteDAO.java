package com.kimboofactory.iconvert.persistence.local;

import com.kimboofactory.iconvert.persistence.model.CurrencyData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

/**
 * Created by CK_ALEENGO on 14/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Dao
public interface FavoriteDAO {


    List<CurrencyDAO> getFavorites();
    @Insert
    void insertFavorite(CurrencyData currency);
    @Insert
    void insertFavorites(List<CurrencyData> currencies);
    @Delete
    void deleteFavorite(CurrencyData currency);
    @Delete
    void deleteFavorites(List<CurrencyData> currency);
}
