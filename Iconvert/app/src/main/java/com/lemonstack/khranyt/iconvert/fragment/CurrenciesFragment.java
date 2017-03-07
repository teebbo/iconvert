package com.lemonstack.khranyt.iconvert.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lemonstack.khranyt.iconvert.R;
import com.lemonstack.khranyt.iconvert.model.Currency;
import com.lemonstack.khranyt.iconvert.adapter.CurrencyAdapter;
import com.lemonstack.khranyt.iconvert.data.CurrencyContract;

/**
 * Created by khranyt on 24/10/15.
 */
public class CurrenciesFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    String TAG = CurrenciesFragment.class.getSimpleName();

    // The Loader's unique id. Loader ids are specific
    // to the Activity or Fragment in which they reside
    static private final int CURRENCY_LOADER_ID = 100;

    private LoaderManager.LoaderCallbacks<Cursor> mLoader;

    // The adapter that binds our data to the listview
    private CurrencyAdapter mAdapter;


    public CurrenciesFragment(){
        super();
    }

    public static CurrenciesFragment newInstance() {
        return newInstance("");
    }

    public static CurrenciesFragment newInstance(final String param) {
        final CurrenciesFragment fragment = new CurrenciesFragment();
        Bundle args = new Bundle();
        args.putString("PARAM_1", param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() method");
        if(getArguments() != null) {
            final String param = getArguments().getString("PARAM_1");
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_currencies, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "Inside onViewCreated");

        ListView currenciesListView = (ListView) getActivity().findViewById(R.id.currenciesListView);
        mAdapter = new CurrencyAdapter(getContext() ,null, 0);

        // bind adapter and listview; So that when the adapter will changes, the listview will
        // automatically be updated.
        currenciesListView.setAdapter(mAdapter);

        mLoader = this;
        getLoaderManager().initLoader(CURRENCY_LOADER_ID, null, mLoader);

        currenciesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String currencyName = ((TextView) view.findViewById(R.id.currency_text_view)).getText().toString();
                String currencyFullName = ((TextView) view.findViewById(R.id.currency_full_name_text_view)).getText().toString();

                Toast.makeText(getActivity(), (new Currency(currencyName, currencyFullName)).toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "Inside onActivityCreated");

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        /*inflater.inflate(R.menu.currencies_menu, menu);

        // associate searchable config with the SearchView
        SearchManager manager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchActMenu = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchActMenu);
        searchView.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));*/
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {

        // Sort order:  Ascending.
        String sortOrder = CurrencyContract.CurrencyFullName.COLUMN_NAME + " ASC";

        return new CursorLoader(getActivity(),
                CurrencyContract.CurrencyFullName.CONTENT_URI,
                null,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        int loaderId = loader.getId();
        switch (loaderId){
            case CURRENCY_LOADER_ID:
                /**
                 * The aysnchronous load is completed and the data is now
                 * available for use. Now, we can associated the queried cursor
                 * with the adapter
                 */
                mAdapter.swapCursor(data);
                break;
            default:
                break;
        }
        // here the listview now display the queried data
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        /**
         * For whatever reason, the Loader's data is now unavailable
         * remove any references to the old data by replacing it with
         * a null cursor
         */
        mAdapter.swapCursor(null);
    }

}