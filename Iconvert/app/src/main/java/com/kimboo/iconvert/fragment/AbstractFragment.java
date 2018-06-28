package com.kimboo.iconvert.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by gnemoka on 28/06/2018.
 * Copyright (c) 2018. All rights reserved.
 * gnemo.ka@gmail.com
 */
public abstract class AbstractFragment extends Fragment {

    //protected abstract BaseFragment newInstance();

    /**
     *
     * @return
     */
    protected abstract int getLayout();

    /**
     *
     * @return
     */
    protected abstract String getDebugTag();

    //---------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getDebugTag(), "onCreated() is called");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(getDebugTag(), "onAttach() is called");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.d(getDebugTag(), "onCreateView() is called!");
        final View v = inflater.inflate(getLayout(), container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(getDebugTag(), "onViewCreated() is called!");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(getDebugTag(), "onSaveInstanceState() is called!");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(getDebugTag(), "onActivityCreated() is called");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(getDebugTag(), "onStart() is called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(getDebugTag(), "onResume() is called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(getDebugTag(), "onPause() is called!");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(getDebugTag(), "OnStop() is called!");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(getDebugTag(), "onDestroyView() is called!");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(getDebugTag(), "onDestroy() is called!");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(getDebugTag(), "onDetach is called");
    }
}
