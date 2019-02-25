package com.kimboofactory.iconvert.ui;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.adapter.DeviseAdapter;
import com.kimboofactory.iconvert.dao.devise.DeviseDao;
import com.kimboofactory.iconvert.dao.devise.DeviseDaoImpl;
import com.kimboofactory.iconvert.common.BaseFragment;
import com.kimboofactory.iconvert.loader.DeviseListLoader;
import com.kimboofactory.iconvert.persistence.model.CurrencyData;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;


/**
 * Created by khranyt on 24/10/15.
 */
public class DeviseFragment extends BaseFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "DeviseFragment";

    // The Loader's unique id. Loader ids are specific
    // to the Activity or Fragment in which they reside
    private static final int DEVISE_LOADER_ID = 100;

    private LoaderManager.LoaderCallbacks<Cursor> callbacks;

    // The adapter that binds our data to the listview
    private DeviseAdapter mAdapter;
    private DeviseDao deviseDao;

    @Override
    public String getClassName() {
        return TAG;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_devise;
    }

    @Override
    public void onInitialize() {

    }

    public DeviseFragment(){
        super();
    }

    public static DeviseFragment newInstance() {
        return newInstance("");
    }

    public static DeviseFragment newInstance(final String param) {
        final DeviseFragment fragment = new DeviseFragment();
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
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        deviseDao = new DeviseDaoImpl(getContext());
        ListView currenciesListView = view.findViewById(R.id.currenciesListView);

        /*
        * You do not have a Cursor object before the onLoadFinished() method of your useCaseCallback has been called.
        * In other words: The cursor is not ready when you create the adapter.
        * Thus you create the adapter using "null" for the cursor argument:
        */
        mAdapter = new DeviseAdapter(getContext(), new ArrayList<>());

        // bind adapter and listview; So that when the adapter changes, the listview will
        // automatically be updated.
        currenciesListView.setAdapter(mAdapter);

        currenciesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Cursor c = (Cursor) parent.getItemAtPosition(position);
                final CurrencyData devise = deviseDao.createEntity(c);

                Toast.makeText(getActivity(), devise.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Initialisation du callbacks
        callbacks = this;
        // To start loading data from a callbacks call either initLoader() or restartLoader().
        // The system automatically determines whether a callbacks with the same integer ID already
        // exists and will either create a new callbacks or reuse an existing callbacks.
        Log.d(TAG, "Calling getLoaderManager().initLoader");
        getLoaderManager().initLoader(DEVISE_LOADER_ID, null, callbacks);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        /*inflater.inflate(R.menu.currencies_menu, menu);

        // associate searchable config with the SearchCurrencyView
        SearchManager manager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchActMenu = menu.findItem(R.id.action_search);
        SearchCurrencyView searchView = (SearchCurrencyView) MenuItemCompat.getActionView(searchActMenu);
        searchView.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));*/
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // The onCreateLoader is responsible for building a callbacks
        Log.d(TAG, "onCreateLoader() is called");
        final DeviseListLoader loader = new DeviseListLoader(getActivity().getApplicationContext());
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // The onFinished useCaseCallback returns with our data results
        // Here you update the UI based on the results of your filter.
        Log.d(TAG, "onLoadFinished() is called");
        mAdapter.updateDataSet(new ArrayList<>());

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //onLoaderReset is responsible for wiping out the data.
        Log.d(TAG, "onLoaderReset() is called");
        mAdapter.updateDataSet(new ArrayList<>());
    }
}