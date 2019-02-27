package com.kimboofactory.iconvert.common;

import lombok.Getter;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public abstract class AbstractPresenter {

    @Getter
    private BaseView mvpView;

    public AbstractPresenter() {
    }

    public void attach(BaseView mvpView) {
        if (this.mvpView != null) {
            throw new IllegalArgumentException("mvpView is already attached");
        }
        this.mvpView = mvpView;
    }

    public void detach() {
        if (this.mvpView == null) {
            throw new RuntimeException("mvpView is already detached.");
        }
        this.mvpView = null;
    }
}
