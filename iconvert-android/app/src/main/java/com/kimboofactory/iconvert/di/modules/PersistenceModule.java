package com.kimboofactory.iconvert.di.modules;

import android.content.Context;

import com.aleengo.peach.toolbox.commons.util.AppExecutors;
import com.kimboofactory.iconvert.di.scope.ApplicationScope;
import com.kimboofactory.iconvert.persistence.local.AppDatabase;
import com.kimboofactory.iconvert.persistence.local.IConvertDAO;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CK_ALEENGO on 03/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Module
public class PersistenceModule {

    @Provides
    @ApplicationScope
    public AppExecutors appExecutors() {
        return new AppExecutors();
    }

    @Provides
    @ApplicationScope
    public IConvertDAO dao(Context context) {
        final AppDatabase db = AppDatabase.getInstance(context);
        return db.convertDAO();
    }

   /* @Provides
    @ApplicationScope
    public LocalCurrencyDataSource localCurrencyDataSource(AppExecutors appExecutors, IConvertDAO dao) {
        return new LocalCurrencyDataSource(appExecutors, dao);
    }*/

}
