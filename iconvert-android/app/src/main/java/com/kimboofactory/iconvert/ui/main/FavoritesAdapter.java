package com.kimboofactory.iconvert.ui.main;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleengo.peach.toolbox.adapter.ListViewAdapter;
import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.util.Helper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by CK_ALEENGO on 12/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class FavoritesAdapter
        extends ListViewAdapter<CurrencyIHM, FavoritesAdapter.ViewHolder> {


    public FavoritesAdapter(Context context, List<CurrencyIHM> favorites) {
        super(context, favorites);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.favorite_item;
    }

    @Override
    protected ViewHolder onNewViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void updateItems(List<CurrencyIHM> newItems) {
        if (newItems != null) {
            getItems().addAll(newItems);
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CurrencyIHM item = (CurrencyIHM) getItem(position);

        holder.codeTV.setText(item.getEntity().getCode());
        holder.libelleTV.setText(item.getEntity().getLibelle());
        holder.rateTV.setText(Helper.RATE + "\n" + item.getComputeRate());
        holder.resultTV.setText(item.getAmount());
    }

    public static class ViewHolder extends ListViewAdapter.ViewHolder {

        /*@BindView(R.id.iv_delete_logo)
        ImageView deleteIV;*/
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
        @BindView(R.id.cb_favorite_selected)
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
