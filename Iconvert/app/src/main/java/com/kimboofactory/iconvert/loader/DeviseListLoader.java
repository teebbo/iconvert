package com.kimboofactory.iconvert.loader;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.kimboofactory.iconvert.dao.devise.DeviseDao;
import com.kimboofactory.iconvert.dao.devise.DeviseDaoImpl;

/**
 * Created by khranyt on 31/03/2017.
 */

public class DeviseListLoader extends AbstractCursorLoader {

    private static final String TAG = "DeviseListLoader";

    @Override
    protected String getDebugTag() {
        return TAG;
    }

    public DeviseListLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        Log.d(getDebugTag(), "loadInBackground() is called");

        final DeviseDao deviseDao = new DeviseDaoImpl(getContext());
        return deviseDao.list();
    }
}
