package com.kimboofactory.iconvert.dao.tauxchange;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kimboofactory.iconvert.data.IConvertContract;
import com.kimboofactory.iconvert.dao.GenericDao;
import com.kimboofactory.iconvert.model.TauxChange;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by khranyt on 31/03/2017.
 */

public class TauxChangeDaoImpl extends GenericDao
        implements TauxChangeDao {

    private static final String TAG = TauxChangeDaoImpl.class.getName();
    private static final int COL_ID_IDX = 0;
    private static final int COL_CODE_IDX = 1;
    private static final int COL_TAUX_CHANGE_IDX = 2;

    private Context context;

    @Override
    public TauxChange createEntity(Cursor c) {
        return new TauxChange(c.getLong(COL_ID_IDX), c.getString(COL_CODE_IDX), c.getDouble(COL_TAUX_CHANGE_IDX));
    }

    public TauxChangeDaoImpl(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public Cursor list() {
        final SQLiteDatabase db = getDb();

        db.beginTransaction();
        final Cursor cursor = db.query(IConvertContract.TauxChange.T_TAUX_CHANGE, null, null, null, null, null, null);

        db.setTransactionSuccessful();
        db.endTransaction();

        closeConnexion();
        return cursor;
    }

    @Override
    public long save(List<TauxChange> tauxChanges) {
        int count = 0;
        final SQLiteDatabase db = getDb();
        final String whereClause = IConvertContract.TauxChange.COL_CODE + "=?";

        db.beginTransaction();
        for (TauxChange tauxChange : tauxChanges) {
            // ContentValues is a name:value pair that makes it easy for us
            // to insert data in the database
            final ContentValues value = new ContentValues();
            value.put(IConvertContract.TauxChange.COL_CODE, tauxChange.getCode());
            value.put(IConvertContract.TauxChange.COL_TAUX_CHANGE, tauxChange.getTauxChange());

            final String[] whereArgs = {tauxChange.getCode()};
            final int rowUpdated = db.update(IConvertContract.TauxChange.T_TAUX_CHANGE, value, whereClause, whereArgs);
            if(rowUpdated == 0) {
                final long rowInserted = db.insert(IConvertContract.TauxChange.T_TAUX_CHANGE, null, value);
                if(rowInserted != 0) {
                    count++;
                }
            }
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        closeConnexion();
        return count;
    }

    @Override
    public void save(JSONObject tauxChangeJson) {

        final List<TauxChange> tauxChanges = new ArrayList<>();

        if (tauxChangeJson != null) {
            Log.i(TAG, "currencies rate operation");

            try {
                final Iterator<String> iterator = tauxChangeJson.keys();
                while(iterator.hasNext()) {
                    final String tauxChangeCode = iterator.next();
                    final Double tauxChangeValue = new Double(tauxChangeJson.getDouble(tauxChangeCode));

                    tauxChanges.add(new TauxChange(tauxChangeCode, tauxChangeValue));
                }

                long inserted = save(tauxChanges);
                Log.d(TAG, inserted + " rows inserted.");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public TauxChange getByCode(final String code) {
        final Cursor c = super.getByCode(IConvertContract.TauxChange.T_TAUX_CHANGE, IConvertContract.TauxChange.COL_CODE, code);
        return cursorToTauxChange(c);
    }

    private TauxChange cursorToTauxChange(final Cursor c) {
        TauxChange tauxChange = null;
        if(c.moveToFirst()) {
            tauxChange = new TauxChange(c.getLong(0), c.getString(1), c.getDouble(2));
        }
        return tauxChange;
    }

}
