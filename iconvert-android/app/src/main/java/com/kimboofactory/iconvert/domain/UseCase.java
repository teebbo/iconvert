package com.kimboofactory.iconvert.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public abstract class UseCase<Q> {

    @Setter @Getter
    private UseCase.Callback callback;
    @Setter @Getter
    private Repository repository;
    @Setter @Getter
    private Q queryValue;

    public UseCase() {
    }

    protected abstract void execute();

    public void run() {
        execute();
    }

    interface Callback {
        void onSuccess();
        void onFailure();
    }
}
