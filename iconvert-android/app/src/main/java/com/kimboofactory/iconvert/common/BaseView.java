package com.kimboofactory.iconvert.common;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public interface BaseView {
    void attachUi(Object activity);

    void clear();

    /*default <T extends BaseView> void connect2EventBus(T subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    default <T extends BaseView> void disconnect2EventBus(T subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }*/
}
