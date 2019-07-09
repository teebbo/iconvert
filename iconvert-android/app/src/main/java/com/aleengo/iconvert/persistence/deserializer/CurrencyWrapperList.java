package com.aleengo.iconvert.persistence.deserializer;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Copyright (c) Aleengo, 2019. All rights reserved.
 * Created by bau.cj on 05/07/2019.
 */
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyWrapperList {

    public List<CurrencyWrapper> data;

    public List<CurrencyWrapper> get() {
        return data;
    }
}
