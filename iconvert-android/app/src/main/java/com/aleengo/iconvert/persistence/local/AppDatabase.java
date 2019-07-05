package com.aleengo.iconvert.persistence.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.aleengo.iconvert.persistence.model.db.CurrencyData;
import com.aleengo.iconvert.persistence.model.db.FavoriteData;
import com.aleengo.iconvert.persistence.model.db.RateData;


/**
 * Created by CK_ALEENGO on 14/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Database(entities = {CurrencyData.class, RateData.class, FavoriteData.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DB_NAME = "iconvert.db";

    public AppDatabase() {
        super();
    }

    public abstract ConvertDAO convertDAO();
}
