package com.kimboofactory.iconvert.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by CK_ALEENGO on 25/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class AppExecutors {

    private static final int MAX_THREADS = 3;
    private DiskIOThreadExecutor mDiskIO;
    private Executor mNetworkIO;

    public AppExecutors() {
        this(new DiskIOThreadExecutor(), Executors.newFixedThreadPool(MAX_THREADS));
    }

    public AppExecutors(DiskIOThreadExecutor mDiskIO, Executor mNetworkIO) {
        this.mDiskIO = mDiskIO;
        this.mNetworkIO = mNetworkIO;
    }

    public DiskIOThreadExecutor diskIO() {
        return mDiskIO;
    }

    public Executor networkIO() {
        return mNetworkIO;
    }
}
