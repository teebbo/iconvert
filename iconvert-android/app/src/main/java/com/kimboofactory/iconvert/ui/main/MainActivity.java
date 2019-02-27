package com.kimboofactory.iconvert.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aleengo.peach.toolbox.widget.PeachToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.common.BaseActivity;
import com.kimboofactory.iconvert.di.Injection;
import com.kimboofactory.iconvert.dto.CurrencyIHM;
import com.kimboofactory.iconvert.util.Helper;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.LinkedList;

import androidx.annotation.Nullable;
import butterknife.BindView;
import lombok.Getter;
import lombok.Setter;


public class MainActivity extends BaseActivity {

    public static final int SEARCH_CURRENCY_REQUEST_CODE = 100;
    public static final int CHOOSE_CURRENCY_REQUEST_CODE = 101;
    public static final String REQUEST_CODE = "com.kimboofactory.iconvert.REQUEST_CODE";

    private static final long DELAY_MILLIS = 400;

    @BindView(R.id.toolbar)
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

    @Getter
    private FavoritesAdapter favoritesAdapter;
    private MainPresenter mPresenter;
    private MainView mMvpView;
    @Getter @Setter
    private CurrencyIHM currencySource = new CurrencyIHM();

    private boolean mFirstLoad = true;

    @Override
    public String getClassName() {
        return "MainActivity";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onInitialize() {

        setSupportActionBar(toolbar);

        favoritesAdapter = new FavoritesAdapter(this, new LinkedList<>());
        favoritesLV.setAdapter(favoritesAdapter);

        fab.setOnClickListener((View v) -> mPresenter.addFavorite(SEARCH_CURRENCY_REQUEST_CODE));

        mMvpView = new MainView();
        mMvpView.attachUi(this);

        mPresenter = new MainPresenter(Injection.provideUseCaseHandler(),
                Injection.provideGetCurrencies(getApplicationContext()),
                Injection.provideGetCurrency(getApplicationContext()),
                Injection.provideGetRates(getApplicationContext()),
                Injection.provideGetRatesAndCurrencies(getApplicationContext()));

        mPresenter.attach(mMvpView);

        amountET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0) {
                    /*mCurrency = new CurrencyIHM("EUR", "EURO", false, "1.0");
                    mCurrency.setComputeRate(s.toString());

                    (new Handler()).postDelayed(() -> mPresenter.loadCurrency(mCurrency), Helper.DELAY_MILLIS_2000);*/
                    currencySource.setAmount(s.toString());
                    final Runnable task = () -> mMvpView.updateFavoritesList(favoritesAdapter.getItems());
                    new Handler().postDelayed(task, Helper.DELAY_MILLIS_400);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        currencyRL.setOnClickListener(v -> mPresenter.addFavorite(CHOOSE_CURRENCY_REQUEST_CODE));
    }

    @Override
    protected void onResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == SEARCH_CURRENCY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            final Serializable extras = data.getSerializableExtra(Helper.EXTRA_SELECTED_ITEMS);
            mPresenter.updateFavorites(resultCode, extras);
        } else if (requestCode == CHOOSE_CURRENCY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            final Serializable extra = data.getSerializableExtra(Helper.EXTRA_SELECTED_ITEM);
            mPresenter.updateSourceCurrency(resultCode, extra);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.loadRatesAndCurrencies(false);
        EventBus.getDefault().register(mMvpView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mFirstLoad) {
            mPresenter.loadDefaultCurrency();
            mFirstLoad = false;

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(mMvpView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
        mPresenter = null;
        mMvpView = null;
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
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
