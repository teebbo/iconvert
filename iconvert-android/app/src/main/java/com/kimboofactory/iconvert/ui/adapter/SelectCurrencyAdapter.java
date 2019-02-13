package com.kimboofactory.iconvert.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.adapter.ListViewAdapter;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.util.Helper;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;


/**
 * Created by CK_ALEENGO on 12/02/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public class SelectCurrencyAdapter extends ListViewAdapter<CurrencyIHM, SelectCurrencyAdapter.Holder>
        implements Filterable {

    private SelectCurrencyFilter mFilter;
    @Getter
    private final List<CurrencyIHM> originalList;

    public SelectCurrencyAdapter(Context mContext, List<CurrencyIHM> currencies) {
      super(mContext, currencies);
      this.originalList = new LinkedList<>();
      Helper.copy(currencies, originalList);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_devise;
    }

    @Override
    public Holder onCreateViewHolder(View view) {
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final CurrencyIHM currencyIHM = (CurrencyIHM) getItem(position);

        holder.codeTV.setText(currencyIHM.getCode());
        holder.libelleTV.setText(currencyIHM.getLibelle());
        holder.checkBox.setChecked(currencyIHM.getChecked());
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new SelectCurrencyFilter(this);
        }
        return mFilter;
    }

    public class Holder extends ListViewAdapter.ViewHolder {

        @BindView(R.id.code_devise_textview)
        TextView codeTV;
        @BindView(R.id.libelle_devise_textview)
        TextView libelleTV;
        @BindView(R.id.cb_choose_currency)
        CheckBox checkBox;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class SelectCurrencyFilter extends Filter {

        private SelectCurrencyAdapter adapter;

        public SelectCurrencyFilter(SelectCurrencyAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            // this method is called on the background thread

            List<CurrencyIHM> filteredList = new LinkedList<>();

            if (constraint != null && constraint.length() > 0) {
                final String query = constraint.toString().toLowerCase().trim();

                for (CurrencyIHM currencyIHM : adapter.getOriginalList()) {
                    if (currencyIHM.getCode().toLowerCase().trim().contains(query) ||
                            currencyIHM.getLibelle().toLowerCase().trim().contains(query)) {
                        filteredList.add(currencyIHM);
                    }
                }
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
            adapter.updateDataSet((List<CurrencyIHM>) results.values);
        }
    }
}
