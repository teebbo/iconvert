package com.kimboofactory.iconvert.persistence.local;

import com.kimboofactory.iconvert.persistence.model.RateData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;

/**
 * Created by CK_ALEENGO on 21/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Dao
public interface RateDAO {

    @Insert
    void insert(List<RateData> rates);
    @Delete
    void deleteAll();
}
