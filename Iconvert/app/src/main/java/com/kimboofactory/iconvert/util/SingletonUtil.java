package com.kimboofactory.iconvert.util;

public class SingletonUtil {

    private SingletonUtil(){}

    public static <T> T getInstance(Class<T> cls, T instance) {

        if (instance == null) {
            synchronized (cls) {
                if (instance == null) {
                    try {
                        instance = cls.newInstance();
                    } catch (IllegalAccessException |
                            InstantiationException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return instance;
    }
}
