package fr.enst.igr201.kanmogne.iconvert;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import fr.enst.igr201.kanmogne.iconvert.data.CurrencyContract;
import fr.enst.igr201.kanmogne.iconvert.data.CurrencyContract.Currency;
import fr.enst.igr201.kanmogne.iconvert.tabs.SlidingTabLayout;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    static private final String BASE_URL = "https://openexchangerates.org/api/";
    static private final String APP_ID = "8c01fe27397241a78baf9ee8b72f6a37";

    static private final String END_POINT_LATEST = BASE_URL + "latest.json?app_id=" + APP_ID;
    static private final String END_POINT_FULL_NAME = BASE_URL + "currencies.json?app_id=" + APP_ID;

    static private final String DISCLAIMER_KEY = "disclaimer";
    static private final String LICENSE_KEY = "license";
    static private final String TIMESTAMP_KEY = "timestamp";
    static private final String BASE_KEY = "base";
    static private final String RATES_KEY = "ratesMap";


    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private ViewPagerAdapter mPagerAdapter;
    private SlidingTabLayout mTabs;


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
        mToolbar = (Toolbar) super.findViewById(R.id.tool_bar);
        super.setSupportActionBar(mToolbar);

        // Creating the ViewPagerAdapter
        mPagerAdapter = new ViewPagerAdapter(super.getSupportFragmentManager(),
                getResources().getStringArray(R.array.tabs_title),
                Integer.valueOf(getResources().getString(R.string.tabs_numbers)));

        // get the ViewPager and setting the adapter
        mViewPager = (ViewPager) super.findViewById(R.id.vpager);
        mViewPager.setAdapter(mPagerAdapter);

        // get the SlidingTabLayout View
        mTabs = (SlidingTabLayout) super.findViewById(R.id.tabs);

        if (null != mTabs) {
            Log.d(TAG, "SlidingTabStrip is not null");
            // To make the tabs fixed, set this to "True"
            mTabs.setDistributeEvenly(true); // this makes the tabs space evenly in available width

            // Setting Custom color for the scroll bar indicator of the Tab View
            mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
                @Override
                public int getIndicatorColor(int position) {
                    //return 0;
                    return getResources().getColor(R.color.tabsScrollColor);
                }
            });
            // setting the ViewPager fo the SlidingTabsLayout
            mTabs.setViewPager(mViewPager);
        }
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
        // automatically handle clicks on the HomeTabFragment/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
