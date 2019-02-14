package com.kimboofactory.iconvert.domain;

import com.kimboofactory.iconvert.dto.Result;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public abstract class UseCase<Q, RESULT extends Result> {

    @Getter @Setter
    private Callback<RESULT> usecaseCallback;
    @Setter @Getter
    private Repository repository;
    @Setter @Getter
    private Q queryValue;

    public UseCase() {
    }

    protected abstract void executeUseCase();

    public void run() {
        executeUseCase();
    }

    public interface Callback<RESULT> {
        void onResult(RESULT result);
    }
}
