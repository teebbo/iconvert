package com.aleengo.iconvert.ui.search;

import com.aleengo.iconvert.R;
import com.aleengo.iconvert.domain.Repository;
import com.aleengo.iconvert.domain.model.CurrencyEntity;
import com.aleengo.iconvert.dto.CurrencyIHM;
import com.aleengo.iconvert.persistence.repository.CurrencyRepository;
import com.aleengo.iconvert.ui.base.Presenter;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;

/**
 * Created by CK_ALEENGO on 13/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class SearchPresenter extends Presenter<SearchTemplate> implements SearchContract.Presenter {

    private Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();

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
        final Disposable d = repository.getCurrencies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnSuccess(), new OnError());
        disposables.add(d);

    /*   repository.getCurrencies((Response response) -> {

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

       });*/
    }

    // free resources
    @Override
    public void clear() {
        super.clear();
        selectedItems = null;
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    private class OnSuccess implements Consumer<List<CurrencyEntity>> {
        @Override
        public void accept(List<CurrencyEntity> currencyEntities) {
            final List<CurrencyIHM> _data = currencyEntities.stream()
                    .map(entity -> {
                        final CurrencyIHM item = new CurrencyIHM(entity);
                        item.setCode(getTemplate().getRequestCode());
                        return item;
                    })
                    .collect(Collectors.toList());
            getTemplate().updateAdapter(_data);
        }
    }

    private class OnError implements Consumer<Throwable> {
        @Override
        public void accept(Throwable throwable) throws Exception {
            System.err.println("Error happened: " + throwable.getMessage());
            getTemplate().showMessage(R.string.error_message_load_currencies);
        }
    }
}
