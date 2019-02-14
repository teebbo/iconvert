package com.kimboofactory.iconvert.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;


import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.common.ListViewAdapter;
import com.kimboofactory.iconvert.persistence.model.CurrencyData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by khranyt on 29/10/2015.
 */
public class DeviseAdapter extends ListViewAdapter<CurrencyData, DeviseAdapter.ViewHolder> {

    public DeviseAdapter(Context context, List<CurrencyData> currencies){
        super(context, currencies);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_search_item;
    }

    @Override
    public ViewHolder onCreateViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CurrencyData currency = (CurrencyData) getItem(position);

        holder.codeDeviseTextView.setText(currency.getCode());
        holder.libelleDeviseTextView.setText(currency.getLibelle());
    }

    /**
     * ViewHolder Class
     */
    public static class ViewHolder extends ListViewAdapter.ViewHolder {
        @BindView(R.id.code_devise_textview)
        TextView codeDeviseTextView;
        @BindView(R.id.libelle_devise_textview)
        TextView libelleDeviseTextView;

        public ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
