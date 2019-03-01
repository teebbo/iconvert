package com.kimboofactory.iconvert.di;

import android.content.Context;

import com.aleengo.peach.toolbox.commons.util.AppExecutors;
import com.kimboofactory.iconvert.domain.UseCaseHandler;
import com.kimboofactory.iconvert.domain.usecases.DeleteFavorite;
import com.kimboofactory.iconvert.domain.usecases.DeleteFavorites;
import com.kimboofactory.iconvert.domain.usecases.GetCurrencies;
import com.kimboofactory.iconvert.domain.usecases.GetCurrency;
import com.kimboofactory.iconvert.domain.usecases.GetFavorites;
import com.kimboofactory.iconvert.domain.usecases.GetRate;
import com.kimboofactory.iconvert.domain.usecases.GetRatesAndCurrencies;
import com.kimboofactory.iconvert.domain.usecases.SaveFavorite;
import com.kimboofactory.iconvert.domain.usecases.SaveFavorites;
import com.kimboofactory.iconvert.persistence.api.OpenXchangeRateAPI;
import com.kimboofactory.iconvert.persistence.local.AppDatabase;
import com.kimboofactory.iconvert.persistence.local.LocalCurrencyDataSource;
import com.kimboofactory.iconvert.persistence.remote.RemoteDataSource;
import com.kimboofactory.iconvert.persistence.repository.CurrencyRepository;

import androidx.annotation.NonNull;

/**
 * Created by CK_ALEENGO on 25/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class Injection {

    private Injection() {}

    public static CurrencyRepository provideCurrencyRepository(@NonNull Context context) {
        final AppDatabase db = AppDatabase.getInstance(context);

        final AppExecutors appExecutors = new AppExecutors();
        final LocalCurrencyDataSource localDataSource =
                LocalCurrencyDataSource.getInstance(appExecutors, db.convertDAO());
        final RemoteDataSource remoteDataSource =
                RemoteDataSource.getInstance(appExecutors, OpenXchangeRateAPI.getInstance());

        return new CurrencyRepository(localDataSource, remoteDataSource);
    }

    public static UseCaseHandler provideUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }

    public static GetCurrencies provideGetCurrencies(@NonNull Context context) {
        return new GetCurrencies(provideCurrencyRepository(context));
    }

    public static GetCurrency provideGetCurrency(@NonNull Context context) {
        return new GetCurrency(provideCurrencyRepository(context));
    }

    public static GetFavorites provideGetFavorites(@NonNull Context context) {
        return new GetFavorites(provideCurrencyRepository(context));
    }

    public static SaveFavorite provideSaveFavorite(@NonNull Context context) {
        return new SaveFavorite(provideCurrencyRepository(context));
    }

    public static SaveFavorites provideSaveFavorites(@NonNull Context context) {
        return new SaveFavorites(provideCurrencyRepository(context));
    }

    public static DeleteFavorites provideDeleteFavorites(@NonNull Context context) {
        return new DeleteFavorites(provideCurrencyRepository(context));
    }

    public static DeleteFavorite provideDeleteFavorite(@NonNull Context context) {
        return new DeleteFavorite(provideCurrencyRepository(context));
    }

    public static GetRate provideGetRates(@NonNull Context context) {
        return new GetRate(provideCurrencyRepository(context));
    }

    public static GetRatesAndCurrencies provideGetRatesAndCurrencies(@NonNull Context context) {
        return new GetRatesAndCurrencies(provideCurrencyRepository(context));
    }
}
