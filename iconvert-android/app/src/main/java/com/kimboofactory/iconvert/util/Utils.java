package com.kimboofactory.iconvert.util;

import java.util.concurrent.ThreadFactory;

public class Utils {

    private Utils() {
    }

    public static ThreadFactory threadFactory(String name, boolean deamon) {
        return new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                final String oldName = Thread.currentThread().getName();
                final Thread thread = new Thread(runnable, name + "[" + oldName + "]");
                thread.setDaemon(deamon);
                return thread;
            }
        };
    }
}