package com.kimboofactory.iconvert.common;

import lombok.Getter;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public abstract class AbstractPresenter<MvpView extends BaseView> {

    @Getter
    private MvpView mvpView;

    public AbstractPresenter() { }

    public void attach(MvpView mvpView) {
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
