package com.kimboofactory.iconvert.ui.home.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aleengo.peach.toolbox.commons.common.PeachConfig;
import com.aleengo.peach.toolbox.ui.BaseActivity;
import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.application.IConvertApplication;
import com.kimboofactory.iconvert.common.Constant;
import com.kimboofactory.iconvert.ui.home.dagger.HomeModule;
import com.kimboofactory.iconvert.ui.home.presentation.MainPresenter;
import com.kimboofactory.iconvert.ui.home.presentation.MvpHomeView;

import java.io.Serializable;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import lombok.Getter;


public class MainActivity extends BaseActivity {

    /*@BindView(R.id.toolbar)
    PeachToolbar toolbar;

    @BindView(R.id.rl_currency)
    RelativeLayout currencyRL;
    @Getter
    @BindView(R.id.text_view_code)
    TextView textViewCodeSrc;

    @BindView(R.id.tv_update_date)
    TextView updateDateTV;
    @Getter
    @BindView(R.id.et_amount)
    EditText amountET;
    @BindView(R.id.lv_favorites)
    ListView favoritesLV;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Inject @Getter
    FavoritesAdapter favoritesAdapter;
    @Inject
    MainView mMvpView;
    @Inject @Getter
    MainPresenter presenter;
    @Inject
    HomeViewListener listener;

    @Getter @Setter
    private CurrencyIHM currencySource;
*/
    @Inject
    MvpHomeView mMvpView;
    @Inject @Getter
    MainPresenter presenter;
//    private boolean mFirstLoad = true;

    @Override
    public String logTag() {
        return "MainActivity";
    }

    @Override
    public void daggerConfiguration() {
        IConvertApplication.getApplication(this)
                .daggerAppComponent()
                .homeComponentBuilder()
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

   /* @Override
    public int getLayoutResId() {
        return mMvpView.layout();
    }*/

    @Override
    public View getLayoutView() {
        return mMvpView;
    }

    @Override
    protected void initialize(@Nullable Bundle savedInstanceState) {

        /*setSupportActionBar(toolbar);

        favoritesLV.setAdapter(favoritesAdapter);
        favoritesLV.setOnItemClickListener(listener);
        favoritesLV.setOnItemLongClickListener(listener);

        fab.setOnClickListener((View v) -> presenter.addFavorite(SEARCH_CURRENCY_REQUEST_CODE));
        presenter.attach(mMvpView);

        amountET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0) {
                    currencySource.setAmount(s.toString());
                    final NamedRunnable task = new NamedRunnable("%s", "OnTextChanged") {
                        @Override
                        protected void execute() {
                            final List<CurrencyIHM> oldItems = Helper.copy(favoritesAdapter.getItems());
                            favoritesAdapter.clear();
                            mMvpView.updateFavoritesList(oldItems);
                        }
                    };
                    new Handler().postDelayed(task, Helper.DELAY_MILLIS_500);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        currencyRL.setOnClickListener(v -> presenter.addFavorite(CHOOSE_CURRENCY_REQUEST_CODE));*/
        mMvpView.init(savedInstanceState);
        presenter.attach(mMvpView);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constant.SEARCH_CURRENCY_REQUEST_CODE &&
                resultCode == Activity.RESULT_OK) {
            final Serializable extras = data.getSerializableExtra(Constant.EXTRA_SELECTED_ITEMS);
            presenter.updateFavorites(resultCode, extras);
        } else if (requestCode == Constant.CHOOSE_CURRENCY_REQUEST_CODE &&
                resultCode == Activity.RESULT_OK) {
            final Serializable extra = data.getSerializableExtra(Constant.EXTRA_SELECTED_ITEM);
            presenter.updateSourceCurrency(resultCode, extra);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.loadRatesAndCurrencies(false);
        mMvpView.connect2EventBus();
    }

    @Override
    protected void onResume() {
        super.onResume();
       /* if (mFirstLoad) {
            currencySource = mMvpView.getDefaultCurrency();

            final String codeLibelle = getResources().getString(R.string.label_code_libelle,
                    currencySource.getEntity().getCode(), currencySource.getEntity().getLibelle());
            textViewCodeSrc.setText(codeLibelle);

            mFirstLoad = false;
        }*/
       mMvpView.start();
       presenter.loadFavorites();
    }

    @Override
    protected void onStop() {
        mMvpView.disconnect2EventBus();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        presenter.detach();

        if (PeachConfig.isDebug()) {
            IConvertApplication.getRefWatcher(MainActivity.this).watch(MainActivity.this);
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the ConversionFragment/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
               return true;
            case R.id.action_refresh:
                final String appId = getString(R.string.openexchangerates_app_id);
                return true;
            case R.id.action_clear:
                presenter.removeFavorites();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
