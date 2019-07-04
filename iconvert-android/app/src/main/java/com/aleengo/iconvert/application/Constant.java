package com.aleengo.iconvert.application;

import com.aleengo.iconvert.domain.common.QueryValue;

/**
 * Created by CK_ALEENGO on 07/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public final class Constant {

    public static final String BASE_URL = "https://openexchangerates.org/api/";
    public static final String LATEST = "latest.json";
    public static final String CURRENCIES = "currencies.json";

    public static final String RATE = "Rate";
    public static final String EXTRA_SELECTED_ITEMS = "com.aleengo.iconvert.EXTRA_SELECTED_ITEMS";
    public static final String EXTRA_SELECTED_ITEM = "com.aleengo.iconvert.EXTRA_SELECTED_ITEM";

    public static final int SEARCH_CURRENCY_REQUEST_CODE = 100;
    public static final int CHOOSE_CURRENCY_REQUEST_CODE = 101;
    public static final String RC_SEARCH = "com.aleengo.iconvert.RC_SEARCH";

    public static final String EMPTY_STRING = "";

    public static final long DELAY_MILLIS_250 = 250;
    public static final long DELAY_MILLIS_500 = 500;
    public static final long DELAY_MILLIS_2000 = 2000;
    public static final long DELAY_MILLIS_5000 = 5000;

    public static final String DEFAULT_RATE_CODE = "USD";
    public static final String DEFAULT_RATE_LIBELLE = "United States Dollar";
    public static final String DEFAULT_AMOUNT = "1.0";

    public static final QueryValue NO_QUERY = null;
    public static final QueryValue<String> DEFAULT_QUERY = new QueryValue<>(DEFAULT_RATE_CODE);

    public static final String FORMAT_COMPUTED_RATE = "%.3f";
    public static final String FORMAT_AMOUNT = "%.3f";

    public static final int NO_EXTRA = -1;
}
