package com.kimboofactory.iconvert.domain.usecases;

import android.util.Log;

import com.aleengo.peach.toolbox.commons.model.RawJSON;
import com.aleengo.peach.toolbox.commons.model.Response;
import com.aleengo.peach.toolbox.commons.model.Result;
import com.aleengo.peach.toolbox.commons.strategy.RawJSONDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.kimboofactory.iconvert.domain.UseCase;
import com.kimboofactory.iconvert.domain.common.QueryValue;
import com.kimboofactory.iconvert.domain.model.CurrencyEntity;
import com.kimboofactory.iconvert.domain.model.RateEntity;
import com.kimboofactory.iconvert.persistence.model.CurrencyData;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class GetCurrencies extends UseCase<QueryValue> {

    public static final String TAG = "GetCurrenciesUseCase";

    public GetCurrencies() {
    }

    @Override
    protected void executeUseCase() {
        final String query = getQueryValue() == null ?
                null : getQueryValue().getValue();
        getRepository().find(query, this::onDataLoaded);
    }

    private void onDataLoaded(Response response) {
        if (response.getError() != null) {
            getUseCaseCallback().onResult(new Result<String>(null, response.getError()));
            return;
        }

        final Future<List<CurrencyData>> promise = processLoadedData(response);
        try {
            getRepository().addAll(promise.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private Future<List<CurrencyData>> processLoadedData(Response response) {

        final Callable<List<CurrencyData>> task = () -> {
            Log.d(TAG, "processLoadedData: " + Thread.currentThread().getName());

            final Gson gson = new GsonBuilder()
                    .registerTypeAdapter(RawJSON.class, new RawJSONDeserializer())
                    .create();

            final List<String> data = (List<String>) response.getValue();

            final String currenciesString = data.get(0);
            final String ratesString = data.get(1);

            final RawJSON currenciesRaw = gson.fromJson(new JsonReader(new StringReader(currenciesString)),
                    RawJSON.class);

            final List<CurrencyEntity> currencyEntities = currenciesRaw.getItems().entrySet()
                    .stream()
                    .map(entrySet -> new CurrencyEntity(entrySet.getKey(), entrySet.getValue()))
                    .collect(Collectors.toList());

            final JsonObject jo = new JsonParser().parse(ratesString).getAsJsonObject();
            String rates = jo.get("rates").getAsString();

            final RawJSON ratesRaw = gson.fromJson(new JsonReader(new StringReader(rates)),
                    RawJSON.class);

            final List<RateEntity> rateEntities = ratesRaw.getItems().entrySet()
                    .stream()
                    .map(entrySet -> new RateEntity(entrySet.getKey(), entrySet.getValue()))
                    .collect(Collectors.toList());

            final List<CurrencyData> items = new LinkedList<>();
            rateEntities.forEach(rateEntity -> {
                currencyEntities.forEach(currencyEntity -> {
                    if (currencyEntity.getCode().equals(rateEntity.getCode())) {
                        items.add(new CurrencyData(rateEntity.getCode(), currencyEntity.getLibelle(), rateEntity.getValue()));
                    }
                });
            });
            return items;
        };

        return Executors.newFixedThreadPool(1).submit(task);
    }
}
