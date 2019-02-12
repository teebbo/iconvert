package com.kimboofactory.iconvert.domain;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public interface Repository {

    interface GetCallback {
    }

    void get(long id);
    void getAll();
}
