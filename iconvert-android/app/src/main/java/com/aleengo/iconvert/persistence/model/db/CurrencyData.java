package com.aleengo.iconvert.persistence.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.aleengo.iconvert.model.Model;

/**
 * Created by CK_ALEENGO on 31/03/2017.
 * Copyright (c) 2017. All rights reserved.
 */

@Entity(tableName = "currency", indices = {@Index("code")})
public class CurrencyData implements Model {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long currencyId;
    @ColumnInfo(name = "code")
    private String code;
    @ColumnInfo(name = "libelle")
    private String libelle;

    public CurrencyData(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
