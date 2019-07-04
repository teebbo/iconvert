package com.aleengo.iconvert.ui.favorite;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleengo.iconvert.application.ConvertApp;
import com.aleengo.iconvert.ui.home.ActivityHome;
import com.aleengo.peach.toolbox.adapter.ItemView;
import com.aleengo.peach.toolbox.commons.common.Pair;
import com.aleengo.iconvert.R;
import com.aleengo.iconvert.application.Constant;
import com.aleengo.iconvert.dto.CurrencyIHM;

import java.lang.ref.WeakReference;

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

    private WeakReference<ActivityHome> activityWeakRef;

    public FavoriteItemView(ActivityHome activity) {
        super(activity);

        this.activityWeakRef = new WeakReference<>(activity);
        inflate(getContext(), R.layout.favorite_item, this);
        binder = ButterKnife.bind(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ConvertApp.getRefWatcher().watch(this, "FavoriteItemView");
    }

    @Override
    public void bind(Pair<CurrencyIHM, Integer> pair) {

        final CurrencyIHM item = pair.getFirst();
        final Integer position = pair.getSecond();

        final String rate = Constant.RATE + "\n" + item.getComputeRate();
        codeTV.setText(item.getEntity().getCode());
        libelleTV.setText(item.getEntity().getLibelle());
        rateTV.setText(rate);
        resultTV.setText(item.getAmount());
        deleteFavorite.setOnClickListener(v -> activityWeakRef.get().getPresenter().removeFavorite(position));
    }
}
