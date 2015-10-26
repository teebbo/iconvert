package fr.enst.igr201.kanmogne.iconvert.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

/**
 * Created by joffrey on 16/10/15.
 */
public class CurrencyProvider extends ContentProvider {

    static private final String TAG = CurrencyProvider.class.getSimpleName();

    private CurrencyDBHelper mCurrencyDB;

    static private final int CURRENCY = 100;
    static private final int CURRENCY_RATE = 101;
    static private final int CURRENCY_ID = 102;
    static private final int CURRENCY_FULL_NAME = 200;
    static private final int CURRENCY_FULL_NAME_ONLY = 201;
    static private final int CURRENCY_RATE_AND_FULLNAME = 300;

    static private final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static private SQLiteQueryBuilder sCurrencyQueryBuilder;

    static {
        sCurrencyQueryBuilder = new SQLiteQueryBuilder();
        /*String currencyTable = CurrencyContract.Currency.TABLE_CURRENCY;
        String currencyFullNameTable = CurrencyContract.CurrencyFullName.T_CURRENCY_FULLNAME;

        String currencyColumnName = CurrencyContract.Currency.TABLE_CURRENCY +
                "." + CurrencyContract.Currency.COLUMN_NAME;
        String currencyFullNameColumnName = CurrencyContract.CurrencyFullName.T_CURRENCY_FULLNAME +
                "." + CurrencyContract.CurrencyFullName.COLUMN_NAME;

        sCurrencyQueryBuilder.setTables(
                String.format("%s INNER JOIN %s ON %s = %s",
                        currencyFullNameTable,
                        currencyTable,
                        currencyFullNameColumnName,
                        currencyColumnName));*/
    }

    // Selection criteria
    // currency.name = ?
    static private final String mCurrencyNameSelection = String.format("%s.%s = ?",
            CurrencyContract.Currency.TABLE_CURRENCY,
            CurrencyContract.Currency.COLUMN_NAME);


    private Cursor getRateRelativeToName(Uri uri, String[] projection, String selection, String[] selectionArgs){

        Log.d(TAG, "Inside getRateRelativeToName ... URI : " + uri.toString());
        return mCurrencyDB.getReadableDatabase()
                .query(CurrencyContract.Currency.TABLE_CURRENCY,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null);
    }

    private Cursor getCurrencyByRateNadFullName(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){
        Log.d(TAG, "Inside getCurrencyByRateAndFullName ... URI : " + uri.toString());

        //String selection = CurrencyContract.Currency.TABLE_CURRENCY + "." + CurrencyContract.Currency.COLUMN_NAME + "= ?";

        Log.d(TAG, CurrencyContract.CurrencyFullName.getCurrencyNameFromURI(uri));
        //String[] selectionArgs = {CurrencyContract.CurrencyFullName.getCurrencyNameFromURI(uri)};

        sCurrencyQueryBuilder.appendWhere ( CurrencyContract.Currency.TABLE_CURRENCY + "." + CurrencyContract.Currency.COLUMN_NAME +
                "=" + CurrencyContract.CurrencyFullName.getCurrencyNameFromURI(uri) );
        return sCurrencyQueryBuilder.query(mCurrencyDB.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
    }

    static {
        //final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authority = CurrencyContract.CONTENT_AUTORITY;

        sURIMatcher.addURI(authority, CurrencyContract.CURRENCY_PATH, CURRENCY); // uri : content://authority/currency <-> 100
        sURIMatcher.addURI(authority,  CurrencyContract.CURRENCY_FULL_NAME_PATH, CURRENCY_FULL_NAME); // uri : content://authority/currencyFullname <-> 200
        sURIMatcher.addURI(authority,  CurrencyContract.CURRENCY_PATH + "/*", CURRENCY_RATE); // projection du taux de change pour une monnaie donnée
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "Inside onCreate of ContentProvider");
        mCurrencyDB = new CurrencyDBHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        Log.d(TAG, "Inside getType of ContentProvider. Uri : " + uri.toString());
        int code = sURIMatcher.match(uri);
        switch (code){
            case CURRENCY:
                return CurrencyContract.Currency.CONTENT_TYPE;
            case CURRENCY_RATE:
                return CurrencyContract.Currency.CONTENT_TYPE;
            case CURRENCY_FULL_NAME:
                return CurrencyContract.CurrencyFullName.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown URI : " + uri);

        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "Query uri : " + uri.toString());
        Cursor cursor;
        int code = sURIMatcher.match(uri);

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(CurrencyContract.Currency.TABLE_CURRENCY);

        switch (code){
            // currency
            case CURRENCY:
                cursor = mCurrencyDB.getReadableDatabase()
                        .query(CurrencyContract.Currency.TABLE_CURRENCY,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null,
                                sortOrder);
                break;

            // currency.name = ?
            case CURRENCY_RATE:
                Log.wtf(TAG, "-->>" + CURRENCY_RATE);
                Log.wtf(TAG, uri.getLastPathSegment());

                cursor = getRateRelativeToName(uri, projection, selection, selectionArgs);
                break;

            // currency_fullname
            case CURRENCY_FULL_NAME:
                cursor = mCurrencyDB.getReadableDatabase()
                        .query(CurrencyContract.CurrencyFullName.T_CURRENCY_FULLNAME,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null,
                                sortOrder);
                break;

            case CURRENCY_RATE_AND_FULLNAME:
                Log.d(TAG, "" + CURRENCY_RATE_AND_FULLNAME);
                Log.d(TAG, sCurrencyQueryBuilder.getTables());
                cursor = getCurrencyByRateNadFullName(uri, projection, selection, selectionArgs, sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the context's ContentResolver if the cursor result set changes
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "Insert uri : " + uri.toString());
        final SQLiteDatabase db = mCurrencyDB.getWritableDatabase();
        final int match = sURIMatcher.match(uri);
        Uri retUri = null;

        switch (match){
            case CURRENCY:
                long rowID = db.insert(CurrencyContract.Currency.TABLE_CURRENCY, null, values);

                if(rowID > 0){
                    // return an URI to the newly created row
                    retUri = ContentUris.withAppendedId(CurrencyContract.Currency.CONTENT_URI, rowID);
                }
                else
                    throw new SQLException("Failed to insert row into " + uri.toString());
                break;
            case CURRENCY_FULL_NAME:
                long recordID = db.insert(CurrencyContract.CurrencyFullName.T_CURRENCY_FULLNAME, null, values);

                if(recordID > 0){
                    // return an URI to the newly created row
                    retUri = ContentUris.withAppendedId(CurrencyContract.CurrencyFullName.CONTENT_URI, recordID);
                }
                else
                    throw new SQLException("Failed to insert row into " + uri.toString());
                break;
        }

        // Notify the context's ContentResolver of the change
        getContext().getContentResolver().notifyChange(retUri, null);
        return retUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "Delete uri : " + uri.toString());
        SQLiteDatabase db = mCurrencyDB.getWritableDatabase();

        int rowsDeleted = 0;

        final int match  = sURIMatcher.match(uri);
        if(match != CURRENCY)
            throw new IllegalArgumentException("Unsupported URI : " + uri.toString());

        rowsDeleted = db.delete(CurrencyContract.Currency.TABLE_CURRENCY, selection, selectionArgs);

        // If rows has been deleted, Notify the context's ContentResolver of the change
        if(rowsDeleted != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(TAG, "Update uri : " + uri.toString());
        SQLiteDatabase db = mCurrencyDB.getWritableDatabase();

        int rowsUpdated = 0;

        final int match  = sURIMatcher.match(uri);
        if(match != CURRENCY)
            throw new IllegalArgumentException("Unsupported URI : " + uri.toString());

        rowsUpdated = db.update(CurrencyContract.Currency.TABLE_CURRENCY, values, selection, selectionArgs);

        // If rows has been updated, Notify the context's ContentResolver of the change
        if(rowsUpdated != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {

        Log.d(TAG, "Inside BulkInsert method. Uri : " + uri.toString());
        final SQLiteDatabase db  = mCurrencyDB.getWritableDatabase();
        final int match  = sURIMatcher.match(uri);

        // count all items add in the database
        int count = 0;

        switch(match){
            case CURRENCY:
                Log.d(TAG, " Currency Match " + String.valueOf(match));
                db.beginTransaction();
                for(ContentValues value : values){
                    long rowInserted = db.insert(CurrencyContract.Currency.TABLE_CURRENCY, null, value);
                    if(rowInserted != 0)
                        count++;
                }
                db.setTransactionSuccessful();
                db.endTransaction();
                break;

            case CURRENCY_FULL_NAME:
                Log.d(TAG, " Currency Full Name Match " + String.valueOf(match));
                db.beginTransaction();
                for(ContentValues value : values){
                    long rowInserted = db.insert(CurrencyContract.CurrencyFullName.T_CURRENCY_FULLNAME, null, value);
                    if(rowInserted != 0)
                        count++;
                }
                db.setTransactionSuccessful();
                db.endTransaction();
                break;
            default:
                return super.bulkInsert(uri, values);
        }

        // Notify the context's ContentResolver of the change
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    @TargetApi(11)
    public void shutdown() {
        mCurrencyDB.close();
        super.shutdown();
    }
}
