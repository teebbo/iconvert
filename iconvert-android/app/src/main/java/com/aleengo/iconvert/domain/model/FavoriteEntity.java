package com.aleengo.iconvert.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by CK_ALEENGO on 14/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Setter
@Getter
public class FavoriteEntity {
    public CurrencyEntity source;
    public CurrencyEntity dest;
    public String computedRate;
    public String computedAmount;

    public FavoriteEntity(CurrencyEntity source, CurrencyEntity dest, String computedRate, String computedAmount) {
        this.source = source;
        this.dest = dest;
        this.computedRate = computedRate;
        this.computedAmount = computedAmount;
    }
}
