package com.kimboofactory.iconvert.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.UiContract;
import com.kimboofactory.iconvert.model.Resource;

/**
 * 28/06/2018
 */
public abstract class BaseActivity extends AppCompatActivity implements UiContract {

    protected Resource getResource(final Class type, final int resId) {

        final String className = type.getName();
        final Resource resource = new Resource<>();

        if(String.class.getName().equals(className)) {
            resource.set(getResources().getString(resId));
        } else if (Integer.class.getName().equals(className)) {
            resource.set(getResources().getInteger(resId));
        }
        return resource;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(getClassName(), "onCreate() method called");
        setContentView(getLayoutResId());

        // replace the actionBar by the toolbar
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialize(null);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        Log.d(getClassName(), "onAttachFragment --> " + fragment.toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(getClassName(), "onStart() method called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(getClassName(), "onResume() method called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(getClassName(), "onPause() method called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(getClassName(), "onRestart() method called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(getClassName(), "onDestroy() method called");
    }
}
