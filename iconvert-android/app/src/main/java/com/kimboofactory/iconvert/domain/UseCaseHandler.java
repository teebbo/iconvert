package com.kimboofactory.iconvert.domain;

import com.kimboofactory.iconvert.domain.common.QueryValue;
import com.kimboofactory.iconvert.dto.Result;
import com.kimboofactory.iconvert.util.SingletonUtil;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by CK_ALEENGO on 14/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class UseCaseHandler {

    private static UseCaseHandler instance;
    @Getter
    private UseCase useCase;

    private UseCaseHandler() {
    }

    public static UseCaseHandler getInstance() {
        instance = SingletonUtil.getInstance(UseCaseHandler.class, instance);
        return instance;
    }

    public void setUseCase(final QueryValue queryValue, UseCase useCase) {
        useCase.setQueryValue(queryValue);
        this.useCase = useCase;
    }

    public void execute(final UseCase.Callback<Result> callback) {
        useCase.setUsecaseCallback(callback);
        useCase.run();
    }
}
