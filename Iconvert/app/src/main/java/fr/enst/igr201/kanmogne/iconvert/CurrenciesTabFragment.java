package fr.enst.igr201.kanmogne.iconvert;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.enst.igr201.kanmogne.iconvert.R;

/**
 * Created by joffrey KanMoney on 24/10/15.
 */
public class CurrenciesTabFragment extends Fragment {

    String TAG = CurrenciesTabFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_currencies_tab, container, false);
        return rootview;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "Inside onActivityCreated");
    }
}