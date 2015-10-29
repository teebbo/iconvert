package fr.enst.igr201.kanmogne.iconvert;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.enst.igr201.kanmogne.iconvert.data.CurrencyContract;

/**
 * Created by Propriétaire on 29/10/2015.
 */
public class CurrencyAdapter extends CursorAdapter {

    /**
     * Cache of the children views
     */
    static public class ViewHolder{
        public final TextView tvCurrency;
        public final TextView tvCurrencyFullname;

        public ViewHolder(View view){
            tvCurrency = (TextView) view.findViewById(R.id.currency_text_view);
            tvCurrencyFullname = (TextView) view.findViewById(R.id.currency_full_name_text_view);
        }
    }


    public CurrencyAdapter(Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
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
        View view = LayoutInflater.from(context).inflate(R.layout.currency_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
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

        int nameIndex = cursor.getColumnIndex(CurrencyContract.CurrencyFullName.COLUMN_NAME);
        int fullnameIndex = cursor.getColumnIndex(CurrencyContract.CurrencyFullName.COLUMN_FULL_NAME);

        // extract data from the cursor
        String name = cursor.getString(nameIndex);
        String fullname = cursor.getString(fullnameIndex);

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        viewHolder.tvCurrency.setText(name);
        viewHolder.tvCurrencyFullname.setText(fullname);
    }
}
