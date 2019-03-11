package com.kimboofactory.iconvert.ui.home.views;

import android.view.View;

import com.aleengo.peach.toolbox.adapter.ListViewAdapter;
import com.kimboofactory.iconvert.dto.CurrencyIHM;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by CK_ALEENGO on 12/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class FavoritesAdapter
        extends ListViewAdapter<CurrencyIHM, FavoriteItemView> {

    private MainActivity activity;
    @Inject
    public FavoritesAdapter(MainActivity activity, List<CurrencyIHM> favorites) {
        super(activity, favorites);
        this.activity = activity;
    }

    @Override
    public void updateItems(List<CurrencyIHM> newItems) {
        if (newItems != null) {
            getItems().addAll(newItems);
            notifyDataSetChanged();
        }
    }

    @Override
    protected View onNewViewItem() {
        return new FavoriteItemView(activity);
    }
}
