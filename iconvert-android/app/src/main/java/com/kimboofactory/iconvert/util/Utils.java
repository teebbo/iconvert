package com.kimboofactory.iconvert.util;

import com.kimboofactory.iconvert.dto.CurrencyIHM;

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

    public static CurrencyIHM computeAmount(CurrencyIHM src, CurrencyIHM dst) {
        final float computeRate = Float.valueOf(dst.getEntity().getValue()) / Float.valueOf(src.getEntity().getValue());
        dst.setComputeRate("" + computeRate);
        dst.setAmount("" + computeRate * Float.valueOf(src.getAmount()));
        return dst;
    }
}