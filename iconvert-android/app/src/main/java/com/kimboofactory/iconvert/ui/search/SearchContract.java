package com.kimboofactory.iconvert.ui.search;

import com.kimboofactory.iconvert.common.BasePresenter;
import com.kimboofactory.iconvert.common.BaseView;
import com.kimboofactory.iconvert.dto.CurrencyIHM;

import java.util.List;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public interface SearchContract {

    interface Presenter extends BasePresenter {
        void itemSelected(CurrencyIHM item);
        void filter(String query);
    }

    interface View extends BaseView {
        void filter(String query);
        void toggleSnackbar(boolean show, List<CurrencyIHM> items);
    }
}
