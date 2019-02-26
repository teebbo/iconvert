package com.kimboofactory.iconvert.domain;

import com.kimboofactory.iconvert.util.Utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by CK_ALEENGO on 26/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public final class UseCaseScheduler {

    private static final int MAX_THREADS = 3;
    private ThreadPoolExecutor executor;

    public UseCaseScheduler() {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(MAX_THREADS, Utils.threadFactory("UseCaseHandler", false));
    }

    public void execute(Runnable command) {
        executor.execute(command);
    }
}
