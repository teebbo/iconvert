package com.aleengo.iconvert.domain;

import com.aleengo.peach.toolbox.commons.common.NamedRunnable;
import com.aleengo.iconvert.domain.common.QueryValue;

/**
 * Created by CK_ALEENGO on 14/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class UseCaseHandler {

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
        return LazyHolder.INSTANCE;
    }

    public void execute(final UseCase.Callback callback) {
        useCase.setUseCaseCallback(callback);

        final NamedRunnable task = new NamedRunnable("%s", "") {
            @Override
            protected void execute() {
                useCase.run();
            }
        };
        useCaseScheduler.execute(task);
    }

    private static class LazyHolder {
        private static final UseCaseHandler INSTANCE = new UseCaseHandler(new UseCaseScheduler());
    }
}
