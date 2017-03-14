package com.lemonstack.khranyt.iconvert.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lemonstack.khranyt.iconvert.R;
import com.lemonstack.khranyt.iconvert.data.CurrencyContract;
import com.lemonstack.khranyt.iconvert.fragment.dialog.CurrenciesDialogFragment;
import com.lemonstack.khranyt.iconvert.model.Currency;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by khranyt on 12/03/2017.
 */

public class CurrenciesDialogListAdapter extends BaseAdapter {

    private Context context;
    private List<Currency> currenciesList;
    private LayoutInflater inflater;

    public static class ViewHolder{
        private TextView currencyTv;
        private TextView currencyFullName;

        public ViewHolder (View view) {
            currencyTv = (TextView) view.findViewById(R.id.currency_text_view);
            currencyFullName = (TextView) view.findViewById(R.id.currency_full_name_text_view);
        }
    }


    public CurrenciesDialogListAdapter(final Context context, final List<Currency> currenciesList) {
        this.context = context;
        this.currenciesList = currenciesList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return currenciesList.size();
    }

    @Override
    public Object getItem(int position) {
        return currenciesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder vh;
        if (view == null) {
            view = inflater.inflate(R.layout.currency_item, parent, false);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        final Currency currency = (Currency) getItem(position);
        vh.currencyTv.setText(currency.getName());
        vh.currencyFullName.setText(currency.getFullname());

        return view;
    }
}
