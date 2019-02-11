package com.kimboofactory.iconvert.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kimboofactory.iconvert.util.IConvertDBUtil;

/**
 * Created by khranyt on 16/10/15.
 */
public class IConvertDBHelper extends SQLiteOpenHelper {

    private static final String TAG = IConvertDBHelper.class.getSimpleName();
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "iconvert.db";

    private static Object lock = new Object();
    private static IConvertDBHelper instance;

    private IConvertDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static IConvertDBHelper newInstance(Context context) {
        synchronized (lock) {
            Log.d(TAG, "inside lock");
            if(null == instance) {
                instance = new IConvertDBHelper(context);
            }
        }
        return instance;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.d(TAG, "database is open");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "sql : " + IConvertDBUtil.createTableTauxChange());
        db.execSQL(IConvertDBUtil.createTableTauxChange());

        Log.d(TAG, "sql : " + IConvertDBUtil.createTableDevise());
        db.execSQL(IConvertDBUtil.createTableDevise());

        Log.d(TAG, "Both tables are created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 1. Destroy the current db
        // 2. Create new one
        Log.d(TAG, "Upgrading database. Existing contents will be lost. ["
                + oldVersion + "]->[" + newVersion + "]");

        db.execSQL(IConvertDBUtil.dropTableTauxChange()); // for development purposes
        db.execSQL(IConvertDBUtil.dropTableDevise()); // for development purposes
        onCreate(db);
    }
}
