package com.kimboofactory.iconvert.domain.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by CK_ALEENGO on 23/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */

@Setter
@Getter
public class CurrencyEntity implements Serializable {
    private String code;
    private String libelle;
    private String value;

    public CurrencyEntity(String code, String libelle, String value) {
        this.code = code;
        this.libelle = libelle;
        this.value = value;
    }



}
