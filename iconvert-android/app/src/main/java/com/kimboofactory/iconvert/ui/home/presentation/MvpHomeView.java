package com.kimboofactory.iconvert.ui.home.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aleengo.peach.toolbox.commons.model.Result;
import com.aleengo.peach.toolbox.widget.PeachToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kimboofactory.iconvert.events.GetFavoriteEvent;
import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.common.Constant;
import com.kimboofactory.iconvert.di.Injector;
import com.kimboofactory.iconvert.domain.model.CurrencyEntity;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.ui.home.views.FavoritesAdapter;
import com.kimboofactory.iconvert.ui.home.views.HomeViewListener;
import com.kimboofactory.iconvert.ui.home.views.MainActivity;
import com.kimboofactory.iconvert.ui.search.views.SearchCurrencyActivity;
import com.kimboofactory.iconvert.util.ComputeTask;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import butterknife.BindView;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by CK_ALEENGO on 07/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class MvpHomeView extends FrameLayout implements FavoriteContract.View {

    @BindView(R.id.toolbar)
    PeachToolbar toolbar;
    @BindView(R.id.rl_currency)
    RelativeLayout currencyRL;
    @Getter @BindView(R.id.text_view_code)
    TextView textViewCodeSrc;
    @BindView(R.id.tv_update_date)
    TextView updateDateTV;
    @Getter @BindView(R.id.et_amount)
    EditText amountET;
    @BindView(R.id.lv_favorites)
    ListView favoritesLV;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Getter @Inject
    FavoritesAdapter adapter;
    @Inject
    HomeViewListener listener;
    @Getter @Setter
    private CurrencyIHM currencySource;

    private MainActivity activity;
    private boolean mFirstLoad = true;

    public MvpHomeView(MainActivity activity) {
        super(activity);
        this.activity = activity;
        inflate(getContext(), R.layout.activity_main, this);
    }

    public void init(@Nullable Bundle savedInstanceState) {

        if (savedInstanceState == null) {
        }

        Injector.instance().inject(this);

        favoritesLV.setAdapter(adapter);
        activity.setSupportActionBar(toolbar);
        favoritesLV.setOnItemClickListener(listener);
        favoritesLV.setOnItemLongClickListener(listener);

        fab.setOnClickListener((View v) -> activity.getPresenter().addFavorite(Constant.SEARCH_CURRENCY_REQUEST_CODE));
        amountET.addTextChangedListener(listener);

        currencyRL.setOnClickListener(v -> activity.getPresenter().addFavorite(Constant.CHOOSE_CURRENCY_REQUEST_CODE));
    }

    public void start() {
        if (mFirstLoad) {
            currencySource = getDefaultCurrency();
            final String codeLibelle = getResources().getString(R.string.label_code_libelle,
                    currencySource.getEntity().getCode(), currencySource.getEntity().getLibelle());
            textViewCodeSrc.setText(codeLibelle);
            mFirstLoad = false;
        }
    }

    public void stop() {
//        disconnect2EventBus(this);
    }

    @Override
    public void clear() {
        //binder.unbind();
    }

    @Override
    public void showSearchActivity(int requestCode) {
        final Intent intent = new Intent(this.activity, SearchCurrencyActivity.class);
        intent.putExtra(Constant.REQUEST_CODE, requestCode);
        this.activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void updateFavoritesList(List<CurrencyIHM> currencies) {
        if (currencies.size() > 0) {
            final List<CurrencyIHM> newCurrencies = currencies.stream()
                    .map(dst -> {
                        final ComputeTask task = new ComputeTask(getCurrencySource(), dst);
                        task.execute();
                        return task.getFinalCurrency();
                    })
                    .collect(Collectors.toList());
            // update the adapter
            adapter.clear();
            adapter.updateItems(newCurrencies);
        } else {
            adapter.clear();
            adapter.updateItems(currencies);
        }
    }

    @Override
    public void updateSourceCurrency(CurrencyIHM item) {
        final String codeLibelle = activity.getResources().getString(R.string.label_code_libelle,
                item.getEntity().getCode(), item.getEntity().getLibelle());

        item.setAmount(Constant.DEFAULT_AMOUNT);

        getTextViewCodeSrc().setText(codeLibelle);
        setCurrencySource(item);

        // update the favorite according to the new currency source
        updateFavoritesList(adapter.getItems());
    }


    @Override
    public CurrencyIHM getDefaultCurrency() {
        final CurrencyEntity entity =
                new CurrencyEntity(Constant.DEFAULT_RATE_CODE, Constant.DEFAULT_RATE_LIBELLE, Constant.DEFAULT_AMOUNT);

        final CurrencyIHM source  = new CurrencyIHM();
        source.setEntity(entity);
        source.setAmount(Constant.DEFAULT_AMOUNT);
        return source;
    }

    @Override
    public CurrencyIHM removeItem(int position) {
        return adapter.removeItem(position);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Result event) {
        if (event.getError() != null) {
            // show an error message
            return;
        }

        final CurrencyEntity entity = (CurrencyEntity) event.getValue();
        final CurrencyIHM ihm = new CurrencyIHM(entity);
        ihm.setAmount(Constant.DEFAULT_AMOUNT);

        final String codeLibelle = activity.getResources().getString(R.string.label_code_libelle,
                ihm.getEntity().getCode(), ihm.getEntity().getLibelle());

        getTextViewCodeSrc().setText(codeLibelle);
        setCurrencySource(ihm);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GetFavoriteEvent event) {

        if (event.getSource() != null && event.getCurrencies().size() > 0) {
            // update source
            final CurrencyIHM ihm = event.getSource();
            ihm.setAmount(Constant.DEFAULT_AMOUNT);

            final String codeLibelle = activity.getResources().getString(R.string.label_code_libelle,
                    ihm.getEntity().getCode(), ihm.getEntity().getLibelle());

            getTextViewCodeSrc().setText(codeLibelle);
            setCurrencySource(ihm);
            getAmountET().setHint(Constant.DEFAULT_AMOUNT);

            // update favorite list
            final List<CurrencyIHM> newItems = event.getCurrencies()
                    .stream()
                    .map(currencyIHM -> {
                        final ComputeTask task = new ComputeTask(ihm, currencyIHM);
                        task.execute();
                        return task.getFinalCurrency();
                    }).collect(Collectors.toList());

            adapter.clear();
            adapter.updateItems(newItems);
        }
    }
}
