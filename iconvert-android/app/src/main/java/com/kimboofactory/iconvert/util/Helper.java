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
}
