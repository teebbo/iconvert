package com.kimboofactory.iconvert.common;

import com.kimboofactory.iconvert.domain.common.QueryValue;
import com.kimboofactory.iconvert.dto.CurrencyIHM;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by CK_ALEENGO on 07/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public final class Constant {

    public static final String BASE_URL = "https://openexchangerates.org";
    public static final String LATEST = "latest.json";
    public static final String CURRENCIES = "currencies.json";

    public static final String RATE = "Rate";
    public static final String EXTRA_SELECTED_ITEMS = "com.kimboofactory.iconvert.EXTRA_SELECTED_ITEMS";
    public static final String EXTRA_SELECTED_ITEM = "com.kimboofactory.iconvert.EXTRA_SELECTED_ITEM";

    public static final int SEARCH_CURRENCY_REQUEST_CODE = 100;
    public static final int CHOOSE_CURRENCY_REQUEST_CODE = 101;
    public static final String REQUEST_CODE = "com.kimboofactory.iconvert.REQUEST_CODE";

    public static final String EMPTY_STRING = "";

    public static final long DELAY_MILLIS_500 = 500;
    public static final long DELAY_MILLIS_2000 = 2000;
    public static final long DELAY_MILLIS_4000 = 4000;
    public static final long DELAY_MILLIS_5000 = 5000;

    public static final String USD_CODE = "USD";
    public static final String USD_LIBELLE = "United States Dollar";

    public static final List<CurrencyIHM> CURRENCY_IHMS_EMPTY_LIST = new LinkedList<>();
    public static final QueryValue NO_QUERY = null;
    public static final QueryValue<String> DEFAULT_QUERY = new QueryValue<>(USD_CODE);

    public static final String FORMAT_COMPUTED_RATE = "%.3f";
    public static final String FORMAT_AMOUNT = "%.3f";
    public static final String DEFAULT_AMOUNT = "1.0";
}
