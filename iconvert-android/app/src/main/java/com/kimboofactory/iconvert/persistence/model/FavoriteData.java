package com.kimboofactory.iconvert.persistence.model;

import com.kimboofactory.iconvert.domain.model.CurrencyEntity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by CK_ALEENGO on 14/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Entity(
        tableName = "favorite"
)
public class FavoriteData {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="_id")
    private long favoriteId;
    @Embedded(prefix = "source_")
    private CurrencyEntity source;
    @Embedded(prefix = "dest_")
    private CurrencyEntity dest;
    @ColumnInfo(name = "computed_rate")
    private String computedRate;
    @ColumnInfo(name = "computed_amount")
    private String amount;

    public FavoriteData( CurrencyEntity source, CurrencyEntity dest, String computedRate, String amount) {
        this.source = source;
        this.dest = dest;
        this.computedRate = computedRate;
        this.amount = amount;
    }

    public long getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(long favoriteId) {
        this.favoriteId = favoriteId;
    }

    public CurrencyEntity getSource() {
        return source;
    }

    public void setSource(CurrencyEntity source) {
        this.source = source;
    }

    public CurrencyEntity getDest() {
        return dest;
    }

    public void setDest(CurrencyEntity dest) {
        this.dest = dest;
    }

    public String getComputedRate() {
        return computedRate;
    }

    public void setComputedRate(String computedRate) {
        this.computedRate = computedRate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
