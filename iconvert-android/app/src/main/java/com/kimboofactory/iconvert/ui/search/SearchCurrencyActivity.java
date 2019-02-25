package com.kimboofactory.iconvert.ui.search;

import android.app.SearchManager;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;
import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.common.BaseActivity;
import com.kimboofactory.iconvert.di.Injection;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.util.Helper;
import com.kimboofactory.widget.KFYToolbar;

import org.greenrobot.eventbus.EventBus;

import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import lombok.Getter;

public class SearchCurrencyActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    @Getter
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    @Getter
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.search_toolbar)
    KFYToolbar toolbar;

    @BindView(R.id.iv_back)
    ImageView mBack;
    @BindView(R.id.sv_search)
    SearchView mSearchView;

    @BindView(R.id.lv_currencies)
    ListView searchCurrencyLV;

    @Getter
    private Snackbar snackbar;
    @Getter
    private SearchPresenter presenter;
    private SearchCurrencyView mMvpView;

    @Getter
    private SearchCurrencyAdapter adapter;

    @Override
    public String getClassName() {
        return "SearchCurrencyActivity";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_search_list;
    }

    @Override
    public void onInitialize() {

        setSupportActionBar(toolbar);

        adapter = new SearchCurrencyAdapter(this, Helper.CURRENCY_IHMS_EMPTY_LIST);
        searchCurrencyLV.setAdapter(adapter);
        searchCurrencyLV.setOnItemClickListener(this::OnItemClick);

        setupSearchView();

        mBack.setOnClickListener( (View v) -> onBackPressed());
        snackbar = Snackbar.make(coordinatorLayout, Helper.EMPTY_STRING, Snackbar.LENGTH_INDEFINITE);

        mMvpView = new SearchCurrencyView();
        mMvpView.attachUi(this);

        presenter = new SearchPresenter(Injection.provideUseCaseHandler(),
                Injection.provideGetCurrencies(this));

        presenter.attach(mMvpView);

        // setting SwipeRefresh
        setupSwipeRefreshContainer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(mMvpView);
        swipeRefreshLayout.setRefreshing(true);
        presenter.loadCurrencies();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(mMvpView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
        presenter = null;
        mMvpView = null;
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
        //adapter.getFilter().filter(newText);
        presenter.filter(newText);
        return false;
    }

    private void setupSwipeRefreshContainer() {

        final Runnable runnable = presenter::loadCurrencies;

        final SwipeRefreshLayout.OnRefreshListener onRefreshListener =
                () -> (new Handler()).postDelayed(runnable, Helper.DELAY_MILLIS_2000);

        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        // configure the refreshing colors
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void setupSearchView() {
        final SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        mSearchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mSearchView.setOnQueryTextListener(this);
    }

    private void OnItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        final SearchCurrencyAdapter.ViewHolder viewHolder =
                (SearchCurrencyAdapter.ViewHolder) view.getTag();

        final CheckBox checkBox = viewHolder.getCheckBox();
        checkBox.setChecked(!checkBox.isChecked());

        final CurrencyIHM currencyIHM = (CurrencyIHM) adapter.getItem(position);
        currencyIHM.setChecked(checkBox.isChecked());
        adapter.notifyDataSetChanged();

        presenter.itemSelected(currencyIHM);
    }


    /*private List<CurrencyIHM> getData() {

        final List<CurrencyIHM> data = new LinkedList<>();

        data.add(new CurrencyIHM( "USD", "United State Dollar", false));
        data.add(new CurrencyIHM( "AUD", "Australian Dollar", false));
        data.add(new CurrencyIHM( "EUR", "Euro", false));
        data.add(new CurrencyIHM( "JPN", "Japanese Yen", false));
        data.add(new CurrencyIHM( "CHY", "Chinese Yen", false));
        data.add(new CurrencyIHM( "XAF", "CFA Franc", false));

        return data;
    }*/
}
