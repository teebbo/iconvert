package com.lemonstack.iconvert.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.lemonstack.iconvert.data.IConvertContract;
import com.lemonstack.iconvert.data.IConvertDBHelper;

/**
 * Created by khranyt on 16/10/15.
 */
public abstract class GenericDao {

    static private final String TAG = GenericDao.class.getSimpleName();

    private Context context;
    private IConvertDBHelper dbHelper;

    public GenericDao(Context context) {
        this.context = context;
    }

    protected SQLiteDatabase getDb() {
       dbHelper = IConvertDBHelper.newInstance(context);
        return dbHelper.getWritableDatabase();
    }

    protected void closeConnexion() {
        dbHelper.close();
    }

    protected Cursor getByCode(final String table, final String code) {

        final SQLiteDatabase db = getDb();

        //final String
        db.beginTransaction();

        final Cursor cursor = db.query(table, null,
                IConvertContract.TauxChange.COL_CODE + "=?", new String[] {code}, null, null, null);
        db.setTransactionSuccessful();
        db.endTransaction();

        return cursor;

    }

}
