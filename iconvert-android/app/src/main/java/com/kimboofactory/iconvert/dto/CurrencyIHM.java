package com.kimboofactory.iconvert.dto;

import lombok.AllArgsConstructor;
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
public class CurrencyIHM {
    @NonNull
    private String code;
    @NonNull
    private String libelle;
    @NonNull
    private Boolean checked;
    private String rate;
    private String result;
}
