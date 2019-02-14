package com.kimboofactory.iconvert.persistence.local;

import com.kimboofactory.iconvert.persistence.model.CurrencyData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * Created by CK_ALEENGO on 14/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Dao
public interface CurrencyDAO {

    @Query("SELECT * from currency WHERE code = :currencyCode")
    CurrencyData get(String currencyCode);
    @Query("SELECT * from currency")
    List<CurrencyData> getAll();
    @Insert
    void saveAll(List<CurrencyData> currencies);
    @Delete
    void deleteAll();
}
