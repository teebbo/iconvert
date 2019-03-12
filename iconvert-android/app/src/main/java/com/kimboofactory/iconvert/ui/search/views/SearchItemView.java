package com.kimboofactory.iconvert.ui.search.views;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.aleengo.peach.toolbox.adapter.ItemView;
import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.common.Constant;
import com.kimboofactory.iconvert.dto.CurrencyIHM;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by CK_ALEENGO on 06/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class SearchItemView extends FrameLayout implements ItemView<CurrencyIHM> {

    @BindView(R.id.code_devise_textview)
    @Getter TextView codeTV;
    @BindView(R.id.libelle_devise_textview)
    @Getter TextView libelleTV;
    @BindView(R.id.cb_choose_currency)
    @Getter @Setter CheckBox checkBox;
    @BindView(R.id.rb_choose_currency)
    @Getter @Setter RadioButton radioButton;

    Unbinder unbinder;

    public SearchItemView(Context context) {
        super(context);
        inflate(context, R.layout.activity_search_item, this);
        unbinder = ButterKnife.bind(this);
    }

   /* @Override
    protected void onDetachedFromWindow() {
        unbinder.unbind();
        super.onDetachedFromWindow();
    }*/

    @Override
    public void bind(CurrencyIHM item, int position) {

        codeTV.setText(item.getEntity().getCode());
        libelleTV.setText(item.getEntity().getLibelle());

        checkBox.setVisibility(item.getCode() == Constant.SEARCH_CURRENCY_REQUEST_CODE ? View.VISIBLE : View.GONE);
        radioButton.setVisibility(item.getCode() == Constant.CHOOSE_CURRENCY_REQUEST_CODE ? View.VISIBLE : View.GONE);
        checkBox.setChecked(item.getCheckboxChecked());
        radioButton.setChecked(item.getRadioButtonChecked());
    }
}
