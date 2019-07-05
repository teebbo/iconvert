package com.aleengo.iconvert.persistence.local;

import com.aleengo.iconvert.persistence.model.db.RateData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * Created by CK_ALEENGO on 21/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
@Dao
public interface RateDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<RateData> rates);
    @Query("DELETE FROM rate")
    void deleteAll();
}
