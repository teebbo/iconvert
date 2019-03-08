package com.kimboofactory.iconvert.common;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public interface BaseView {
    void attachUi(Object activity);

    void clear();

    default void connect2EventBus() {
        EventBus.getDefault().register(this);
    }

    default void disconnect2EventBus() {
        EventBus.getDefault().unregister(this);
    }
}
