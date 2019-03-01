package com.kimboofactory.iconvert.domain.model;

import com.kimboofactory.iconvert.dto.CurrencyIHM;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by CK_ALEENGO on 14/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Setter
@Getter
public class FavoriteEntity {
    private CurrencyEntity source;
    private CurrencyEntity dest;
    private String computedRate;
    private String computedAmount;

    public FavoriteEntity(CurrencyEntity source, CurrencyEntity dest, String computedRate, String computedAmount) {
        this.source = source;
        this.dest = dest;
        this.computedRate = computedRate;
        this.computedAmount = computedAmount;
    }
}
