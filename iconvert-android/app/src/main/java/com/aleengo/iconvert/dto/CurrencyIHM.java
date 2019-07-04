package com.aleengo.iconvert.dto;

import com.aleengo.iconvert.application.Constant;
import com.aleengo.iconvert.domain.model.CurrencyEntity;
import com.aleengo.iconvert.model.Model;

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
    private Integer code;
    private Boolean checkboxChecked = false;
    private Boolean radioButtonChecked = false;
    private String computeRate;
    private String amount = Constant.DEFAULT_AMOUNT;

    @Override
    public CurrencyIHM clone() throws CloneNotSupportedException {
        return (CurrencyIHM) super.clone();
    }
}
