package com.kimboofactory.iconvert.common;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.kimboofactory.iconvert.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

/**
 * Created by CK_ALEENGO on 28/06/2018.
 * Copyright (c) 2018. All rights reserved.
 */
public abstract class BaseActivity extends AppCompatActivity implements UiCommon {

    private static final boolean DEBUG = true;

    protected void onResult(int requestCode, int resultCode, @Nullable Intent data) {
        // do nothing
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (DEBUG) Log.d(getClassName(), "onCreate() method called");
        setContentView(getLayoutResId());

        ButterKnife.bind(this);
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (DEBUG) Log.d(getClassName(), "onSaveInstanceState() method called");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (DEBUG) Log.d(getClassName(), "onRestoreInstanceState() method called");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (DEBUG) Log.d(getClassName(), "onActivityResult() method called");

        onResult(requestCode, resultCode, data);
    }
}
