package com.kimboofactory.iconvert.ui.search.presentation;

import com.aleengo.peach.toolbox.commons.model.Response;
import com.aleengo.peach.toolbox.commons.model.Result;
import com.kimboofactory.iconvert.common.AbstractPresenter;
import com.kimboofactory.iconvert.domain.Repository;
import com.kimboofactory.iconvert.domain.model.CurrencyEntity;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.events.CurrenciesEvent;
import com.kimboofactory.iconvert.persistence.repository.CurrencyRepository;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import lombok.Getter;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class SearchPresenter extends AbstractPresenter<MvpSearchView>
        implements SearchContract.Presenter {

    private Repository repository;
    @Getter
    private List<CurrencyIHM> selectedItems = new LinkedList<>();

    @Inject
    public SearchPresenter(CurrencyRepository repository) {
        this.repository = repository;
    }

    @Override
    public void itemSelectedCheckbox(final CurrencyIHM item) {

        if (item.getCheckboxChecked()) {
            selectedItems.add(item);
        } else {
            selectedItems.remove(item);
        }
        getMvpView().toggleSnackbar(selectedItems.size() > 0, selectedItems);
    }

    @Override
    public void itemSelectedRadioButton(CurrencyIHM item) {
        getMvpView().showCurrency(item);
    }

    @Override
    public void filter(String query) {
        getMvpView().filter(query);
    }

    @Override
    public void loadCurrencies() {
       repository.getCurrencies((Response response) -> {

           final Result<List<CurrencyEntity>> result = new Result<>(null, null);

           if (response.getError() != null) {
               //result.setError(response.getError());
               EventBus.getDefault().post(new CurrenciesEvent(null, response.getError()));
               return;
           }


           final List<CurrencyEntity> data = (List<CurrencyEntity>) response.getValue();
           final List<CurrencyIHM> list =  data.stream()
                   .map(entity -> {
                       final CurrencyIHM item = new CurrencyIHM(entity);
                       item.setCode(getMvpView().getRequestCode());
                       return item;
                   })
                   .collect(Collectors.toList());
           EventBus.getDefault().post(new CurrenciesEvent(list, null));

       });
    }

    // free resources
    public void clear() {
        detach();
        selectedItems = null;
    }
}
