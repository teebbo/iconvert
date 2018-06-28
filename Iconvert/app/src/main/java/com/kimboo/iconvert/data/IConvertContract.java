package com.kimboo.iconvert.data;

/**
 * Created by khranyt on 16/10/15.
 */

public class IConvertContract {

    /**
     * Table contenant le taux de change à jour
     */
    public static class TauxChange {

        // table name
        public static final String T_TAUX_CHANGE = "taux_change";

        //
        public static final String COL_ID = "_id";

        // name of the currency
        public static final String COL_CODE = "code";

        // rate
        public static final String COL_TAUX_CHANGE = "taux_change";
    }

    /**
     * Liste des devises
     */
    public static class Devise {

        public Devise() {}

        // table name
        public static final String T_DEVISE = "devise";

        //
        public static final String COL_ID = "_id";

        // column code
        public static final String COL_CODE = "code";

        // column libelle
        public static final String COL_LIBELLE = "libelle";

    }
}
