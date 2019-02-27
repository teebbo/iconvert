package com.kimboofactory.iconvert.ui.main;

import android.content.Intent;

import com.aleengo.peach.toolbox.commons.model.Result;
import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.domain.model.CurrencyEntity;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.ui.search.SearchCurrencyActivity;
import com.kimboofactory.iconvert.util.Utils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by CK_ALEENGO on 11/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class MainView implements FavoriteContract.View {

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

    /*@Override
    public void chooseSourceCurrency() {
        final Intent intent = new Intent(this.activity, SearchCurrencyActivity.class);
        intent.putExtra(MainActivity.REQUEST_CODE, MainActivity.CHOOSE_CURRENCY_REQUEST_CODE);
        this.activity.startActivityForResult(intent, MainActivity.CHOOSE_CURRENCY_REQUEST_CODE);
    }*/

    @Override
    public void updateFavoritesList(List<CurrencyIHM> currencies) {
        //activity.getFavoritesAdapter().clear();
        final List<CurrencyIHM> newCurrencies = currencies.stream()
                .map(currencyIHM -> Utils.computeAmount(activity.getCurrencySource(), currencyIHM))
                .collect(Collectors.toList());
        activity.getFavoritesAdapter().updateItems(newCurrencies);
    }

    @Override
    public void updateSourceCurrency(CurrencyIHM item) {
        final String codeLibelle = activity.getResources().getString(R.string.label_code_libelle,
                item.getEntity().getCode(), item.getEntity().getLibelle());
        activity.getTextViewCodeSrc().setText(codeLibelle);

        item.setAmount("100");
        activity.setCurrencySource(item);
        activity.getAmountET().setText(activity.getCurrencySource().getAmount());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Result event) {
        if (event.getError() != null) {
            // show an error message
            return;
        }

        final CurrencyEntity entity = (CurrencyEntity) event.getValue();

        final CurrencyIHM ihm = new CurrencyIHM(entity);
        ihm.setComputeRate(entity.getValue());
        ihm.setAmount("100");

        final String codeLibelle = activity.getResources().getString(R.string.label_code_libelle,
                ihm.getEntity().getCode(), ihm.getEntity().getLibelle());

        activity.getTextViewCodeSrc().setText(codeLibelle);
        activity.setCurrencySource(ihm);
        activity.getAmountET().setText(activity.getCurrencySource().getAmount());
    }
}
