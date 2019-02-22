package com.kimboofactory.iconvert.ui.search;

import com.aleengo.peach.toolbox.commons.factory.Singleton;
import com.aleengo.peach.toolbox.commons.model.Result;
import com.kimboofactory.iconvert.common.AbstractPresenter;
import com.kimboofactory.iconvert.domain.UseCase;
import com.kimboofactory.iconvert.domain.UseCaseHandler;
import com.kimboofactory.iconvert.domain.common.QueryValue;
import com.kimboofactory.iconvert.domain.usecases.GetCurrencies;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.persistence.local.LocalDataSource;
import com.kimboofactory.iconvert.persistence.remote.RemoteDataSource;
import com.kimboofactory.iconvert.persistence.repository.CurrencyRepository;

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
    private UseCase<QueryValue> getCurrenciesUseCase;

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

    @Override
    public void loadCurrencies() {
        getCurrenciesUseCase = new GetCurrencies();
        getCurrenciesUseCase
                .setRepository(new CurrencyRepository(Singleton.of(LocalDataSource.class),
                        Singleton.of(RemoteDataSource.class)));

        UseCaseHandler.getInstance().setUseCase(null, getCurrenciesUseCase);
        UseCaseHandler.getInstance().execute((Result result) -> EventBus.getDefault().post(result));
    }
}
