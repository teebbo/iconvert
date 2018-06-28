package com.kimboo.iconvert.loader;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.util.Log;

/**
 * Created by gnemoka on 28/06/2018.
 */
public abstract class AbstractCursorLoader extends CursorLoader {

    /*
    *
    * PROTECTED METHODS
    */
    protected abstract String getDebugTag();

    public AbstractCursorLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        Log.d(getDebugTag(), "loadInBackground() is called");
        return super.loadInBackground();
    }

    @Override
    public void cancelLoadInBackground() {
        super.cancelLoadInBackground();
        Log.d(getDebugTag(), "cancelLoadInBackground() is called");
    }

    @Override
    public void onCanceled(Cursor cursor) {
        super.onCanceled(cursor);
        Log.d(getDebugTag(), "onCanceled() is called");
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.d(getDebugTag(), "onStartLoading() is called");
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        Log.d(getDebugTag(), "onStopLoading() is called");
    }

    @Override
    public void deliverResult(Cursor cursor) {
        super.deliverResult(cursor);
        Log.d(getDebugTag(), "deliverResult() is called");
    }
}

