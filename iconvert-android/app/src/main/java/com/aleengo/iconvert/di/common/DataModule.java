package com.aleengo.iconvert.di.common;

import android.content.Context;

import androidx.room.Room;

import com.aleengo.iconvert.persistence.local.AppDatabase;
import com.aleengo.iconvert.persistence.local.ConvertDAO;
import com.aleengo.peach.toolbox.commons.util.AppExecutors;

import dagger.Provides;

/**
 * Copyright (c) Aleengo, 2019. All rights reserved.
 * Created by bau.cj on 04/07/2019.
 */
public class DataModule {

    @Provides
    @ApplicationScope
    public static AppExecutors appExecutors() {
        return new AppExecutors();
    }

    @Provides
    @ApplicationScope
    public static AppDatabase database(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @ApplicationScope
    public static ConvertDAO dao(AppDatabase db) {
        return db.convertDAO();
    }
}
