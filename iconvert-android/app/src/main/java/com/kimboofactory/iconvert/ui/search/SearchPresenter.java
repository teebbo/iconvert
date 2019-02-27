package com.kimboofactory.iconvert.ui.search;

import com.aleengo.peach.toolbox.commons.model.Result;
import com.kimboofactory.iconvert.common.AbstractPresenter;
import com.kimboofactory.iconvert.domain.UseCase;
import com.kimboofactory.iconvert.domain.UseCaseHandler;
import com.kimboofactory.iconvert.domain.common.QueryValue;
import com.kimboofactory.iconvert.dto.CurrencyIHM;

import org.greenrobot.eventbus.EventBus;

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
    private UseCase<QueryValue> mGetCurrenciesUseCase;
    private UseCaseHandler mUseCaseHandler;

    public SearchPresenter(UseCaseHandler useCaseHandler, UseCase getCurrenciesUseCase) {
        mUseCaseHandler = useCaseHandler;
        this.mGetCurrenciesUseCase = getCurrenciesUseCase;
    }

    @Override
    public void itemSelectedCheckbox(final CurrencyIHM item) {

        if (item.getChecked()) {
            selectedItems.add(item);
        } else {
            selectedItems.remove(item);
        }

        final boolean show = selectedItems.size() > 0;
        ((SearchCurrencyView) getMvpView()).toggleSnackbar(show, selectedItems);
    }

    @Override
    public void itemSelectedRadioButton(CurrencyIHM item) {
        ((SearchCurrencyView) getMvpView()).showCurrency(item);
    }

    @Override
    public void filter(String query) {
        ((SearchCurrencyView) getMvpView()).filter(query);
    }

    @Override
    public void loadCurrencies() {
       mUseCaseHandler.setUseCase(null, mGetCurrenciesUseCase);
       mUseCaseHandler.execute((Result result) -> EventBus.getDefault().post(result));
    }
}
