package com.kimboofactory.iconvert.ui.search;

import com.kimboofactory.iconvert.common.AbstractPresenter;
import com.kimboofactory.iconvert.dto.CurrencyIHM;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class SearchPresenter extends AbstractPresenter
        implements SearchContract.Presenter {

    @Getter
    private List<CurrencyIHM> selectedItems = new LinkedList<>();

    public SearchPresenter() {
    }

    @Override
    public void itemSelected(final CurrencyIHM item) {

        if (item.getChecked()) {
            selectedItems.add(item);
        } else {
            selectedItems.remove(item);
        }

        final boolean show = selectedItems.size() > 0;
        ((SearchCurrencyView) getMvpView()).toggleSnackbar(show, selectedItems);
    }

    @Override
    public void filter(String query) {
        ((SearchCurrencyView) getMvpView()).filter(query);
    }
}
