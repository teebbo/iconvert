package com.kimboofactory.iconvert.util;

import android.os.AsyncTask;

import com.kimboofactory.iconvert.common.Constant;
import com.kimboofactory.iconvert.dto.CurrencyIHM;

import java.util.Locale;

import lombok.Getter;

/**
 * Created by CK_ALEENGO on 27/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class ComputeTask extends AsyncTask<Void, Void, CurrencyIHM> {

    private CurrencyIHM src;
    @Getter
    private CurrencyIHM finalCurrency;

    public ComputeTask(CurrencyIHM src, CurrencyIHM dst) {
        this.src = src;
        this.finalCurrency = dst;
    }

    @Override
    protected CurrencyIHM doInBackground(Void... voids) {
        final float computedRate = Helper.computeRate(Float.valueOf(src.getEntity().getValue()),
                Float.valueOf(finalCurrency.getEntity().getValue()));

        final float amount = Helper.computeAmount(Float.valueOf(src.getAmount()), computedRate);

        finalCurrency.setComputeRate(String.format(Locale.US, Constant.FORMAT_COMPUTED_RATE, computedRate));
        finalCurrency.setAmount(Helper.formatNumber(String.format(Locale.US, Constant.FORMAT_AMOUNT, amount)));
        return finalCurrency;
    }
}
