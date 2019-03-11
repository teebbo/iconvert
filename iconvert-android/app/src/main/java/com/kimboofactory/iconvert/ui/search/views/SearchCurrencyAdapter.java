package com.kimboofactory.iconvert.ui.search.views;

import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import com.aleengo.peach.toolbox.adapter.ListViewAdapter;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.util.Helper;

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

    private SearchCurrencyActivity activity;
    private SearchCurrencyFilter mFilter;
    @Getter
    private List<CurrencyIHM> originalList;
    private Integer requestCode;
    private boolean isOriginalListEmpty = true;

    @Inject
    public SearchCurrencyAdapter(SearchCurrencyActivity activity,
                                 List<CurrencyIHM> currencies,
                                 Integer requestCode) {
      super(activity, currencies);
      this.activity = activity;
      this.originalList = new LinkedList<>();
      this.requestCode = requestCode;
    }

    @Override
    protected View onNewViewItem() {
        return new SearchItemView(activity);
    }

    /*@Override
        protected ViewHolder onNewViewHolder(View view) {
            return new ViewHolder(view);
        }


    */
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

    /*@Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CurrencyIHM currencyIHM = (CurrencyIHM) getItem(position);

        holder.codeTV.setText(currencyIHM.getEntity().getCode());
        holder.libelleTV.setText(currencyIHM.getEntity().getLibelle());

        holder.checkBox.setVisibility(requestCode.intValue() == MainActivity.SEARCH_CURRENCY_REQUEST_CODE ?
                View.VISIBLE : View.GONE);
        holder.radioButton.setVisibility(requestCode.intValue() == MainActivity.SEARCH_CURRENCY_REQUEST_CODE ?
                View.GONE : View.VISIBLE);
        holder.checkBox.setCheckboxChecked(currencyIHM.getCheckboxChecked());
        holder.radioButton.setCheckboxChecked(currencyIHM.getCheckboxChecked());
    }*/

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new SearchCurrencyFilter(this);
        }
        return mFilter;
    }

    /*public static class ViewHolder extends ListViewAdapter.ViewHolder {

        @BindView(R.id.code_devise_textview)
        TextView codeTV;
        @BindView(R.id.libelle_devise_textview)
        TextView libelleTV;
        @Getter @Setter
        @BindView(R.id.cb_choose_currency)
        CheckBox checkBox;
        @Getter @Setter
        @BindView(R.id.rb_choose_currency)
        RadioButton radioButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }*/

    public static class SearchCurrencyFilter extends Filter {

        private SearchCurrencyAdapter adapter;

        public SearchCurrencyFilter(SearchCurrencyAdapter adapter) {
            this.adapter = adapter;
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

                filteredList = adapter.getOriginalList().stream()
                        .filter(currencyIHM -> accept(currencyIHM, query))
                        .collect(Collectors.toList());
            } else {
                filteredList = Helper.copy(adapter.getOriginalList());
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
