package com.lemonstack.khranyt.iconvert.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.lemonstack.khranyt.iconvert.R;
import com.lemonstack.khranyt.iconvert.RetrieveCurrencyTask;
import com.lemonstack.khranyt.iconvert.adapter.CurrencyViewPagerAdapter;
import com.lemonstack.khranyt.iconvert.fragment.dialog.CurrenciesDialogFragment;


public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    static private final String BASE_URL = "https://openexchangerates.org/api";
    static private final String APP_ID = "8c01fe27397241a78baf9ee8b72f6a37";

    static private final String END_POINT_LATEST = uriBuilder("latest.json");
    static private final String END_POINT_FULL_NAME = uriBuilder("currencies.json");

    RetrieveCurrencyTask retrieveCurrencyTask;

    private static String uriBuilder (String path) {
        return Uri.parse(BASE_URL).buildUpon().appendPath(path).appendQueryParameter("app_id", APP_ID).toString();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        Log.d(TAG, "onAttachFragment --> " + fragment.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, MainActivity.class.toString() + " OnCreate");
        setContentView(R.layout.main);

        // create a toolbar and setting it as the toolbar for the activity
        final Toolbar toolbar = (Toolbar) super.findViewById(com.lemonstack.khranyt.iconvert.R.id.toolbar);
        super.setSupportActionBar(toolbar);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        setupTabs(tabLayout);

        // Creating the CurrencyViewPagerAdapter
        final CurrencyViewPagerAdapter adapter =
                new CurrencyViewPagerAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());

        // get the ViewPager and setting the adapter
        final ViewPager viewPager = (ViewPager) super.findViewById(R.id.vpager);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupTabs(final TabLayout tabLayout) {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.convert_tab_title));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.currencies_tab_title));
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
                retrieveCurrencyTask = new RetrieveCurrencyTask(getApplicationContext());
                retrieveCurrencyTask.execute(END_POINT_LATEST, END_POINT_FULL_NAME);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
