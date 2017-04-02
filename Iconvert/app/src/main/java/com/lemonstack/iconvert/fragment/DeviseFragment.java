package com.lemonstack.iconvert.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
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

import com.lemonstack.iconvert.R;
import com.lemonstack.iconvert.activity.MainActivity;
import com.lemonstack.iconvert.loader.DeviseLoader;
import com.lemonstack.iconvert.model.Devise;
import com.lemonstack.iconvert.adapter.DeviseAdapter;

/**
 * Created by khranyt on 24/10/15.
 */
public class DeviseFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = DeviseFragment.class.getName();

    // The Loader's unique id. Loader ids are specific
    // to the Activity or Fragment in which they reside
    private static final int DEVISE_LOADER_ID = 100;

    private LoaderManager.LoaderCallbacks<Cursor> deviseLoader;

    // The adapter that binds our data to the listview
    private DeviseAdapter mAdapter;


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
        Log.d(TAG, "onCreate() method");
        if(getArguments() != null) {
            final String param = getArguments().getString("PARAM_1");
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_devise, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "Inside onViewCreated");

        ListView currenciesListView = (ListView) getActivity().findViewById(R.id.currenciesListView);

        /*
        * You do not have a Cursor object before the onLoadFinished() method of your callback has been called.
        * In other words: The cursor is not ready when you create the adapter.
        * Thus you create the adapter using "null" for the cursor argument:
        */
        mAdapter = new DeviseAdapter(getContext(), null);

        // bind adapter and listview; So that when the adapter changes, the listview will
        // automatically be updated.
        currenciesListView.setAdapter(mAdapter);

        currenciesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String currencyName = ((TextView) view.findViewById(R.id.code_devise_textview)).getText().toString();
                String currencyFullName = ((TextView) view.findViewById(R.id.libelle_devise_textview)).getText().toString();

                Toast.makeText(getActivity(), (new Devise(currencyName, currencyFullName)).toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart() is called");
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume() is called");

        // Initialisation du deviseLoader
        deviseLoader = this;

        // To start loading data from a deviseLoader call either initLoader() or restartLoader().
        // The system automatically determines whether a deviseLoader with the same integer ID already
        // exists and will either create a new deviseLoader or reuse an existing deviseLoader.
        Log.d(TAG, "Calling getLoaderManager().initLoader");
        getLoaderManager().initLoader(DEVISE_LOADER_ID, null, deviseLoader);
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
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // The onCreateLoader is responsible for building a deviseLoader
        Log.d(TAG, "onCreateLoader() is called");
        final DeviseLoader loader = new DeviseLoader(getActivity().getApplicationContext());
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // The onFinished callback returns with our data results
        // Here you update the UI based on the results of your query.
        Log.d(TAG, "onLoadFinished() is called");
        mAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //onLoaderReset is responsible for wiping out the data.
        Log.d(TAG, "onLoaderReset() is called");
        mAdapter.swapCursor(null);
    }
}