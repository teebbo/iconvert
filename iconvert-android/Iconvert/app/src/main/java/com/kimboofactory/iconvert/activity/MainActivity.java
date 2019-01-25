package com.kimboofactory.iconvert.activity;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.RetrieveCurrencyTask;
import com.kimboofactory.iconvert.adapter.ViewPagerAdapter;
import com.kimboofactory.iconvert.fragment.ConversionFragment;
import com.kimboofactory.iconvert.fragment.DeviseFragment;
import com.kimboofactory.iconvert.model.Page;
import com.kimboofactory.iconvert.model.Resource;
import com.kimboofactory.iconvert.util.Helper;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    private RetrieveCurrencyTask retrieveCurrencyTask;
    private ViewPagerAdapter mAdapter;

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

    private List<Page> getData() {
        final List<Page> pages = new ArrayList<>();

        Resource resource = getResource(String.class, R.string.fragment_convert_tab_title);
        pages.add(new Page( (String)resource.get() , ConversionFragment.newInstance()));

        resource = getResource(String.class, R.string.fragment_devise_tab_title);
        pages.add(new Page( (String) resource.get(), DeviseFragment.newInstance()));
        return pages;
    }

    @Override
    public void initialize(View view) {
        // Creating the ViewPagerAdapter
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), new ArrayList<Page>());

        // get the ViewPager and setting the adapter
        final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(mAdapter);

        final TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
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
    protected void onStart() {
        super.onStart();
        mAdapter.swapData(getData());
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
