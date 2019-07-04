package com.aleengo.iconvert.ui.base;

import androidx.annotation.CallSuper;

import lombok.Getter;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public abstract class Presenter<Template extends BaseTemplate> {

    @Getter
    private Template template;

    public Presenter() { }

    public void attach(Template template) {
        if (this.template != null) {
            throw new IllegalArgumentException("MvpView is already attached.");
        }
        this.template = template;
    }

    protected void detach() {
        if (this.template == null) {
            throw new RuntimeException("MvpView is already detached.");
        }
        this.template = null;
    }

    @CallSuper
    public void clear() {
        detach();
    }
}
