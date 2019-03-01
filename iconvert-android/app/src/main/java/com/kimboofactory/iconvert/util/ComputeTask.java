package com.kimboofactory.iconvert.util;

import android.os.AsyncTask;

import com.kimboofactory.iconvert.dto.CurrencyIHM;

import java.text.DecimalFormat;
import java.util.Locale;

import lombok.Getter;

/**
 * Created by CK_ALEENGO on 27/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class ComputeTask extends AsyncTask<Void, Void, CurrencyIHM> {

    private static final DecimalFormat FORMAT = new DecimalFormat("0.0000000");

    private CurrencyIHM src;
    @Getter
    private CurrencyIHM realCurrency;

    public ComputeTask(CurrencyIHM src, CurrencyIHM dst) {
        this.src = src;
        this.realCurrency = dst;
    }

    @Override
    protected CurrencyIHM doInBackground(Void... voids) {
        final float computedRate = Helper.computeRate(Float.valueOf(src.getEntity().getValue()),
                Float.valueOf(realCurrency.getEntity().getValue()));

        final float amount = Helper.computeAmount(Float.valueOf(src.getAmount()), computedRate);

        realCurrency.setComputeRate(String.format(Locale.US, Helper.FORMAT_COMPUTED_RATE, computedRate));
        realCurrency.setAmount(String.format(Locale.US, Helper.FORMAT_AMOUNT, amount));
        return realCurrency;
    }
}
