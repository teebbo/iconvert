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
 * Created by CK_ALEENGO on 29/10/2015..
 * Copyright (c) 2015. All rights reserved.
 */
@ToString(of = {"code", "value"})
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "rate", indices = {@Index("code")})
public class RateData implements Model {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long rateId;
    @ColumnInfo(name = "code")
    private String code;
    @ColumnInfo(name = "rate")
    private String value;
}
