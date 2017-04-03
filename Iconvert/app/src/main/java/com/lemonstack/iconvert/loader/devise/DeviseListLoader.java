package com.lemonstack.iconvert.loader.devise;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.util.Log;

import com.lemonstack.iconvert.dao.devise.DeviseDao;
import com.lemonstack.iconvert.dao.devise.DeviseDaoImpl;

/**
 * Created by khranyt on 31/03/2017.
 */

public class DeviseListLoader extends CursorLoader {

    private static final String TAG = DeviseListLoader.class.getName();

    private Context context;

    public DeviseListLoader(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public Cursor loadInBackground() {
        final DeviseDao deviseDao = new DeviseDaoImpl(context);
        Log.d(TAG, "loadInBackground() is called");
        return deviseDao.list();
    }
}
