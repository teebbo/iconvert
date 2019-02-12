package com.kimboofactory.iconvert.common;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import butterknife.ButterKnife;


/**
 * Created by CK_ALEENGO on 28/06/2018.
 * Copyright (c) 2018. All rights reserved.
 */
public abstract class BaseFragment extends Fragment implements UiCommon {

    private static final boolean DEBUG = true;

    public FragmentActivity getParentActivity() {
        return getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (DEBUG) Log.d(getClassName(), "onCreated() is called");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (DEBUG) Log.d(getClassName(), "onAttach() is called");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (DEBUG) Log.d(getClassName(), "onCreateView() is called!");
        return inflater.inflate(getLayoutResId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (DEBUG) Log.d(getClassName(), "onViewCreated() is called!");

        ButterKnife.bind(this, view);
        onInitialize();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (DEBUG) Log.d(getClassName(), "onSaveInstanceState() is called!");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (DEBUG) Log.d(getClassName(), "onActivityCreated() is called");
    }

    @Override
    public void onStart() {
        super.onStart();
        if (DEBUG) Log.d(getClassName(), "onStart() is called");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (DEBUG) Log.d(getClassName(), "onResume() is called");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (DEBUG) Log.d(getClassName(), "onPause() is called!");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (DEBUG) Log.d(getClassName(), "OnStop() is called!");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (DEBUG) Log.d(getClassName(), "onDestroyView() is called!");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (DEBUG) Log.d(getClassName(), "onDestroy() is called!");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (DEBUG) Log.d(getClassName(), "onDetach is called");
    }
}
