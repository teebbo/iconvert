package com.kimboofactory.iconvert.persistence.local;

import com.kimboofactory.iconvert.persistence.model.CurrencyData;
import com.kimboofactory.iconvert.persistence.model.FavoriteData;
import com.kimboofactory.iconvert.util.SingletonUtil;

import androidx.room.Database;
import androidx.room.RoomDatabase;


/**
 * Created by CK_ALEENGO on 14/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Database(entities = {FavoriteData.class, CurrencyData.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public AppDatabase() {
        super();
    }

    public static AppDatabase getInstance() {
        instance = SingletonUtil.getInstance(AppDatabase.class, instance);
        return instance;
    }

    public abstract FavoriteDAO favoriteDAO();
    public abstract CurrencyDAO currencyDAO();
}
