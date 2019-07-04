package com.aleengo.iconvert.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 25/01/2019
 */
@Setter
@Getter
public final class Resource<T>{

    private T data;
    private Throwable e;

    private Resource(Throwable e, T data) {
        this.data = data;
        this.e = e;
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(null, data);
    }

    public static <T> Resource<T> error(Throwable e) {
        return new Resource<>(e, null);
    }

    public boolean failed() {
        return e != null;
    }

    public boolean succeed() {
        return data != null;
    }
}
