package com.aleengo.iconvert.persistence.deserializer;

import java.util.List;

import lombok.AllArgsConstructor;

/**
 * Copyright (c) Aleengo, 2019. All rights reserved.
 * Created by bau.cj on 05/07/2019.
 */
@AllArgsConstructor
public class CurrencyWrapperList {

    private List<CurrencyWrapper> data;

    public List<CurrencyWrapper> get() {
        return data;
    }
}
