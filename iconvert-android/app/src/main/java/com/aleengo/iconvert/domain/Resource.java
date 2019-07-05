package com.aleengo.iconvert.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 25/01/2019
 */
@Setter
@Getter
public final class Resource<T>{

    public T data;
    public Throwable e;

    private Resource(Throwable e, T data) {
        this.e = e;
        this.data = data;
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(null, data);
    }

    public static <T> Resource<T> error(Throwable e) {
        return new Resource<>(e, null);
    }

    public boolean hasError() {
        return e != null;
    }

    public boolean isSuccess() {
        return data != null;
    }
}
