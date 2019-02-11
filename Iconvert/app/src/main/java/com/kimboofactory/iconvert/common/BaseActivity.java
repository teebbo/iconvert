package com.kimboofactory.iconvert.common;

import android.os.Bundle;
import android.util.Log;

import com.kimboofactory.iconvert.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

/**
 * Created by CK_ALEENGO on 28/06/2018.
 * Copyright (c) 2018. All rights reserved.
 */
public abstract class BaseActivity
        extends AppCompatActivity
        implements UiCommon {

    private static final boolean DEBUG = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (DEBUG) Log.d(getClassName(), "onCreate() method called");
        setContentView(getLayoutResId());

        // replace the actionBar by the toolbar
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        onInitialize();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (DEBUG) Log.d(getClassName(), "onAttachFragment --> " + fragment.toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (DEBUG) Log.d(getClassName(), "onStart() method called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (DEBUG) Log.d(getClassName(), "onResume() method called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (DEBUG) Log.d(getClassName(), "onPause() method called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (DEBUG) Log.d(getClassName(), "onRestart() method called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (DEBUG) Log.d(getClassName(), "onDestroy() method called");
    }
}
