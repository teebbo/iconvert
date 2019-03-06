package com.kimboofactory.iconvert.ui.main;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kimboofactory.iconvert.domain.model.FavoriteEntity;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by CK_ALEENGO on 28/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public final class ListViewListener implements
        ListView.OnItemClickListener, ListView.OnItemLongClickListener {

    @Getter @Setter
    private List<FavoriteEntity> favoritesToDelete;
    private WeakReference<MainActivity> activityRef;

    @Inject
    public ListViewListener(WeakReference<MainActivity> activityRef) {
        this.activityRef = activityRef;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        activityRef.get().getPresenter().removeFavorite(position);

        /*final FavoritesAdapter.ViewHolder viewHolder =
                (FavoritesAdapter.ViewHolder) view.getTag();


        final CheckBox checkBox = viewHolder.checkBox;
        checkBox.setCheckboxChecked(!checkBox.isChecked());
        checkBox.setVisibility(View.VISIBLE);

        // add the favorite to list
        final Integer pos = (Integer) checkBox.getTag();
        final CurrencyIHM ihm = (CurrencyIHM) adapter.getItem(position);

        final FavoriteEntity favorite = new FavoriteEntity(null, ihm.getEntity(),
                ihm.getComputeRate(), ihm.getAmount());

        checkBox.setOnClickListener(v -> {
            v.setVisibility(View.GONE);
            favoritesToDelete.remove(favorite);
        });

        if (checkBox.isChecked()) {
            favoritesToDelete.add(favorite);
        }*/

        return true;
    }
}
