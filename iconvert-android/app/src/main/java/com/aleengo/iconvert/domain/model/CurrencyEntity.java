package com.aleengo.iconvert.domain.model;

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
    public String code;
    public String libelle;
    public String value;

    public CurrencyEntity(String code, String libelle, String value) {
        this.code = code;
        this.libelle = libelle;
        this.value = value;
    }



}
