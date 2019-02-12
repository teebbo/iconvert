package com.kimboofactory.iconvert.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.model.DeviseData;

import java.util.List;

/**
 * Created by khranyt on 12/03/2017.
 */

public class DeviseDialogListAdapter extends CursorAdapter {

    private Context context;
    private List<DeviseData> devises;
    private LayoutInflater inflater;

    public static class ViewHolder{
        private TextView codeDeviseTextView;
        private TextView libelleDeviseTextView;

        public ViewHolder (View view) {
            codeDeviseTextView = (TextView) view.findViewById(R.id.code_devise_textview);
            libelleDeviseTextView = (TextView) view.findViewById(R.id.libelle_devise_textview);
        }
    }

    public DeviseDialogListAdapter(Context context) {
        this(context, null);
    }

    public DeviseDialogListAdapter(Context context, Cursor c) {
        this(context, c, 0);
    }

    public DeviseDialogListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View view = inflater.inflate(R.layout.item_devise, parent, false);
        final ViewHolder vh = new ViewHolder(view);
        view.setTag(vh);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final ViewHolder vh = (ViewHolder) view.getTag();

        final DeviseData devise = new DeviseData(cursor.getLong(0), cursor.getString(1), cursor.getString(2));

        vh.codeDeviseTextView.setText(devise.getCode());
        vh.libelleDeviseTextView.setText(devise.getLibelle());
    }
}
