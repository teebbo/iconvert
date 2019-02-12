package com.kimboofactory.iconvert.domain.usecases;

import com.kimboofactory.iconvert.domain.UseCase;
import com.kimboofactory.iconvert.domain.common.QueryValue;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class GetRate extends UseCase<QueryValue> {

    public GetRate() {
    }

    @Override
    protected void execute() {
        final String query = getQueryValue().getValue();
    }
}
