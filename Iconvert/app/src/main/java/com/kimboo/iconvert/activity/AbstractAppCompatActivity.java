package com.kimboo.iconvert.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.kimboo.iconvert.R;

/**
 * Created by gnemoka on 28/06/2018.
 * Copyright (c) 2018. All rights reserved.
 * gnemo.ka@gmail.com
 */
public abstract class AbstractAppCompatActivity extends AppCompatActivity {

    // ----------------- PROTECTED METHODS -----------------------

    protected abstract String getDebugTag();
    protected abstract int getLayout();

    // -----------------------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(getDebugTag(), "onCreate() method called");
        setContentView(getLayout());

        // replace the actionBar by the toolbar
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        Log.d(getDebugTag(), "onAttachFragment --> " + fragment.toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(getDebugTag(), "onStart() method called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(getDebugTag(), "onResume() method called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(getDebugTag(), "onPause() method called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(getDebugTag(), "onRestart() method called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(getDebugTag(), "onDestroy() method called");
    }
}
