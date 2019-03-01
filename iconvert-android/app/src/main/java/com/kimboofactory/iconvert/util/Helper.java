package com.kimboofactory.iconvert.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.kimboofactory.iconvert.domain.common.QueryValue;
import com.kimboofactory.iconvert.dto.CurrencyIHM;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;

public class Helper {

    public static final String BASE_URL = "https://openexchangerates.org";
    public static final String LATEST = "latest.json";
    public static final String CURRENCIES = "currencies.json";

    public static final String RATE = "Rate";
    public static final String EXTRA_SELECTED_ITEMS = "com.kimboofactory.iconvert.EXTRA_SELECTED_ITEMS";
    public static final String EXTRA_SELECTED_ITEM = "com.kimboofactory.iconvert.EXTRA_SELECTED_ITEM";

    public static final String EMPTY_STRING = "";

    public static final long DELAY_MILLIS_500 = 500;
    public static final long DELAY_MILLIS_2000 = 2000;
    public static final long DELAY_MILLIS_4000 = 4000;
    public static final long DELAY_MILLIS_5000 = 5000;

    public static final List<CurrencyIHM> CURRENCY_IHMS_EMPTY_LIST = new LinkedList<>();
    public static final QueryValue NO_QUERY = null;
    public static final QueryValue<String> DEFAULT_QUERY = new QueryValue<>("USD");

    public static final String FORMAT_COMPUTED_RATE = "%.3f";
    public static final String FORMAT_AMOUNT = "%.3f";
    public static final String DEFAULT_AMOUNT = "1";

    private Helper() {
    }

    public static <T> List<T> copy(final List<T> src) {
        final List<T> dst = new LinkedList<>();
        if (src.size() > 0) {
            dst.addAll(src);
        }
        return dst;
    }

    public static void hideKeyboard(final Context context, final View view) {
        if ( view != null) {
            final InputMethodManager imm = (InputMethodManager)
                    context.getSystemService(Context.INPUT_METHOD_SERVICE);


            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static float computeRate(float rateSrc, float rateDst) {
        return rateDst/rateSrc;
    }

    public static float computeAmount(float amount, float computedRate) {
        return (computedRate * amount);
    }
}
