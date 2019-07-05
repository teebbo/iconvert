package com.aleengo.iconvert.persistence.remote;

import com.aleengo.iconvert.persistence.CurrencyDataSource;
import com.aleengo.iconvert.persistence.api.OpenXchangeRateAPI;
import com.aleengo.iconvert.persistence.api.XchangeRateAPI;
import com.aleengo.iconvert.persistence.deserializer.CurrencyWrapperList;
import com.aleengo.iconvert.persistence.model.db.CurrencyData;
import com.aleengo.iconvert.persistence.model.db.RateData;
import com.aleengo.peach.toolbox.commons.common.NamedRunnable;
import com.aleengo.peach.toolbox.commons.util.AppExecutors;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class RemoteDataSource implements CurrencyDataSource {

    @Getter @Setter
    private XchangeRateAPI api;
    @Getter @Setter
    private AppExecutors appExecutors;


    //private static RemoteDataSource instance;

    @Inject
    public RemoteDataSource(AppExecutors appExecutors, XchangeRateAPI api) {
        this.api = api;
        this.appExecutors = appExecutors;
    }

   /* public static RemoteDataSource getInstance(AppExecutors appExecutors, API api) {
        if (instance == null) {
            instance = Singleton.of(RemoteDataSource.class, instance);
            instance.setApi(api);
            instance.setAppExecutors(appExecutors);
        }
        return instance;
    }*/

    @Override
    public void getCurrency(String code, GetCurrencyCallback callback) {

    }

    @Override
    public void getCurrencies(GetCurrenciesCallback callback) {
        final NamedRunnable command = new NamedRunnable("%s.%s", "RemoteDataSource", "getCurrencies") {
            @Override
            protected void execute() {
                ((OpenXchangeRateAPI) api).getCurrencies(callback::onCurrenciesReceived);
            }
        };
        appExecutors.networkIO().execute(command);
    }

    public void getRatesAndCurrencies(CurrencyDataSource.GetCallback callback) {
        final NamedRunnable command = new NamedRunnable("%s.%s", "RemoteDataSource", "getRatesAndCurrencies") {
            @Override
            protected void execute() {
                ((OpenXchangeRateAPI) api).getRatesAndCurrencies(callback::onReceived);
            }
        };
        appExecutors.networkIO().execute(command);
    }

    public Single<List<CurrencyData>> getCurrencies() {
        return api.currencies()
                .map(new MapListFunction<CurrencyData>() {
                    @Override
                    protected CurrencyData instantiate(String code, String value) {
                        return new CurrencyData(code, value);
                    }
                });
    }

    public Single<List<RateData>> getRates() {
        return api.latestRates()
                .map(new MapListFunction<RateData>() {
                    @Override
                    protected RateData instantiate(String code, String value) {
                        return new RateData(code, value);
                    }
                });
    }

    public static abstract class MapListFunction<D> implements Function<CurrencyWrapperList, List<D>> {

        private final List<D> data;

        public MapListFunction() {
            this.data = new LinkedList<>();
        }

        @Override
        public List<D> apply(CurrencyWrapperList currencyWrapperList) throws Exception {
            currencyWrapperList.get().forEach(currencyWrapper -> {
                final String code = currencyWrapper.getKey();
                final String value = currencyWrapper.getValue();
                data.add(instantiate(code, value));
            });
            return data;
        }

        protected abstract D instantiate(String code, String value);
    }
}
