package com.kimboofactory.iconvert.ui.main;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleengo.peach.toolbox.adapter.ListViewAdapter;
import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.dto.CurrencyIHM;

import java.util.List;
import java.util.stream.Collectors;

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

   /* @Override
    public Object onNewViewHolder(View view) {
       ;
    }*/

    @Override
    protected ViewHolder onNewViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void updateItems(List<CurrencyIHM> newItems) {
        if (newItems != null && newItems.size() > 0) {
            getItems().addAll(newItems);
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CurrencyIHM item = (CurrencyIHM) getItem(position);

        holder.codeTV.setText(item.getCode());
        holder.libelleTV.setText(item.getLibelle());
        /*holder.rateTV.setText(item.getRate());
        holder.resultTV.setText(item.getResult());*/
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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
