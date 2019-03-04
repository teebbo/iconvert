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
@Database(entities = {CurrencyData.class, RateData.class, FavoriteData.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DB_NAME = "iconvert.db";

    private static AppDatabase instance;

    public AppDatabase() {
        super();
    }

    public abstract IConvertDAO convertDAO();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
