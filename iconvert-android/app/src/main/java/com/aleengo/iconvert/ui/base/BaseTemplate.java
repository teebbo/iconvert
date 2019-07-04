package com.aleengo.iconvert.ui.base;

import androidx.annotation.LayoutRes;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public interface BaseTemplate {

    void initialize();
    void inflate(@LayoutRes int resid);
}
