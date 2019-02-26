package com.kimboofactory.iconvert.persistence.model;

import com.kimboofactory.iconvert.model.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by CK_ALEENGO on 29/10/2015..
 * Copyright (c) 2015. All rights reserved.
 */
@Entity(tableName = "rate", indices = {@Index("code")})
public class RateData implements Model {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long rateId;
    @ColumnInfo(name = "code")
    private String code;
    @ColumnInfo(name = "rate")
    private String value;

    public RateData(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public Long getRateId() {
        return rateId;
    }

    public void setRateId(Long rateId) {
        this.rateId = rateId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
