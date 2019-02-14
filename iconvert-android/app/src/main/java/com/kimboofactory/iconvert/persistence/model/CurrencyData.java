package com.kimboofactory.iconvert.persistence.model;

import com.kimboofactory.iconvert.model.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by CK_ALEENGO on 31/03/2017.
 * Copyright (c) 2017. All rights reserved.
 */

@ToString(of = {"code", "libelle"})
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "currency", indices = {@Index("code")})
public class CurrencyData implements Model {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long currencyId;
    @ColumnInfo(name = "code")
    private String code;
    @ColumnInfo(name = "libelle")
    private String libelle;
    @ColumnInfo(name = "rate")
    private String rate;
}
