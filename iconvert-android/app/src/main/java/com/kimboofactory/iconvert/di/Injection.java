package com.kimboofactory.iconvert.di;

import android.content.Context;

import com.kimboofactory.iconvert.persistence.local.AppDatabase;
import com.kimboofactory.iconvert.persistence.repository.CurrencyRepository;

/**
 * Created by CK_ALEENGO on 25/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class Injection {

    private Injection() {}

    public static CurrencyRepository provideCurrencyRepository(Context context) {

        final AppDatabase db = AppDatabase.getInstance();


        return null;
    }

}
