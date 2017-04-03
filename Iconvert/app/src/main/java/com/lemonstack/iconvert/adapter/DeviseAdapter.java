package com.lemonstack.iconvert.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lemonstack.iconvert.R;
import com.lemonstack.iconvert.dao.devise.DeviseDao;
import com.lemonstack.iconvert.dao.devise.DeviseDaoImpl;
import com.lemonstack.iconvert.model.Devise;

/**
 * Created by khranyt on 29/10/2015.
 */
public class DeviseAdapter extends CursorAdapter {

    /**
     * Cache of the children views
     */

    private LayoutInflater inflater;
    private DeviseDao deviseDao;

    /**
     * ViewHolder Class
     */
    public static class ViewHolder{
        private final TextView codeDeviseTextView;
        private final TextView libelleDeviseTextView;

        public ViewHolder(View view){
            codeDeviseTextView = (TextView) view.findViewById(R.id.code_devise_textview);
            libelleDeviseTextView = (TextView) view.findViewById(R.id.libelle_devise_textview);
        }
    }

    public DeviseAdapter(Context context) {
        this(context, null);
    }

    public DeviseAdapter(Context context, Cursor cursor){
        this(context, cursor, 0);
    }

    public DeviseAdapter(Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
        inflater = LayoutInflater.from(context);
        deviseDao = new DeviseDaoImpl(context);
    }

    /**
     * Method used to inflate a new view and return it
     * @param context
     * @param cursor
     * @param parent
     * @return
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View view = inflater.inflate(R.layout.item_devise, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    /**
     * Method used to bind all data to given view by setting te text on a textview
     * @param view
     * @param context
     * @param cursor
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // extract data from the cursor
        final Devise devise = deviseDao.createEntity(cursor);
        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.codeDeviseTextView.setText(devise.getCode());
        viewHolder.libelleDeviseTextView.setText(devise.getLibelle());
    }
}
