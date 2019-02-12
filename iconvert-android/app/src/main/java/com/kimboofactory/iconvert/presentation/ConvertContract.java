package com.kimboofactory.iconvert.presentation;

import com.kimboofactory.iconvert.common.BasePresenter;
import com.kimboofactory.iconvert.common.BaseView;
import com.kimboofactory.iconvert.model.RateData;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public interface ConvertContract {

    interface Presenter extends BasePresenter {
        void loadRate();
        void getCurrencies();
    }

    interface View extends BaseView<Presenter> {

    }
}
