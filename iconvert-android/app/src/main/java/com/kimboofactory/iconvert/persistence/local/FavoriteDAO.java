package com.kimboofactory.iconvert.persistence.local;

import com.kimboofactory.iconvert.persistence.model.FavoriteData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * Created by CK_ALEENGO on 14/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Dao
public interface FavoriteDAO {

    @Query("SELECT * FROM favorite")
    List<FavoriteData> getFavorites();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(FavoriteData currency);
    @Insert
    void insertFavorites(List<FavoriteData> currencies);
    @Delete
    void deleteFavorite(FavoriteData currency);
}
