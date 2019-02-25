package com.kimboofactory.iconvert.util;

import com.aleengo.peach.toolbox.commons.model.RawJSON;
import com.aleengo.peach.toolbox.commons.model.Response;
import com.aleengo.peach.toolbox.commons.strategy.RawJSONDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.kimboofactory.iconvert.persistence.model.CurrencyData;
import com.kimboofactory.iconvert.persistence.model.RateData;

import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by CK_ALEENGO on 25/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class Mapper {

    private final Gson gson;

    public Mapper() {
        gson = new GsonBuilder()
                .registerTypeAdapter(RawJSON.class, new RawJSONDeserializer())
                .create();
    }

    public List<CurrencyData> map2Currencies(Response response) {
        final String data = (String) response.getValue();
        final RawJSON raw = gson.fromJson(new JsonReader(new StringReader(data)), RawJSON.class);

        return raw.getItems().entrySet()
                .stream()
                .map(entrySet -> new CurrencyData(entrySet.getKey(), entrySet.getValue()))
                .collect(Collectors.toList());
    }

    public List<RateData> map2Rates(Response response) {
        final String data = (String) response.getValue();
        final JsonObject jo = new JsonParser().parse(data).getAsJsonObject();
        String rates = jo.get("rates").getAsString();

        final RawJSON raw = gson.fromJson(new JsonReader(new StringReader(rates)), RawJSON.class);

        return raw.getItems().entrySet()
                .stream()
                .map(entrySet -> new RateData(entrySet.getKey(), entrySet.getValue()))
                .collect(Collectors.toList());
    }
}
