package com.kimboofactory.iconvert.ui.home.views;

import com.aleengo.peach.toolbox.adapter.ListViewAdapter;
import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.dto.CurrencyIHM;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by CK_ALEENGO on 12/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class FavoritesAdapter
        extends ListViewAdapter<CurrencyIHM, FavoriteItemView> {

    @Inject
    public FavoritesAdapter(MainActivity context, List<CurrencyIHM> favorites) {
        super(context, favorites);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.favorite_item;
    }

    @Override
    public void updateItems(List<CurrencyIHM> newItems) {
        if (newItems != null) {
            getItems().addAll(newItems);
            notifyDataSetChanged();
        }
    }
}
