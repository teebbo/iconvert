package com.kimboofactory.iconvert.ui.search;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;
import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.common.BaseActivity;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.util.Helper;
import com.kimboofactory.widget.KFYToolbar;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import butterknife.BindView;

public class SearchCurrencyActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.search_toolbar)
    KFYToolbar toolbar;

    @BindView(R.id.iv_back)
    ImageView mBack;
    @BindView(R.id.sv_search)
    SearchView mSearchView;

    @BindView(R.id.lv_currencies)
    ListView selectCurrencyLV;

    private Snackbar mSnackbar;

    private SearchCurrencyAdapter mAdapter;
    private HashMap<Integer, CurrencyIHM> selectedItems = new LinkedHashMap<>();

    @Override
    public String getClassName() {
        return "SearchCurrencyActivity";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_select_currency;
    }

    @Override
    public void onInitialize() {

        setSupportActionBar(toolbar);

        mAdapter = new SearchCurrencyAdapter(this, getData());
        selectCurrencyLV.setAdapter(mAdapter);
        selectCurrencyLV.setOnItemClickListener(this::OnItemClick);

        setupSearchView();
        mBack.setOnClickListener( (View v) -> onBackPressed());

        mSnackbar = Snackbar.make(coordinatorLayout, "", Snackbar.LENGTH_INDEFINITE);
    }

    private void setupSearchView() {
        final SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        mSearchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mSearchView.setOnQueryTextListener(this);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mAdapter.getFilter().filter(newText);
        return false;
    }

    private void OnItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        final SearchCurrencyAdapter.ViewHolder viewHolder =
                (SearchCurrencyAdapter.ViewHolder) view.getTag();

        final CheckBox checkBox = viewHolder.getCheckBox();
        checkBox.setChecked(!checkBox.isChecked());

        final CurrencyIHM currencyIHM = (CurrencyIHM) mAdapter.getItem(position);
        currencyIHM.setChecked(checkBox.isChecked());

        mAdapter.notifyDataSetChanged();

        if (currencyIHM.getChecked()) {
            selectedItems.put(position, currencyIHM);
        } else {
            selectedItems.remove(position);
        }

        if (selectedItems.size() == 0) {
            showSnackbar(null);
        } else {
            showSnackbar(this::onClick);
        }
    }

    private void onClick(View v) {
        final Intent data = new Intent();

        /*final Bundle extras = new Bundle();
        selectedItems.entrySet()
                .forEach(entry -> extras.putSerializable(entry.getKey() + "", entry.getValue()));

        data.putExtras(extras);*/

        data.putExtra(Helper.EXTRA_SELECTED_ITEM, selectedItems);
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    private void showSnackbar(View.OnClickListener listener) {
        if (listener != null) {
            mSnackbar.setText(selectedItems.size() + " item(s)");
            mSnackbar.setAction("Add", listener);
            mSnackbar.setActionTextColor(Color.CYAN);
            mSnackbar.show();
        } else {
            mSnackbar.dismiss();
        }
    }

    private List<CurrencyIHM> getData() {

        final List<CurrencyIHM> data = new LinkedList<>();

        data.add(new CurrencyIHM( "USD", "United State Dollar", false));
        data.add(new CurrencyIHM( "AUD", "Australian Dollar", false));
        data.add(new CurrencyIHM( "EUR", "Euro", false));
        data.add(new CurrencyIHM( "JPN", "Japanese Yen", false));
        data.add(new CurrencyIHM( "CHY", "Chinese Yen", false));
        data.add(new CurrencyIHM( "XAF", "CFA Franc", false));

        return data;
    }
}
