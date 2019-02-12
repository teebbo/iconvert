package com.kimboofactory.iconvert.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.adapter.KFYListAdapter;
import com.kimboofactory.iconvert.dto.CurrencyIHM;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by CK_ALEENGO on 12/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class FavoriteCurrenciesAdapter
        extends KFYListAdapter<CurrencyIHM, FavoriteCurrenciesAdapter.Holder> {


    public FavoriteCurrenciesAdapter(Context context, List<CurrencyIHM> favorites) {
        super(context, favorites);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.favorite_item;
    }

    @Override
    public Holder onCreateViewHolder(View view) {
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    public static class Holder extends KFYListAdapter.ViewHolder {

        @BindView(R.id.iv_delete_logo)
        ImageView deleteIV;
        @BindView(R.id.iv_currency_logo)
        ImageView currencyLogoIV;
        @BindView(R.id.tv_code)
        TextView codeTV;
        @BindView(R.id.tv_libelle)
        TextView libelleTV;
        @BindView(R.id.tv_result)
        TextView resultTV;
        @BindView(R.id.tv_rate)
        TextView rateTV;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
