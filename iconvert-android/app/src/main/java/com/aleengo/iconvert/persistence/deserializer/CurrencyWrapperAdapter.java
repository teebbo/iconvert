package com.aleengo.iconvert.persistence.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * Copyright (c) Aleengo, 2019. All rights reserved.
 * Created by bau.cj on 05/07/2019.
 */
public class CurrencyWrapperAdapter implements JsonDeserializer<CurrencyWrapperList> {

    @Override
    public CurrencyWrapperList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        final List<CurrencyWrapper> data = new LinkedList<>();

        JsonObject jo = json.getAsJsonObject();

        if (jo.has("rates")) {
            jo = jo.getAsJsonObject("rates");
        }

        jo.entrySet().forEach(e -> {
            final String k = e.getKey();
            final String v = e.getValue().getAsString();
            data.add(new CurrencyWrapper(k, v));
        });

        return new CurrencyWrapperList(data);
    }
}
