package com.kimboofactory.iconvert.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.adapter.KFYListAdapter;
import com.kimboofactory.iconvert.data.IConvertContract;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.model.DeviseData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by CK_ALEENGO on 12/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class SelectCurrencyAdapter extends KFYListAdapter<CurrencyIHM, SelectCurrencyAdapter.Holder> {

    public SelectCurrencyAdapter(Context mContext, List<CurrencyIHM> currencies) {
      super(mContext, currencies);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_devise;
    }

    @Override
    public Holder onCreateViewHolder(View view) {
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final CurrencyIHM currencyIHM = (CurrencyIHM) getItem(position);

        holder.codeTV.setText(currencyIHM.getCode());
        holder.libelleTV.setText(currencyIHM.getLibelle());
        holder.checkBox.setChecked(currencyIHM.getChecked());
    }


    public class Holder extends KFYListAdapter.ViewHolder {

        @BindView(R.id.code_devise_textview)
        TextView codeTV;
        @BindView(R.id.libelle_devise_textview)
        TextView libelleTV;
        @BindView(R.id.cb_choose_currency)
        CheckBox checkBox;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
