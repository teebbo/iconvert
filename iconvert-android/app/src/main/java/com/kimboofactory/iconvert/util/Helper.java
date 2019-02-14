package com.kimboofactory.iconvert.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.kimboofactory.iconvert.dto.CurrencyIHM;

import java.util.LinkedList;
import java.util.List;

public class Helper {

    public static final String BASE_URL = "https://openexchangerates.org";
    public static final String LATEST = "latest.json";
    public static final String CURRENCIES = "currencies.json";
    public static final String EXTRA_SELECTED_ITEM = "com.kimboofactory.iconvert.EXTRA_SELECTED_ITEMS";
    public static final String EMPTY_STRING = "";
    public static final long DELAY_MILLIS_2000 = 2000;
    public static final long DELAY_MILLIS_4000 = 4000;
    public static final long DELAY_MILLIS_5000 = 5000;
    public static final List<CurrencyIHM> CURRENCY_IHMS_EMPTY_LIST = new LinkedList<>();

    private Helper() {
    }

    public static <T> void copy(final List<T> src, final List<T> dst) {
        if (src.size() > 0) {
            src.forEach(e -> dst.add(dst.size(), e));
        }
    }

    public static void hideKeyboard(final Context context, final View view) {
        if ( view != null) {
            final InputMethodManager imm = (InputMethodManager)
                    context.getSystemService(Context.INPUT_METHOD_SERVICE);


            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
