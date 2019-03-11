package com.kimboofactory.iconvert.ui.home.views;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
public final class HomeViewListener implements
        ListView.OnItemClickListener, ListView.OnItemLongClickListener, TextWatcher {

    @Getter @Setter
    private List<FavoriteEntity> favoritesToDelete;
    private WeakReference<MvpHomeView> viewRef;

    @Inject
    public HomeViewListener(MvpHomeView view) {
        this.viewRef = new WeakReference<>(view);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //viewRef.get().getPresenter().removeFavorite(position);

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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s != null && s.length() > 0) {
            viewRef.get().getCurrencySource().setAmount(s.toString());
            final NamedRunnable task = new NamedRunnable("%s", "OnTextChanged") {
                @Override
                protected void execute() {
                    final List<CurrencyIHM> oldItems = Helper.copy(viewRef.get().getAdapter().getItems());
                    viewRef.get().getAdapter().clear();
                    viewRef.get().updateFavoritesList(oldItems);
                }
            };
            new Handler().postDelayed(task, Constant.DELAY_MILLIS_500);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
