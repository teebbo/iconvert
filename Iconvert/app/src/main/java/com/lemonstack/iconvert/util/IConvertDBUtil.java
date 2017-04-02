package com.lemonstack.iconvert.util;

import com.lemonstack.iconvert.data.IConvertContract;

/**
 * Created by khranyt on 15/03/2017.
 */

public class IConvertDBUtil {

    private static final String SCHEMA = "iconvert";

    public static String createTableTauxChange() {
        final StringBuilder sb = new StringBuilder(500);
        sb.append("CREATE TABLE IF NOT EXISTS " + IConvertContract.TauxChange.T_TAUX_CHANGE)
                .append(" ( ")
                .append(IConvertContract.TauxChange.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(IConvertContract.TauxChange.COL_CODE + " TEXT NON NULL UNIQUE, ")
                .append(IConvertContract.TauxChange.COL_TAUX_CHANGE + " REAL NON NULL ")
                .append(" );") ;
        return sb.toString();
    }

    public static String createTableDevise() {
        final StringBuilder sb = new StringBuilder(500);
        sb.append("CREATE TABLE IF NOT EXISTS " + IConvertContract.Devise.T_DEVISE)
                .append(" ( ")
                .append(IConvertContract.Devise.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(IConvertContract.Devise.COL_CODE + " TEXT NON NULL UNIQUE, ")
                .append(IConvertContract.Devise.COL_LIBELLE + " TEXT NON NULL ")
                .append(" );");
        return sb.toString();
    }

    public static String dropTableTauxChange() {
        final StringBuilder sb = new StringBuilder(300);
        return sb.append("DROP TABLE IF EXISTS " +
                SCHEMA + "." + IConvertContract.TauxChange.T_TAUX_CHANGE)
                .toString();
    }

    public static String dropTableDevise() {
        final StringBuilder sb = new StringBuilder(300);
        return sb.append("DROP TABLE IF EXISTS " +
                SCHEMA + "." + IConvertContract.Devise.T_DEVISE)
                .toString();
    }
}
