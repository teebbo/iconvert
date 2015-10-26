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

    // variables on the left side
    private TextView mLeftTextView;
    private EditText mLeftEditText;


    // variables on the right side
    private TextView mRightTextView;
    private TextView mResult;

    private TextView mCurrencies;

    String mLeftState = "";
    String mRightState = "";

    boolean fromCurrency = false;
    boolean toCurrency = false;

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


    Map<String, Double> ratesMap = new HashMap<String, Double>();
    Map<String, String> currenciesFullNameMap = new HashMap<String, String>();

    JSONObject currenciesObj = null;

    Resources mRes;
    ContentResolver mResolver;


    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private ViewPagerAdapter mPagerAdapter;
    private SlidingTabLayout mTabs;

    static private final String[] TITLES = {"Home", "Currencies", "About"};
    static private final int TABS_NUMBERS = 3;


    /*private double queryCurrencyTable(Uri uri){
        Log.wtf(TAG, "URI : -> " + CurrencyContract.Currency.getCurrencyNameFromURI(uri));

        double rate = 0.0;

        String[] projection  = {CurrencyContract.Currency.COLUMN_RATE};
        String selection = String .format("%s.%s = ?",
                Currency.TABLE_CURRENCY,
                Currency.COLUMN_NAME);

        Log.wtf(TAG, "getNameFromURI : -> " + CurrencyContract.Currency.getCurrencyNameFromURI(uri));

        String[] selectionArgs = {CurrencyContract.Currency.getCurrencyNameFromURI(uri)};
        Cursor cursor = mResolver.query(uri, projection, selection , selectionArgs, null);
        try{
            if(cursor.moveToFirst()) {
                int rateIndex = cursor.getColumnIndex(Currency.COLUMN_RATE);
                rate = cursor.getDouble(rateIndex);
            }
        }
        finally {
            cursor.close();
        }

        Log.wtf(TAG, "Rate : -->>" + rate);

        return rate;
    }*/

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        Log.d(TAG, "onAttachFragment --> " + fragment.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, MainActivity.class.toString() +  " OnCreate");
        setContentView(R.layout.main);

        // create a toolbar and setting it as the toolbar for the activity
        mToolbar = (Toolbar) super.findViewById(R.id.tool_bar);
        super.setSupportActionBar(mToolbar);

        // Creating the ViewPagerAdapter
        mPagerAdapter = new ViewPagerAdapter(super.getSupportFragmentManager(), TITLES, TABS_NUMBERS);

        // get the ViewPager and setting the adapter
        mViewPager = (ViewPager) super.findViewById(R.id.vpager);
        mViewPager.setAdapter(mPagerAdapter);

        // get the SlidingTabLayout View
        mTabs = (SlidingTabLayout) super.findViewById(R.id.tabs);

        if(null != mTabs){
            Log.d(TAG, "SlidingTabStrip is not null");
            // To make the tabs fixed, set this to "True"
            mTabs.setDistributeEvenly(true); // this makes the tabs space evenly in available width

            // Setting Custom color for the scroll bar indicator of the Tab View
            mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer(){
                @Override
                public int getIndicatorColor(int position) {
                    return 0;
                    //return mRes.getColor(R.color.tabsScrollColor);
                }
            });

            // setting the ViewPager fo the SlidingTabsLayout
            mTabs.setViewPager(mViewPager);
        }

		mLeftEditText = (EditText) super.findViewById(R.id.left_edit_text);
        mLeftTextView = (TextView) super.findViewById(R.id.left_text_view);

        mRightTextView = (TextView) super.findViewById(R.id.right_text_view);
		mResult = (TextView) super.findViewById(R.id.right_text_view_result);

        mCurrencies = (TextView) super.findViewById(R.id.currencies);

		/*Button mConvertBtn = (Button) super.findViewById(R.id.convert_btn);
        ImageView mSwapBtn = (ImageView) super.findViewById(R.id.swap_iv);
        Button mRateBtn = (Button) super.findViewById(R.id.currencies_btn);


        mRes = getResources();
        mResolver = getContentResolver();

        // initialize up and down state
        mLeftState =  mRes.getString(R.string.euro_text_view);
        mRightState = mRes.getString(R.string.usd_currency_text_view);

        mConvertBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // get text view text of the left side
                String fromStr = mLeftTextView.getText().toString();

                // get text view text of the right side
                String toStr = mRightTextView.getText().toString();

                // get the user input
                String amount = mLeftEditText.getText().toString();

                // check if user input is empty
                if(amount.equals("") || amount.isEmpty()){
                    if(!mResult.getText().toString().isEmpty()){
                        mResult.setText("");
                    }
                    Toast.makeText(getApplication(), fromStr + " field is required", Toast.LENGTH_LONG).show();
                } else {
                    Uri builtFromURI = CurrencyContract.Currency.buildCurrencyRateURI(fromStr);
                    double fromRate = queryCurrencyTable(builtFromURI);

                    Uri toURI = CurrencyContract.Currency.buildCurrencyRateURI(toStr);
                    double toRate = queryCurrencyTable(toURI);

                    // compute the rate relative to left and right side
                    double rate = toRate / fromRate;

                    double finalAmount = Double.valueOf(amount) * rate;
                    mResult.setText(String.valueOf(finalAmount));
                }
            }
        });


        mSwapBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String fromStr = mLeftTextView.getText().toString();
                String toStr = mRightTextView.getText().toString();

                if(mLeftState.equals(fromStr) &
                        mRightState.equals(toStr)) {

                    mLeftTextView.setText(toStr);
                    mRightTextView.setText(fromStr);

                    mLeftState = toStr;
                    mRightState = fromStr;
                } else {

                    mLeftTextView.setText(fromStr);
                    mRightTextView.setText(toStr);

                    mLeftState = fromStr;
                    mRightState = toStr;
                }
            }
        });
*/
       /* mRateBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                RetrieveCurrencyTask retrieveCurrencyTask = new RetrieveCurrencyTask(getApplicationContext());
                retrieveCurrencyTask.execute(END_POINT_LATEST, END_POINT_FULL_NAME);
            }
        });*/
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

    /*private class RetrieveCurrencyTask extends AsyncTask<String, Void, String> {

        private String AsynTask_TAG = RetrieveCurrencyTask.class.getSimpleName();
        private Context mContext;

        public RetrieveCurrencyTask(Context context){
            this.mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(AsynTask_TAG, "Inside onPreExecute");
            Toast.makeText( getApplicationContext(),
                            "Start retrieving currencies from OpenExchangeRates",
                            Toast.LENGTH_LONG).show();
        }

        private Map<String, String> getCurrencies(String ratesString, String fullName){
            Map<String, String> map = new HashMap<String, String>();

            if(ratesMap.size() == currenciesFullNameMap.size()){
                    for(String key : currenciesFullNameMap.keySet()){
                        String newKey = String.format("%s--%s", key, currenciesFullNameMap.get(key));
                        String rate = String.valueOf(ratesMap.get(key));

                        map.put(newKey, rate);
                    }
            }
            return map;
        }
        
        private void getCurrenciesFullName(String currenciesFullNameJsonStr) throws IOException{
            BufferedReader buffer = new BufferedReader(new StringReader(currenciesFullNameJsonStr));
            String line;
            while((line = buffer.readLine()) != null){
                if(line.equals("{") || line.equals("}"))
                    continue;
                String currencyName = line.split(":\\s+")[0];
                String currencyFullName = line.split(":\\s+")[1];

                currenciesFullNameMap.put(currencyName, currencyFullName);

                if(currenciesFullNameMap.size() == 0){
                    Log.d(AsynTask_TAG, "currenciesFullNameMap is empty");
                }else{
                    Log.d(AsynTask_TAG, "currenciesFullNameMap is not empty");
                    for (String key : currenciesFullNameMap.keySet()){
                        Log.e(AsynTask_TAG, key + " <-> " + currenciesFullNameMap.get(key));
                    }
                }
            }
        }

        private void getCurrenciesRates(String currenciesRatesJsonStr) throws IOException {
            Log.d(AsynTask_TAG, "Inside getCurrenciesRates");
            BufferedReader buffer = new BufferedReader(new StringReader(currenciesRatesJsonStr));
            String line;

            while ((line = buffer.readLine()) != null) {
                if (line.equals("{") || line.equals("}"))
                    continue;

                String[] vals = line.split(":\\s+");
                Log.d(AsynTask_TAG, "" + vals.length);

                if (vals.length == 2) {
                    Log.d(AsynTask_TAG, vals[0] + " " + vals[1]);
                    String currency = vals[0];
                    Log.d(AsynTask_TAG, "line -> " + line);

                    String rate = vals[1];

                    if (Pattern.matches("\\d+(\\.\\d+)?,", rate)) {
                        rate = rate.substring(0, rate.indexOf(","));
                        Log.d(AsynTask_TAG, currency + " <-->" + rate);
                        ratesMap.put(currency, Double.valueOf(rate));
                    }
                }
            }

            if (ratesMap.size() == 0){
                Log.d(AsynTask_TAG, "RatesMap is empty");
            }else{
                for (String key : ratesMap.keySet()){
                    Log.e(AsynTask_TAG, key + " <<--->> " + String.valueOf(ratesMap.get(key)));
                }
            }
        }

    // TODO: modifier la valeur de la variable key afin de faire des insertions sans les quotes
       private void insertCurrency(String currencies){

            Log.d(AsynTask_TAG, "Inside insertCurrency");
            // fill out the currency table
            if (currencies != null) {
                Log.i(AsynTask_TAG, "currencies rate operation");
                Vector<ContentValues> cVVector = new Vector<ContentValues>();

     try {
                    *//*currenciesObj = new JSONObject(currencies);
                    String timeStamp = currenciesObj.getString(TIMESTAMP_KEY);
                    Log.i(AsynTask_TAG, timeStamp);
                    JSONObject rates = currenciesObj.getJSONObject(RATES_KEY);
                    Log.i(AsynTask_TAG, rates.getString("EUR"));*//*

                    getCurrenciesRates(currencies);
                    for (String key : ratesMap.keySet()) {
                        ContentValues cv = new ContentValues();

                        String name = key.trim().substring(1, 4);

                        cv.put(CurrencyContract.Currency.COLUMN_NAME, name);
                        cv.put(CurrencyContract.Currency.COLUMN_RATE, ratesMap.get(key));
                        cVVector.add(cv);
                    }

                    if (cVVector.size() > 0) {
                        ContentValues[] cvArray = new ContentValues[cVVector.size()];
                        cVVector.toArray(cvArray);
                        int inserted = mContext.getContentResolver().bulkInsert(CurrencyContract.Currency.CONTENT_URI, cvArray);
                        Log.d(TAG, "Retrieve Task Currencies Complete. " + inserted + " Inserted");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        // TODO: modifier la valeur de la variable key afin de faire des insertions sans les quotes
        private void insertCurrencyFullName(String currenciesFullName){
            // fill out the currencyFullName table
            if (currenciesFullName != null){
                Vector<ContentValues> cVVector = new Vector<ContentValues>();

                try {
                    //JSONObject obj = new JSONObject(currenciesFullNameMap);
                    getCurrenciesFullName(currenciesFullName);
                    for(String key : currenciesFullNameMap.keySet()){
                        ContentValues cv = new ContentValues();

                        String name = key.trim().substring(1, 4);

                        String fullname = currenciesFullNameMap.get(key).trim();
                        fullname = fullname.substring(fullname.indexOf("\"") + 1, fullname.lastIndexOf("\""));

                        Log.wtf(AsynTask_TAG, fullname);

                        cv.put(CurrencyContract.CurrencyFullName.COLUMN_NAME, name);
                        cv.put(CurrencyContract.CurrencyFullName.COLUMN_FULL_NAME, fullname);
                        cVVector.add(cv);
                    }

                    if(cVVector.size() > 0)
                    {
                        ContentValues[] cvArray = new ContentValues[cVVector.size()];
                        cVVector.toArray(cvArray);
                        int inserted = mContext.getContentResolver().bulkInsert(CurrencyContract.CurrencyFullName.CONTENT_URI, cvArray);
                        Log.d(TAG, "Retrieve Task currencies full name Complete. " + inserted + " Inserted");
                    }

                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected String doInBackground(String... urls) {

            Log.i(AsynTask_TAG, "Inside doInBackground");

            String latestEndPoint = urls[0];
            String currenciesFullNameEndPoint = urls[1];

            String currencies =  connectTo(latestEndPoint);
            String currenciesFullName = connectTo(currenciesFullNameEndPoint);

            insertCurrency(currencies);
            insertCurrencyFullName(currenciesFullName);

            return String.format("%s\n\n\n%s", currencies, currenciesFullName);
        }

        @Override
        protected void onPostExecute(String currencies) {
            super.onPostExecute(currencies);

            //Log.e(AsynTask_TAG, currencies);

            if(null != currencies)
                mCurrencies.setText(currencies);
            else
                mCurrencies.setText("Currencies is null");

            Toast.makeText( getApplicationContext(),
                    "Currencies extraction operation from OpenExchangeRates is terminated",
                    Toast.LENGTH_LONG).show();
        }

        private String connectTo(String endPoint)
        {
            URL url;
            HttpURLConnection urlConn = null;
            String resp = null;

            try {
                url = new URL(endPoint);
                urlConn = (HttpURLConnection) url.openConnection();
                resp = readStream(urlConn.getInputStream());

            } catch (MalformedURLException e){
                e.printStackTrace();
                Log.e(AsynTask_TAG, "Malformed URL Exception");
            } catch (IOException e){
                e.printStackTrace();
                Log.e(AsynTask_TAG, "IOException");
            }
            finally {
                urlConn.disconnect();
            }
            return resp;
        }

        private String readStream(InputStream in)
        {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
            StringBuffer sb = new StringBuffer();

            try
            {
                String line;
                while((line = buffer.readLine()) != null)
                    sb.append(line).append("\n");

                if(sb.length() == 0){
                    // sb is empty
                    return "";
                }
            } catch(IOException e)
            {
                Log.d(AsynTask_TAG, e.getMessage());
            }
            finally {
                if(buffer != null)
                    try {
                        buffer.close();
                    } catch (IOException e) {
                        Log.d(AsynTask_TAG, e.getMessage());
                    }
            }
            return sb.toString();
        }
    }*/
}
