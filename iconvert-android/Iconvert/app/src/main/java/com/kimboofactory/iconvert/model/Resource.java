package com.kimboofactory.iconvert.model;

/**
 * 25/01/2019
 */
public class Resource<T> {

    private T value;

    public Resource(){}

    public Resource(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
