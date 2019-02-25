package com.kimboofactory.iconvert.persistence.model;

import androidx.room.ColumnInfo;
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
    @ColumnInfo(name = "src_code")
    private String source;
    @ColumnInfo(name="dest_code")
    private String dest;
    @ColumnInfo(name = "computed_rate")
    private String computedRate;
    @ColumnInfo(name = "value")
    private String value;

    public FavoriteData(String source, String dest, String computedRate, String value) {
        this.source = source;
        this.dest = dest;
        this.computedRate = computedRate;
        this.value = value;
    }

    public long getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(long favoriteId) {
        this.favoriteId = favoriteId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getComputedRate() {
        return computedRate;
    }

    public void setComputedRate(String computedRate) {
        this.computedRate = computedRate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
