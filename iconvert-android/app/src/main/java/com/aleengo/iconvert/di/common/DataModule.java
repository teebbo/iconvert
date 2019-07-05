package com.aleengo.iconvert.di.common;

import android.content.Context;

import androidx.room.Room;

import com.aleengo.iconvert.persistence.api.XchangeRateAPI;
import com.aleengo.iconvert.persistence.local.AppDatabase;
import com.aleengo.iconvert.persistence.local.ConvertDAO;
import com.aleengo.iconvert.persistence.local.LocalCurrencyDataSource;
import com.aleengo.iconvert.persistence.remote.RemoteDataSource;
import com.aleengo.iconvert.persistence.repository.CurrencyRepository;
import com.aleengo.peach.toolbox.commons.util.AppExecutors;
import com.aleengo.peank.core.annotations.dagger.Application;

import dagger.Provides;

/**
 * Copyright (c) Aleengo, 2019. All rights reserved.
 * Created by bau.cj on 04/07/2019.
 */
public class DataModule {

    @Provides
    @Application
    public static AppExecutors appExecutors() {
        return new AppExecutors();
    }

    @Provides
    @Application
    public static AppDatabase database(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Application
    public static ConvertDAO dao(AppDatabase db) {
        return db.convertDAO();
    }


    @Application
    @Provides
    public static CurrencyRepository repository(AppExecutors executors, ConvertDAO dao, XchangeRateAPI api) {
        final LocalCurrencyDataSource local = new LocalCurrencyDataSource(executors, dao);
        final RemoteDataSource remote = new RemoteDataSource(executors, api);
        return new CurrencyRepository(local, remote);
    }
}
