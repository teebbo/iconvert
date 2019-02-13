package com.kimboofactory.iconvert.ui.search;

import com.kimboofactory.iconvert.common.BasePresenter;
import com.kimboofactory.iconvert.common.BaseView;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public interface SearchContract {

    interface Presenter extends BasePresenter {
        void itemSelected();
    }

    interface View extends BaseView {
        void notification(boolean show);
    }
}
