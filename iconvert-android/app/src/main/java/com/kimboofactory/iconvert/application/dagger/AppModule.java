package com.kimboofactory.iconvert.application.dagger;

import android.content.Context;

import com.aleengo.peach.toolbox.commons.util.AppExecutors;
import com.kimboofactory.iconvert.persistence.local.AppDatabase;
import com.kimboofactory.iconvert.persistence.local.IConvertDAO;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

/**
 * Created by CK_ALEENGO on 04/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Module
public class AppModule {

    private Context context;
    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @ApplicationScope
    // application's context
    public Context context() {
        return context;
    }

    @Provides
    @ApplicationScope
    public static AppExecutors appExecutors() {
        return new AppExecutors();
    }

    @Provides
    @ApplicationScope
    public static AppDatabase db(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @ApplicationScope
    public static IConvertDAO dao(AppDatabase db) {
        return db.convertDAO();
    }
}
