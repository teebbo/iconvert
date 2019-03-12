package com.kimboofactory.iconvert.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.LinkedList;
import java.util.List;

public class Helper {

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

    public static String unformatNumber(final String number) {
        String[] vals = number.trim().split("");
        final StringBuilder sb = new StringBuilder();
        for(String v : vals) {
            if(v.trim().length() > 0) {
                sb.append(v);
            }
        }
        return sb.toString();
    }

    public static String formatNumber(final String number) {

        final StringBuilder sb = new StringBuilder();
        final String[] values = number.split("\\.");

        String[] vals = values[0].trim().split("");
        for (int index = vals.length - 1; index >= 0; index -= 3) {
                for (int j = 0; j < 3; j++) {
                    if (j <= index) {
                        sb.append(vals[index - j]);
                    }
                }
            sb.append(" ");
        }

        return sb.reverse()
                .append(".") // append the part after the dot
                .append(values[1])
                .toString()
                .trim();
    }
}
