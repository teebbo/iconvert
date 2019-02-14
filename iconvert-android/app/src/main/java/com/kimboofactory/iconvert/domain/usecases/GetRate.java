package com.kimboofactory.iconvert.domain.usecases;

import com.kimboofactory.iconvert.domain.UseCase;
import com.kimboofactory.iconvert.domain.common.QueryValue;
import com.kimboofactory.iconvert.dto.Result;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class GetRate extends UseCase<QueryValue, Result> {

    public GetRate() {
    }

    @Override
    protected void executeUseCase() {
        final String query = getQueryValue().getValue();
    }
}
