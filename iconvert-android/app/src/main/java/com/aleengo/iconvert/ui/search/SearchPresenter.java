package com.aleengo.iconvert.ui.search;

import com.aleengo.peach.toolbox.commons.model.Response;
import com.aleengo.peach.toolbox.commons.model.Result;
import com.aleengo.iconvert.ui.base.Presenter;
import com.aleengo.iconvert.domain.Repository;
import com.aleengo.iconvert.domain.model.CurrencyEntity;
import com.aleengo.iconvert.dto.CurrencyIHM;
import com.aleengo.iconvert.events.CurrenciesEvent;
import com.aleengo.iconvert.persistence.repository.CurrencyRepository;

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
public class SearchPresenter extends Presenter<SearchTemplate> implements SearchContract.Presenter {

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
        getTemplate().toggleSnackbar(selectedItems.size() > 0, selectedItems);
    }

    @Override
    public void itemSelectedRadioButton(CurrencyIHM item) {
        getTemplate().showCurrency(item);
    }

    @Override
    public void filter(String query) {
        getTemplate().filter(query);
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
                       item.setCode(getTemplate().getRequestCode());
                       return item;
                   })
                   .collect(Collectors.toList());
           EventBus.getDefault().post(new CurrenciesEvent(list, null));

       });
    }

    // free resources
    @Override
    public void clear() {
        super.clear();
        selectedItems = null;
    }
}
