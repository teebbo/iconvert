package com.kimboofactory.iconvert.ui.home.views;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;

import com.aleengo.peach.toolbox.commons.common.NamedRunnable;
import com.kimboofactory.iconvert.common.Constant;
import com.kimboofactory.iconvert.domain.model.FavoriteEntity;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.ui.home.presentation.MvpHomeView;
import com.kimboofactory.iconvert.util.Helper;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by CK_ALEENGO on 28/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public final class HomeViewListener implements TextWatcher {

    @Getter @Setter
    private List<FavoriteEntity> favoritesToDelete;
    private WeakReference<MvpHomeView> viewWeakRef;
    private Handler mHandler;

    @Inject
    public HomeViewListener(MvpHomeView view) {
        this.viewWeakRef = new WeakReference<>(view);
        mHandler = new Handler();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s != null && s.length() > 0) {
            viewWeakRef.get().getCurrencySource().setAmount(s.toString());
        } else {
            viewWeakRef.get().getCurrencySource().setAmount(Constant.DEFAULT_AMOUNT);
        }
        final NamedRunnable task = new NamedRunnable("%s", "OnTextChanged") {
            @Override
            protected void execute() {
                final List<CurrencyIHM> oldItems = Helper.copy(viewWeakRef.get().getAdapter().getItems());
                viewWeakRef.get().getAdapter().clear();
                viewWeakRef.get().updateFavoritesList(oldItems);
            }
        };
        mHandler.postDelayed(task, Constant.DELAY_MILLIS_500);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
