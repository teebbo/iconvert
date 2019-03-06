package com.kimboofactory.iconvert.ui.favorite;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aleengo.peach.toolbox.adapter.ItemView;
import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.util.Helper;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;

/**
 * Created by CK_ALEENGO on 06/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class FavoriteItemView extends RelativeLayout implements ItemView<CurrencyIHM> {

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

    public FavoriteItemView(Context context) {
        super(context);
    }

    public FavoriteItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    public void bind(CurrencyIHM item) {
        codeTV.setText(item.getEntity().getCode());
        libelleTV.setText(item.getEntity().getLibelle());
        rateTV.setText(Helper.RATE + "\n" + item.getComputeRate());
        resultTV.setText(item.getAmount());
        deleteFavorite.setOnClickListener(v -> {});
    }
}
