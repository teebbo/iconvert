package com.kimboofactory.iconvert.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by CK_ALEENGO on 25/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class DiskIOThreadExecutor implements Executor {

    private final ExecutorService mSingleThreadService;

    public DiskIOThreadExecutor() {
        mSingleThreadService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(Runnable command) {
        mSingleThreadService.execute(command);
    }

    public <V> V execute(Callable<V> command) {
        final Future<V> promise = mSingleThreadService.submit(command);
        try {
            return promise.get();
        } catch (InterruptedException | ExecutionException e) {
           throw new RuntimeException(e);
        }
    }
}
