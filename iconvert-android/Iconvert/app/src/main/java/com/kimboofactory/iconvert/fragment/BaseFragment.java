package com.kimboofactory.iconvert.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimboofactory.iconvert.UiContract;


/**
 * 28/06/2018.
 */
public abstract class BaseFragment extends Fragment implements UiContract {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getClassName(), "onCreated() is called");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(getClassName(), "onAttach() is called");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.d(getClassName(), "onCreateView() is called!");
        return inflater.inflate(getLayoutResId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(getClassName(), "onViewCreated() is called!");

        initialize(view);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(getClassName(), "onSaveInstanceState() is called!");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(getClassName(), "onActivityCreated() is called");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(getClassName(), "onStart() is called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(getClassName(), "onResume() is called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(getClassName(), "onPause() is called!");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(getClassName(), "OnStop() is called!");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(getClassName(), "onDestroyView() is called!");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(getClassName(), "onDestroy() is called!");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(getClassName(), "onDetach is called");
    }
}
