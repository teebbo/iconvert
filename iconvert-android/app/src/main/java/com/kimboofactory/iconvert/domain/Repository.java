package com.kimboofactory.iconvert.domain;

import com.kimboofactory.iconvert.dto.Result;

import java.util.List;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public interface Repository {

    interface GetCallback {
        void onLoaded(Result<List<String>> result);
    }

    interface SearchCallback {
        void onDataLoaded(Result<List<String>> result);
    }

    void find(String query, SearchCallback callback);
}
