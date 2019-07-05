package com.aleengo.iconvert.persistence.local;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.aleengo.iconvert.persistence.model.db.CurrencyData;

import java.util.List;

/**
 * Created by CK_ALEENGO on 14/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Dao
public interface CurrencyDAO {

    @Query("SELECT * FROM currency")
    List<CurrencyData> getAll();

    @Query("SELECT * FROM currency WHERE code = :code")
    CurrencyData getCurrencyByCode(String code);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<CurrencyData> currencies);

    @Query("DELETE FROM currency")
    void deleteAll();
}
