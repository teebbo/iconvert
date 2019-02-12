package com.kimboofactory.iconvert.presentation;

import com.kimboofactory.iconvert.common.BasePresenter;

import lombok.Getter;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public abstract class AbstractView {

    @Getter
    private BasePresenter presenter;

    public AbstractView() {
    }

    protected void attachPresenter(BasePresenter presenter) {
        this.presenter = presenter;
    }

}
