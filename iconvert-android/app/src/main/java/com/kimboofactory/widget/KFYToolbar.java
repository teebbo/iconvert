package com.kimboofactory.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.kimboofactory.iconvert.R;

import java.lang.reflect.Type;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;


/**
 * Created by CK_ALEENGO on 12/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class KFYToolbar extends Toolbar {

    private static final int LAYOUT_NOT_DEFINED = -7;

    private int customLayout;

    public KFYToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // inflate the custom toolbar
        inflate(context, R.layout.kfy_toolbar, this);
        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.KFYToolbar);

        try {
            customLayout = ta.getResourceId(R.styleable.KFYToolbar_child_layout, LAYOUT_NOT_DEFINED);
            if (customLayout != LAYOUT_NOT_DEFINED) {
                final View child = inflate(context, customLayout, null);
                addView(child);
            }
        } finally {
            ta.recycle();
        }
    }
}
