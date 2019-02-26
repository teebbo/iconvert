package com.kimboofactory.iconvert.util;

import com.aleengo.peach.toolbox.common.PeachConfig;

import java.util.concurrent.Callable;

/**
 * Created by CK_ALEENGO on 26/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public abstract class NamedCallable<V> implements Callable<V> {

    private final String name;

    public NamedCallable(String format, Object... args) {
        this.name = String.format(format, args);
    }

    @Override
    public V call() throws Exception {
        if (PeachConfig.isDebug()) System.out.println(name  + "[" + Thread.currentThread().getName() + "]");
        return execute();
    }

    protected abstract V execute();
}
