package com.kimboofactory.iconvert.ui.search.views;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.aleengo.peach.toolbox.commons.common.PeachConfig;
import com.aleengo.peach.toolbox.ui.BaseActivity;
import com.kimboofactory.iconvert.application.IConvertApplication;
import com.kimboofactory.iconvert.ui.search.dagger.SearchModule;
import com.kimboofactory.iconvert.ui.search.presentation.MvpSearchView;
import com.kimboofactory.iconvert.ui.search.presentation.SearchPresenter;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import lombok.Getter;

public class SearchCurrencyActivity extends BaseActivity
//        implements SearchView.OnQueryTextListener
{

    public static final int NO_EXTRA = -1;

    /*@Getter
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    @Getter
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.search_toolbar)
    PeachToolbar toolbar;

    @BindView(R.id.iv_back)
    ImageView mBack;
    @BindView(R.id.sv_search)
    SearchView mSearchView;
    @BindView(R.id.lv_currencies)
    ListView searchCurrencyLV;*/

    //@Getter
    //private Snackbar snackbar;

    /*@Inject
    SearchCurrencyView mMvpView;*/

    @Inject
    MvpSearchView mMvpView;

    @Inject @Getter
    SearchPresenter presenter;
    /*@Inject @Getter
    SearchCurrencyAdapter adapter;*/
    @Getter @Inject
    Integer requestCode;

    @Override
    public String logTag() {
        return "SearchCurrencyActivity";
    }

    @Override
    public View getLayoutView() {
        return mMvpView;
    }

    @Override
    public void daggerConfiguration() {
        // Dagger config
        IConvertApplication.getApplication(this)
                .daggerAppComponent()
                .searchComponentBuilder()
                .searchActivityModule(new SearchModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initialize(@Nullable Bundle savedInstanceState) {
       /* setSupportActionBar(toolbar);

        if (savedInstanceState != null) {

        }

        searchCurrencyLV.setAdapter(adapter);
        searchCurrencyLV.setOnItemClickListener(this::OnItemClick);

        setupSearchView();

        mBack.setOnClickListener( (View v) -> onBackPressed());

        snackbar = Snackbar.make(coordinatorLayout, Helper.EMPTY_STRING, Snackbar.LENGTH_INDEFINITE);
        presenter.attach(mMvpView);

        // setting SwipeRefresh
        setupSwipeRefreshContainer();*/
       mMvpView.init(savedInstanceState);
       presenter.attach(mMvpView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*EventBus.getDefault().register(mMvpView);
        swipeRefreshLayout.setRefreshing(true);*/
        mMvpView.swipeRefresh(true);
        mMvpView.connect2EventBus();
        //presenter.loadCurrencies();
        presenter.start();
    }

    @Override
    protected void onStop() {
        mMvpView.disconnect2EventBus();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mMvpView.clear();
        presenter.detach();

        if(PeachConfig.isDebug()) {
            IConvertApplication.getRefWatcher(this).watch(this);
        }
        super.onDestroy();
    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
*/
   /* @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //adapter.getFilter().filter(newText);
        presenter.filter(newText);
        return false;
    }*/

  /*  private void setupSwipeRefreshContainer() {

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
    }*/

  /*  private void setupSearchView() {
        final SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        mSearchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mSearchView.setOnQueryTextListener(this);
    }*/

    private void OnItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        /*final SearchCurrencyAdapter.ViewHolder viewHolder =
                (SearchCurrencyAdapter.ViewHolder) view.getTag();

        final CheckBox checkBox = viewHolder.getCheckBox();
        final RadioButton radioButton = viewHolder.getRadioButton();

        checkBox.setChecked(!checkBox.isChecked());
        radioButton.setChecked(!radioButton.isChecked());*/

      /*  final SearchItemView itemView = (SearchItemView) view;
        itemView.getCheckBox().setChecked(!itemView.getCheckBox().isChecked());
        itemView.getRadioButton().setChecked(!itemView.getRadioButton().isChecked());

        final CurrencyIHM currencyIHM = (CurrencyIHM) adapter.getItem(position);
        currencyIHM.setCheckboxChecked(itemView.getCheckBox().isChecked());
        currencyIHM.setRadioButtonChecked(itemView.getRadioButton().isChecked());

        adapter.notifyDataSetChanged();

        switch (requestCode) {
            case MainActivity.SEARCH_CURRENCY_REQUEST_CODE:
                presenter.itemSelectedCheckbox(currencyIHM);
                break;
            case MainActivity.CHOOSE_CURRENCY_REQUEST_CODE:
                presenter.itemSelectedRadioButton(currencyIHM);
                break;
            default:
                break;
        }*/

    }
}
