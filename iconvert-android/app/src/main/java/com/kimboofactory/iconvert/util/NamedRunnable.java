package com.kimboofactory.iconvert.util;

import com.aleengo.peach.toolbox.common.PeachConfig;

/**
 * Created by CK_ALEENGO on 26/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public abstract class NamedRunnable implements Runnable {

    protected final String name;
    public NamedRunnable(String format, Object... args) {
        this.name = String.format(format, args);
    }

    @Override
    public void run() {
        if (PeachConfig.isDebug()) System.out.println(name  + "[" + Thread.currentThread().getName() + "]");
        execute();
    }
    protected abstract void execute();
}
