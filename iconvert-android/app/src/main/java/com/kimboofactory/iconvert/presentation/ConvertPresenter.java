package com.kimboofactory.iconvert.presentation;

import com.kimboofactory.iconvert.domain.usecases.GetCurrencies;
import com.kimboofactory.iconvert.domain.usecases.GetRate;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class ConvertPresenter implements ConvertContract.Presenter {

    private GetRate getRate;
    private GetCurrencies getCurrencies;

    public ConvertPresenter() {
    }


    @Override
    public void loadRate() {
        getRate = new GetRate();
    }

    @Override
    public void getCurrencies() {
        getCurrencies = new GetCurrencies();
    }
}
