package com.aleengo.iconvert.ui.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.CallSuper;

import butterknife.Unbinder;


/**
 * Copyright (c) Aleengo, 2019. All rights reserved.
 * Created by bau.cj on 04/07/2019.
 */
public class MvpView extends FrameLayout {

    protected Unbinder unbinder;

    public MvpView(Context context) {
        super(context);
    }

    public MvpView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @CallSuper
    public void clear() {
        unbinder.unbind();
    }
}
