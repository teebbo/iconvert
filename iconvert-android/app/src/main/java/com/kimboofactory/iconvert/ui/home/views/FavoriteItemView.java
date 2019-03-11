package com.kimboofactory.iconvert.ui.home.views;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleengo.peach.toolbox.adapter.ItemView;
import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.common.Constant;
import com.kimboofactory.iconvert.dto.CurrencyIHM;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lombok.Getter;

/**
 * Created by CK_ALEENGO on 06/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class FavoriteItemView extends FrameLayout implements ItemView<CurrencyIHM> {

    @Getter @BindView(R.id.iv_currency_logo)
    ImageView currencyLogoIV;
    @Getter @BindView(R.id.tv_code)
    TextView codeTV;
    @Getter @BindView(R.id.tv_libelle)
    TextView libelleTV;
    @Getter @BindView(R.id.tv_result)
    TextView resultTV;
    @Getter @BindView(R.id.tv_rate)
    TextView rateTV;
    @Getter @BindView(R.id.iv_delete_favorite)
    ImageView deleteFavorite;

    private Unbinder binder;
    private MainActivity activity;

    public FavoriteItemView(MainActivity activity) {
        super(activity);

        this.activity = activity;
        inflate(getContext(), R.layout.favorite_item, this);
        binder = ButterKnife.bind(this);
    }

    /*@Override
    protected void onDetachedFromWindow() {
        binder.unbind();
        super.onDetachedFromWindow();
    }*/

    @Override
    public void bind(CurrencyIHM item) {

        final String rate = Constant.RATE + "\n" + item.getComputeRate();

        codeTV.setText(item.getEntity().getCode());
        libelleTV.setText(item.getEntity().getLibelle());
        rateTV.setText(rate);
        resultTV.setText(item.getAmount());
        //deleteFavorite.setOnClickListener(v -> activity.getPresenter().removeFavorite());
    }
}
