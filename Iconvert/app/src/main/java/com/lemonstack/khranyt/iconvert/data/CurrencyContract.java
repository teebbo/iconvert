package com.lemonstack.khranyt.iconvert.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by khranyt on 16/10/15.
 */

/* Defines the tables and columns for Currency Databases*/
public class CurrencyContract {

    // the content authority
    static public final String CONTENT_AUTORITY = "com.lemonstack.khranyt.iconvert.provider";

    // base content uri
    static public final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTORITY);

    // define possible paths
    static public final String CURRENCY_PATH = "currency";
    static public final String CURRENCY_FULL_NAME_PATH = "currency_full_name";

    /* Inner class that represents the currency table */
    static public class Currency implements BaseColumns{

        public Currency(){}

        // Build our Content URI
        static public final Uri CONTENT_URI =
                BASE_CONTENT_URI
                .buildUpon()
                .appendPath(CURRENCY_PATH)
                .build();

        // The MIME type of all the currencies
        static public final String CONTENT_TYPE = String.format(
                "%s/%s/%s",
                ContentResolver.CURSOR_DIR_BASE_TYPE,
                CONTENT_AUTORITY,
                CURRENCY_PATH);

        // The MIME type of a single currency
        static public final String CONTENT_ITEM_TYPE = String.format(
                "%s/%s/%s",
                ContentResolver.CURSOR_ITEM_BASE_TYPE,
                CONTENT_AUTORITY,
                CURRENCY_PATH);

        // table name
        static public final String TABLE_CURRENCY = "currency";

        // name of the currency
        static public final String COLUMN_NAME = "name";

        // rate
        static public final String COLUMN_RATE = "rate";

        static public Uri buildCurrencyRateURI(String currencyName){
            return CONTENT_URI.buildUpon().appendPath(currencyName).build();
        }

        static public String getCurrencyNameFromURI(Uri uri){
            return uri.getPathSegments().get(1);
        }

        static public Uri buildCurrencyUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    static public class CurrencyFullName implements BaseColumns {

        public CurrencyFullName() {}

        // Build our Content URI
        static public final Uri CONTENT_URI =
                BASE_CONTENT_URI
                        .buildUpon()
                        .appendPath(CURRENCY_FULL_NAME_PATH)
                        .build();

        // The MIME type of all the currencies
        static public final String CONTENT_TYPE = String.format(
                "%s/%s/%s",
                ContentResolver.CURSOR_DIR_BASE_TYPE,
                CONTENT_AUTORITY,
                CURRENCY_FULL_NAME_PATH);

        // The MIME type of a single currency
        static public final String CONTENT_ITEM_TYPE = String.format(
                "%s/%s/%s",
                ContentResolver.CURSOR_ITEM_BASE_TYPE,
                CONTENT_AUTORITY,
                CURRENCY_FULL_NAME_PATH);


        // table name
        static public final String T_CURRENCY_FULLNAME = "currency_full_name";

        // column name
        static public final String COLUMN_NAME = "name";

        // column fullname
        static public final String COLUMN_FULL_NAME = "fullname";

        static public String getCurrencyNameFromURI(Uri uri){
            return uri.getPathSegments().get(1);
        }

        static public Uri buildCurrencyRateAndFullName(String currencyName){
            return CONTENT_URI.buildUpon().appendPath(currencyName).build();
        }
    }
}
