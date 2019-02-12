package com.kimboofactory.iconvert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kimboofactory.iconvert.common.BaseActivity;
import com.kimboofactory.iconvert.ui.ConversionFragment;
import com.kimboofactory.iconvert.ui.DeviseFragment;
import com.kimboofactory.iconvert.dto.Page;
import com.kimboofactory.iconvert.ui.SearchCurrencyActivity;
import com.kimboofactory.widget.KFYToolbar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;


public class MainActivity extends BaseActivity {

    private static final int SELECT_CURRENCY_REQUEST_CODE = 100;

    @BindView(R.id.toolbar)
    KFYToolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    RetrieveCurrencyTask retrieveCurrencyTask;

    /*private static Uri.Builder createUri (final String path, final String appId) {
        return Uri.parse(Helper.BASE_URL).buildUpon()
                .appendPath("api").appendPath(path)
                .appendQueryParameter("app_id", appId);
    }*/

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

        final Fragment savedFragment = getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        if (savedFragment == null) {
            final Fragment fragment = ConversionFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .show(savedFragment)
                    .commit();
        }
        fab.setOnClickListener((View v) -> {
            final Intent intent = new Intent(MainActivity.this, SearchCurrencyActivity.class);
            startActivityForResult(intent, SELECT_CURRENCY_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_CURRENCY_REQUEST_CODE &&
                resultCode == Activity.RESULT_OK) {
            final Bundle extras = data.getBundleExtra("");
        }
    }

    private List<Page> getData() {
        final List<Page> pages = new ArrayList<>();

        pages.add(new Page( getResources().getString(R.string.fragment_convert_tab_title),
                ConversionFragment.newInstance()));
        pages.add(new Page( getResources().getString(R.string.fragment_devise_tab_title),
                DeviseFragment.newInstance()));
        return pages;
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Creating the ConvertViewPagerAdapter
        *//*final ConvertViewPagerAdapter adapter =
                new ConvertViewPagerAdapter(this, getSupportFragmentManager(), getData());*//*

        // get the ViewPager and setting the adapter
       *//* final ViewPager viewPager = findViewById(R.id.vpager);
        viewPager.setAdapter(adapter);

        configureTabLayout(viewPager);*//*
    }*/

   /* private void configureTabLayout(final ViewPager vp) {
        final TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(vp);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }*/


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
                //final Uri.Builder latestEndPoint = createUri(Helper.LATEST, appId);
                //final Uri.Builder deviseEndPoint = createUri(Helper.CURRENCIES, "");

                //retrieveCurrencyTask = new RetrieveCurrencyTask(getApplicationContext());
                //retrieveCurrencyTask.execute(latestEndPoint.toString(), deviseEndPoint.toString());
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
