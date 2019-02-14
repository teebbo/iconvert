package com.kimboofactory.iconvert.domain;

import com.kimboofactory.iconvert.dto.Result;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public interface Repository {

    interface GetCallback {
        void onLoaded(Result<String> result);
    }

    interface SearchCallback {
        void onDataLoaded(Result<String> result);
    }

    void find(String query, SearchCallback callback);
}
