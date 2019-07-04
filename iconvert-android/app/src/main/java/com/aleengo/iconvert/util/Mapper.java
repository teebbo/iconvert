package com.aleengo.iconvert.util;

import com.aleengo.peach.toolbox.commons.model.RawJSON;
import com.aleengo.peach.toolbox.commons.strategy.RawJSONDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.aleengo.iconvert.persistence.model.CurrencyData;
import com.aleengo.iconvert.persistence.model.RateData;

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

    public List<CurrencyData> map2Currencies(String json) {
        final JsonObject jo = com.aleengo.peach.toolbox.commons.factory.Mapper.map(json);
        return jo.entrySet()
                .stream()
                .map(entrySet -> new CurrencyData(entrySet.getKey(), entrySet.getValue().getAsString()))
                .collect(Collectors.toList());
    }

    public List<RateData> map2Rates(String json) {
        final JsonObject jo = com.aleengo.peach.toolbox.commons.factory.Mapper.map(json);
        return jo.get("rates").getAsJsonObject().entrySet()
                .stream()
                .map(entrySet -> new RateData(entrySet.getKey(), entrySet.getValue().getAsString()))
                .collect(Collectors.toList());
    }
}
