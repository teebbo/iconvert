package com.coconhub.khranyt.iconvert.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by khranyt on 16/10/15.
 */
public class CurrencyDBHelper extends SQLiteOpenHelper {

    static private final String TAG = CurrencyDBHelper.class.getSimpleName();
    static private final int DB_VERSION = 1;
    static private final String DB_NAME = "iconvert.db";

    public CurrencyDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_CURRENCY_TABLE = String.format("CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY," +
                        "%s TEXT NOT NULL, " +
                        "%s REAL NOT NULL );",
                CurrencyContract.Currency.TABLE_CURRENCY,
                CurrencyContract.Currency._ID,
                CurrencyContract.Currency.COLUMN_NAME,
                CurrencyContract.Currency.COLUMN_RATE);

        final String CREATE_CURRENCY_FULL_NAME_TABLE = String.format("CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY," +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL );",
                CurrencyContract.CurrencyFullName.T_CURRENCY_FULLNAME,
                CurrencyContract.CurrencyFullName._ID,
                CurrencyContract.CurrencyFullName.COLUMN_NAME,
                CurrencyContract.CurrencyFullName.COLUMN_FULL_NAME);

        Log.d(TAG, "sql : " + CREATE_CURRENCY_TABLE);
        db.execSQL(CREATE_CURRENCY_TABLE);

        Log.d(TAG, "sql : " + CREATE_CURRENCY_FULL_NAME_TABLE);
        db.execSQL(CREATE_CURRENCY_FULL_NAME_TABLE);

        Log.d(TAG, "Both tables are created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 1. Destroy the current db
        // 2. Create new one
        Log.w(TAG, "Upgrading database. Existing contents will be lost. ["
                + oldVersion + "]->[" + newVersion + "]");

        final String DROP_CURRENCY_TABLE = String.format("DROP TABLE IF EXISTS %s;",
                CurrencyContract.Currency.TABLE_CURRENCY);

        final String DROP_CURRENCY_FULL_NAME_TABLE = String.format("DROP TABLE IF EXISTS %s;",
                CurrencyContract.CurrencyFullName.T_CURRENCY_FULLNAME);

        db.execSQL(DROP_CURRENCY_TABLE); // for development purposes
        db.execSQL(DROP_CURRENCY_FULL_NAME_TABLE); // for development purposes
        onCreate(db);
    }
}
