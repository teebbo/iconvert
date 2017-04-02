package com.lemonstack.iconvert.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lemonstack.iconvert.R;
import com.lemonstack.iconvert.model.Devise;
import com.lemonstack.iconvert.model.TauxChange;

import java.util.List;

/**
 * Created by khranyt on 12/03/2017.
 */

public class DeviseDialogListAdapter extends BaseAdapter {

    private Context context;
    private List<Devise> devises;
    private LayoutInflater inflater;

    public static class ViewHolder{
        private TextView codeDeviseTextView;
        private TextView libelleDeviseTextView;

        public ViewHolder (View view) {
            codeDeviseTextView = (TextView) view.findViewById(R.id.code_devise_textview);
            libelleDeviseTextView = (TextView) view.findViewById(R.id.libelle_devise_textview);
        }
    }

    public DeviseDialogListAdapter(final Context context, final List<Devise> devises) {
        this.context = context;
        this.devises = devises;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return devises.size();
    }

    @Override
    public Object getItem(int position) {
        return devises.get(position);
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
            view = inflater.inflate(R.layout.item_devise, parent, false);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        final Devise devise = (Devise) getItem(position);
        vh.codeDeviseTextView.setText(devise.getCode());
        vh.libelleDeviseTextView.setText(devise.getLibelle());

        return view;
    }
}
