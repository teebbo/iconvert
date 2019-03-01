package com.kimboofactory.iconvert.ui.main;

import android.content.Intent;

import com.aleengo.peach.toolbox.commons.model.Result;
import com.kimboofactory.iconvert.GetFavoriteEvent;
import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.domain.model.CurrencyEntity;
import com.kimboofactory.iconvert.domain.model.FavoriteEntity;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.util.ComputeTask;
import com.kimboofactory.iconvert.ui.search.SearchCurrencyActivity;
import com.kimboofactory.iconvert.util.Helper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import lombok.Getter;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class MainView implements FavoriteContract.View {

    @Getter
    private MainActivity activity;

    public MainView() {
    }

    @Override
    public void attachUi(Object activity) {
        this.activity = (MainActivity) activity;
    }

    @Override
    public void showSearchActivity(int requestCode) {
        final Intent intent = new Intent(this.activity, SearchCurrencyActivity.class);
        intent.putExtra(MainActivity.REQUEST_CODE, requestCode);
        this.activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void updateFavoritesList(List<CurrencyIHM> currencies) {
        final List<CurrencyIHM> newCurrencies = currencies.stream()
                .map(dst -> {
                    final ComputeTask task = new ComputeTask(activity.getCurrencySource(), dst);
                    task.execute();
                    return task.getRealCurrency();
                })
                .collect(Collectors.toList());

        activity.getFavoritesAdapter().updateItems(newCurrencies);
    }

    @Override
    public void updateSourceCurrency(CurrencyIHM item) {
        final String codeLibelle = activity.getResources().getString(R.string.label_code_libelle,
                item.getEntity().getCode(), item.getEntity().getLibelle());
        activity.getTextViewCodeSrc().setText(codeLibelle);

        activity.setCurrencySource(item);

        // update the favorite according to the new currency source
        final List<CurrencyIHM> oldItems = Helper.copy(activity.getFavoritesAdapter().getItems());
        activity.getFavoritesAdapter().clear();
        updateFavoritesList(oldItems);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Result event) {
        if (event.getError() != null) {
            // show an error message
            return;
        }

        final CurrencyEntity entity = (CurrencyEntity) event.getValue();
        final CurrencyIHM ihm = new CurrencyIHM(entity);
        ihm.setAmount("1");

        final String codeLibelle = activity.getResources().getString(R.string.label_code_libelle,
                ihm.getEntity().getCode(), ihm.getEntity().getLibelle());

        activity.getTextViewCodeSrc().setText(codeLibelle);
        activity.setCurrencySource(ihm);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GetFavoriteEvent event) {

        if (event.getSource() != null && event.getCurrencies().size() > 0) {
            // update source
            final CurrencyIHM ihm = event.getSource();
            ihm.setAmount(Helper.DEFAULT_AMOUNT);

            final String codeLibelle = activity.getResources().getString(R.string.label_code_libelle,
                    ihm.getEntity().getCode(), ihm.getEntity().getLibelle());

            activity.getTextViewCodeSrc().setText(codeLibelle);
            activity.setCurrencySource(ihm);
            activity.getAmountET().setHint(Helper.DEFAULT_AMOUNT);

            // update favorite list
            activity.getFavoritesAdapter().clear();
            final List<CurrencyIHM> list = event.getCurrencies().stream()
                    .map(currencyIHM -> {
                        final ComputeTask task = new ComputeTask(ihm, currencyIHM);
                        task.execute();
                        return task.getRealCurrency();
                    }).collect(Collectors.toList());
            activity.getFavoritesAdapter().updateItems(list);
        }
    }
}
