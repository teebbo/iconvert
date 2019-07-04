package com.aleengo.iconvert.domain;


import com.aleengo.peach.toolbox.commons.model.Result;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public abstract class UseCase<Q> {

    @Getter @Setter
    private UseCase.Callback useCaseCallback;
    @Setter @Getter
    private Repository repository;
    @Setter @Getter
    private Q queryValue;

    public UseCase(Repository repository) {
        this.repository = repository;
    }

    protected abstract void executeUseCase();

    public void run() {
        executeUseCase();
    }

    public interface Callback {
        void onResult(Result result);
    }
}
