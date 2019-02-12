package com.kimboofactory.iconvert.ui;

import android.app.SearchManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.common.BaseActivity;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.ui.adapter.SelectCurrencyAdapter;
import com.kimboofactory.widget.KFYToolbar;

import java.util.LinkedList;
import java.util.List;

import androidx.appcompat.widget.SearchView;
import butterknife.BindView;

public class SearchCurrencyActivity extends BaseActivity {

    @BindView(R.id.search_toolbar)
    KFYToolbar toolbar;

    @BindView(R.id.iv_back)
    ImageView mBack;
    @BindView(R.id.sv_search)
    SearchView mSearchView;

    @BindView(R.id.lv_currencies)
    ListView selectCurrencyLV;


    private SelectCurrencyAdapter mAdapter;

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

        mAdapter = new SelectCurrencyAdapter(this, new LinkedList<>());
        selectCurrencyLV.setAdapter(mAdapter);
        selectCurrencyLV.setOnItemClickListener(this::OnItemClick);

        setupSearchView();
        mBack.setOnClickListener( (View v) -> onBackPressed());
    }

    private void setupSearchView() {
        final SearchManager sm = (SearchManager) getSystemService(SEARCH_SERVICE);
        mSearchView.setSearchableInfo(sm.getSearchableInfo(getComponentName()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.updateDataSet(getData());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void OnItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
        final CurrencyIHM currencyIHM = (CurrencyIHM) mAdapter.getItem(pos);

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
