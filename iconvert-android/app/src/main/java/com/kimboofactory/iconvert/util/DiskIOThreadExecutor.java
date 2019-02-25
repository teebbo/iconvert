package com.kimboofactory.iconvert.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by CK_ALEENGO on 25/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class DiskIOThreadExecutor implements Executor {

    private final Executor mDiskIOService;

    public DiskIOThreadExecutor() {
        mDiskIOService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(Runnable command) {
        mDiskIOService.execute(command);
    }
}
