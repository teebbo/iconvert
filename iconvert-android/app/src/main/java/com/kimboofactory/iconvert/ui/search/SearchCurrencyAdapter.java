package com.kimboofactory.iconvert.ui.search;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.aleengo.peach.toolbox.adapter.ListViewAdapter;
import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.util.Helper;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by CK_ALEENGO on 12/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class SearchCurrencyAdapter extends ListViewAdapter<CurrencyIHM, SearchCurrencyAdapter.ViewHolder>
        implements Filterable {

    private SearchCurrencyFilter mFilter;
    @Getter
    private List<CurrencyIHM> originalList;
    private boolean isOriginalListEmpty = true;

    public SearchCurrencyAdapter(Context mContext, List<CurrencyIHM> currencies) {
      super(mContext, currencies);
      this.originalList = new LinkedList<>();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_search_item;
    }

    @Override
    protected ViewHolder onNewViewHolder(View view) {
        return new ViewHolder(view);
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CurrencyIHM currencyIHM = (CurrencyIHM) getItem(position);

        holder.codeTV.setText(currencyIHM.getCode());
        holder.libelleTV.setText(currencyIHM.getLibelle());
        holder.checkBox.setChecked(currencyIHM.getChecked());
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new SearchCurrencyFilter(this);
        }
        return mFilter;
    }

    public static class ViewHolder extends ListViewAdapter.ViewHolder {

        @BindView(R.id.code_devise_textview)
        TextView codeTV;
        @BindView(R.id.libelle_devise_textview)
        TextView libelleTV;
        @Getter @Setter
        @BindView(R.id.cb_choose_currency)
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class SearchCurrencyFilter extends Filter {

        private SearchCurrencyAdapter adapter;

        public SearchCurrencyFilter(SearchCurrencyAdapter adapter) {
            this.adapter = adapter;
        }

        private boolean accept(CurrencyIHM currencyIHM, final String query) {
            return currencyIHM.getCode().toLowerCase().trim().contains(query) ||
                    currencyIHM.getLibelle().toLowerCase().trim().contains(query);
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            // this method is called on the background thread

            List<CurrencyIHM> filteredList = new LinkedList<>();

            if (constraint != null && constraint.length() > 0) {
                final String query = constraint.toString().toLowerCase().trim();

                filteredList = adapter.getOriginalList().stream()
                        .filter(currencyIHM -> accept(currencyIHM, query))
                        .collect(Collectors.toList());
            } else {
                Helper.copy(adapter.getOriginalList(), filteredList);
            }

            final FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // Method called in the UI thread
            adapter.clear();
            adapter.updateItems((List<CurrencyIHM>) results.values);
        }
    }
}
