package com.kimboofactory.iconvert.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.RetrieveCurrencyTask;
import com.kimboofactory.iconvert.adapter.ConvertViewPagerAdapter;
import com.kimboofactory.iconvert.common.BaseActivity;
import com.kimboofactory.iconvert.fragment.ConversionFragment;
import com.kimboofactory.iconvert.fragment.DeviseFragment;
import com.kimboofactory.iconvert.model.Page;
import com.kimboofactory.iconvert.util.Helper;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;


public class MainActivity extends BaseActivity {


    RetrieveCurrencyTask retrieveCurrencyTask;

    private static Uri.Builder createUri (final String path, final String appId) {
        return Uri.parse(Helper.BASE_URL).buildUpon()
                .appendPath("api").appendPath(path)
                .appendQueryParameter("app_id", appId);
    }

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

    }

    private List<Page> getData() {
        final List<Page> pages = new ArrayList<>();

        pages.add(new Page( getResources().getString(R.string.fragment_convert_tab_title),
                ConversionFragment.newInstance()));
        pages.add(new Page( getResources().getString(R.string.fragment_devise_tab_title),
                DeviseFragment.newInstance()));
        return pages;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Creating the ConvertViewPagerAdapter
        final ConvertViewPagerAdapter adapter =
                new ConvertViewPagerAdapter(this, getSupportFragmentManager(), getData());

        // get the ViewPager and setting the adapter
        final ViewPager viewPager = findViewById(R.id.vpager);
        viewPager.setAdapter(adapter);

        configureTabLayout(viewPager);
    }

    private void configureTabLayout(final ViewPager vp) {
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
                final Uri.Builder latestEndPoint = createUri(Helper.LATEST, appId);
                final Uri.Builder deviseEndPoint = createUri(Helper.CURRENCIES, "");

                retrieveCurrencyTask = new RetrieveCurrencyTask(getApplicationContext());
                retrieveCurrencyTask.execute(latestEndPoint.toString(), deviseEndPoint.toString());
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
