package com.kimboofactory.iconvert.util;

import java.util.List;

public class Helper {

    public static final String BASE_URL = "https://openexchangerates.org";
    public static final String LATEST = "latest.json";
    public static final String CURRENCIES = "currencies.json";

    private Helper() {
    }

    public static <T> void copy(final List<T> src, final List<T> dst) {
        if (src.size() > 0) {
            src.forEach(e -> dst.add(dst.size(), e));
        }
    }
}
