package com.aleengo.iconvert.domain;

import com.aleengo.peach.toolbox.commons.concurrent.PeachFixedThreadExecutor;

/**
 * Created by CK_ALEENGO on 26/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public final class UseCaseScheduler {

    private static final int MAX_THREADS = 3;
    private static final String POOL_NAME = "UseCaseSchedulerPool";

    private PeachFixedThreadExecutor executor;

    public UseCaseScheduler() {
        executor = new PeachFixedThreadExecutor(POOL_NAME);
    }

    public void execute(Runnable command) {
        executor.execute(command);
    }
}
