package com.aleengo.iconvert.ui.search;

import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import com.aleengo.peach.toolbox.adapter.ListViewAdapter;
import com.aleengo.iconvert.dto.CurrencyIHM;
import com.aleengo.iconvert.util.Helper;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import lombok.Getter;


/**
 * Created by CK_ALEENGO on 12/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class SearchCurrencyAdapter extends ListViewAdapter<CurrencyIHM, SearchItemView>
        implements Filterable {

    private WeakReference<ActivitySearchCurrency> activityWeakRef;
    private SearchCurrencyFilter mFilter;
    @Getter
    private List<CurrencyIHM> originalList;
    private Integer requestCode;
    private boolean isOriginalListEmpty = true;

    @Inject
    public SearchCurrencyAdapter(ActivitySearchCurrency activity,
                                 List<CurrencyIHM> currencies,
                                 Integer requestCode) {
      super(activity, currencies);
      this.activityWeakRef = new WeakReference<>(activity);
      this.originalList = new LinkedList<>();
      this.requestCode = requestCode;
    }

    @Override
    protected View onNewItemView() {
        return new SearchItemView(activityWeakRef.get());
    }

    @Override
    public void updateItems(List<CurrencyIHM> newItems) {
        if (newItems != null && newItems.size() > 0) {

            if (isOriginalListEmpty) {
                originalList.addAll(newItems);
                isOriginalListEmpty = false;
            }

            getItems().addAll(newItems);
            notifyDataSetChanged();
        }
    }


    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new SearchCurrencyFilter(this);
        }
        return mFilter;
    }

    public static class SearchCurrencyFilter extends Filter {

        private WeakReference<SearchCurrencyAdapter> adapterWeakRef;

        public SearchCurrencyFilter(SearchCurrencyAdapter adapter) {
            this.adapterWeakRef = new WeakReference<>(adapter);
        }

        /* filter on both code and libelle */
        private boolean accept(CurrencyIHM currencyIHM, final String query) {
            return currencyIHM.getEntity().getCode().toLowerCase().trim().contains(query) ||
                    currencyIHM.getEntity().getLibelle().toLowerCase().trim().contains(query);
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            // this method is called on the background thread

            List<CurrencyIHM> filteredList;

            if (constraint != null && constraint.length() > 0) {
                final String query = constraint.toString().toLowerCase().trim();

                filteredList = adapterWeakRef.get().getOriginalList().stream()
                        .filter(currencyIHM -> accept(currencyIHM, query))
                        .collect(Collectors.toList());
            } else {
                filteredList = Helper.copy(adapterWeakRef.get().getOriginalList());
            }

            final FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // Method called in the UI thread
            adapterWeakRef.get().clear();
            adapterWeakRef.get().updateItems((List<CurrencyIHM>) results.values);
        }
    }
}
