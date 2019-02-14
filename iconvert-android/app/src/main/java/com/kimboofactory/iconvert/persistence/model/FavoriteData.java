package com.kimboofactory.iconvert.persistence.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Created by CK_ALEENGO on 14/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Entity(
        tableName = "favorite",
        foreignKeys = {@ForeignKey(
                        entity = CurrencyData.class,
                        parentColumns = "code",
                        childColumns = {"src_code", "dest_code"}) }
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
}
