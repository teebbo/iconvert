package fr.enst.igr201.kanmogne.iconvert;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import fr.enst.igr201.kanmogne.iconvert.data.CurrencyContract;

/**
 * Created by joffrey KanMoney on 24/10/15.
 */
public class CurrenciesTabFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    String TAG = CurrenciesTabFragment.class.getSimpleName();

    // The Loader's unique id. Loader ids are specific
    // to the Activity or Fragment in which they reside
    static private final int CURRENCY_LOADER_ID = 100;

    private LoaderManager.LoaderCallbacks<Cursor> mLoader;

    // The adapter that binds our data to the listview
    private CurrencyAdapter mAdapter;


    public CurrenciesTabFragment(){
        super();
    }

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

        ListView currenciesListView = (ListView) getActivity().findViewById(R.id.currenciesListView);
        mAdapter = new CurrencyAdapter(getContext() ,null, 0);

        // bind adapter and listview; So that when the adapter will changes, the listview will
        // automatically be updated.
        currenciesListView.setAdapter(mAdapter);

        mLoader = this;
        getLoaderManager().initLoader(CURRENCY_LOADER_ID, null, mLoader);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.currencies_menu, menu);

        // associate searchable config with the SearchView
        SearchManager manager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search);
        searchView.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {

        // Sort order:  Ascending, by date.
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