package com.kimboofactory.iconvert.persistence.local;

import android.content.Context;

import com.kimboofactory.iconvert.persistence.model.CurrencyData;
import com.kimboofactory.iconvert.persistence.model.FavoriteData;
import com.kimboofactory.iconvert.persistence.model.RateData;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


/**
 * Created by CK_ALEENGO on 14/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Database(entities = {FavoriteData.class, CurrencyData.class, RateData.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "iconvert.db";

    private static Context context;

    public AppDatabase() {
        super();
    }

    public abstract FavoriteDAO favoriteDAO();
    public abstract CurrencyDAO currencyDAO();
    public abstract RateDAO rateDAO();

    private static class LazyHolder {
        private static final AppDatabase INSTANCE = Room
                .databaseBuilder(AppDatabase.context.getApplicationContext(),
                        AppDatabase.class, DB_NAME)
                .build();
    }

    public static AppDatabase getDb(Context context) {
        if (AppDatabase.context == null) {
            AppDatabase.context = context;
        }
        return LazyHolder.INSTANCE;
    }
}
