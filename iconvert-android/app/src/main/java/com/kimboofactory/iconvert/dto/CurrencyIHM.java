package com.kimboofactory.iconvert.dto;

import com.kimboofactory.iconvert.domain.model.CurrencyEntity;
import com.kimboofactory.iconvert.model.Model;
import com.kimboofactory.iconvert.util.Helper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by CK_ALEENGO on 12/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class CurrencyIHM implements Model, Cloneable {
    @NonNull
    private CurrencyEntity entity;
    private Boolean checked = false;
    private String computeRate;
    private String amount = Helper.DEFAULT_AMOUNT;

    @Override
    public CurrencyIHM clone() throws CloneNotSupportedException {
        return (CurrencyIHM) super.clone();
    }
}
