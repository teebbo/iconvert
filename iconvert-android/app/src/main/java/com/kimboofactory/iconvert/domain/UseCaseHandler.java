package com.kimboofactory.iconvert.domain;

import com.kimboofactory.iconvert.domain.common.QueryValue;
import com.kimboofactory.iconvert.util.NamedRunnable;

/**
 * Created by CK_ALEENGO on 14/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class UseCaseHandler {

    private static UseCaseHandler instance;
    private UseCase useCase;
    private UseCaseScheduler useCaseScheduler;

    private UseCaseHandler(UseCaseScheduler useCaseScheduler) {
        this.useCaseScheduler = useCaseScheduler;
    }

    public void setUseCase(final QueryValue queryValue, UseCase useCase) {
        useCase.setQueryValue(queryValue);
        this.useCase = useCase;
    }

    public static UseCaseHandler getInstance() {
        //instance = Singleton.of(UseCaseHandler.class, instance);
        return LazyHolder.INSTANCE;
    }

    public void execute(final UseCase.Callback callback) {
        useCase.setUseCaseCallback(callback);

        final NamedRunnable task = new NamedRunnable("%s", "UseCaseHandler") {
            @Override
            protected void execute() {
                useCase.run();
            }
        };
        /*Runnable task = () -> {
            System.out.println("UseCaseScheduler.execute: " + Thread.currentThread().getName());
            useCase.run();
        };*/
        useCaseScheduler.execute(task);
    }

    private static class LazyHolder {
        private static final UseCaseHandler INSTANCE = new UseCaseHandler(new UseCaseScheduler());
    }
}
