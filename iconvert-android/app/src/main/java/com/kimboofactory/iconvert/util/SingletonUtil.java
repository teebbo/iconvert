package com.kimboofactory.iconvert.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SingletonUtil {

    private SingletonUtil(){}

    public static <T> T getInstance(Class<T> cls, T instance) {

        if (instance == null) {
            synchronized (cls) {
                if (instance == null) {
                    try {
                        final Class[] ctorArgs = null;
                        final Constructor defaultCtor = cls.getDeclaredConstructor(ctorArgs);
                        defaultCtor.setAccessible(true);

                        final Object[] initArgs = null;
                        instance = (T) defaultCtor.newInstance(initArgs);
                    } catch (IllegalAccessException | NoSuchMethodException |
                            InvocationTargetException| InstantiationException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return instance;
    }
}
