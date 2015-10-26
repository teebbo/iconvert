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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Pattern;

import fr.enst.igr201.kanmogne.iconvert.R;
import fr.enst.igr201.kanmogne.iconvert.data.CurrencyContract;

/**
 * Created by joffrey on 24/10/15.
 */
public class HomeTabFragment extends Fragment {
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

    private final String TAG = HomeTabFragment.class.getSimpleName();

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

    private double queryCurrencyTable(Uri uri){
        Log.wtf(TAG, "URI : -> " + CurrencyContract.Currency.getCurrencyNameFromURI(uri));

        double rate = 0.0;

        String[] projection  = {CurrencyContract.Currency.COLUMN_RATE};
        String selection = String .format("%s.%s = ?",
                CurrencyContract.Currency.TABLE_CURRENCY,
                CurrencyContract.Currency.COLUMN_NAME);

        Log.wtf(TAG, "getNameFromURI : -> " + CurrencyContract.Currency.getCurrencyNameFromURI(uri));

        String[] selectionArgs = {CurrencyContract.Currency.getCurrencyNameFromURI(uri)};
        Cursor cursor = mResolver.query(uri, projection, selection , selectionArgs, null);
        try{
            if(cursor.moveToFirst()) {
                int rateIndex = cursor.getColumnIndex(CurrencyContract.Currency.COLUMN_RATE);
                rate = cursor.getDouble(rateIndex);
            }
        }
        finally {
            cursor.close();
        }

        Log.wtf(TAG, "Rate : -->>" + rate);

        return rate;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_home_tab, container, false);
        return rootview;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach --> " + context.toString());

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "Inside onActivityCreated");

        Button mConvertBtn = (Button) getActivity().findViewById(R.id.convert_btn);
        ImageView mSwapBtn = (ImageView) getActivity().findViewById(R.id.swap_iv);
        Button mRateBtn = (Button) getActivity().findViewById(R.id.currencies_btn);

        mLeftEditText = (EditText) getActivity().findViewById(R.id.left_edit_text);
        mLeftTextView = (TextView) getActivity().findViewById(R.id.left_text_view);

        mRightTextView = (TextView) getActivity().findViewById(R.id.right_text_view);
        mResult = (TextView) getActivity().findViewById(R.id.right_text_view_result);

        mCurrencies = (TextView) getActivity().findViewById(R.id.currencies);

        mRes = getResources();
        mResolver = getActivity().getContentResolver();

        // initialize up and down state
        mLeftState =  mRes.getString(R.string.euro_text_view);
        mRightState = mRes.getString(R.string.usd_currency_text_view);
        if(mConvertBtn == null){
            Log.d(TAG, "mConvert btn is null");
        }
        else {
            mConvertBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    // get text view text of the left side
                    String fromStr = mLeftTextView.getText().toString();

                    // get text view text of the right side
                    String toStr = mRightTextView.getText().toString();

                    // get the user input
                    String amount = mLeftEditText.getText().toString();

                    // check if user input is empty
                    if (amount.equals("") || amount.isEmpty()) {
                        if (!mResult.getText().toString().isEmpty()) {
                            mResult.setText("");
                        }
                        Toast.makeText(getActivity().getApplication(), fromStr + " field is required", Toast.LENGTH_LONG).show();
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
        }

        if(mSwapBtn != null) {
            mSwapBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String fromStr = mLeftTextView.getText().toString();
                    String toStr = mRightTextView.getText().toString();

                    if (mLeftState.equals(fromStr) &
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

        }
        else
            Log.d(TAG, "swap btn is null");
        if(mRateBtn != null) {
            mRateBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    RetrieveCurrencyTask retrieveCurrencyTask = new RetrieveCurrencyTask(getActivity().getApplicationContext());
                    retrieveCurrencyTask.execute(END_POINT_LATEST, END_POINT_FULL_NAME);
                }
            });
        } else
            Log.d(TAG, "rate btn is null");
    }

    private class RetrieveCurrencyTask extends AsyncTask<String, Void, String> {

        private String AsynTask_TAG = RetrieveCurrencyTask.class.getSimpleName();
        private Context mContext;

        public RetrieveCurrencyTask(Context context){
            this.mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(AsynTask_TAG, "Inside onPreExecute");
            Toast.makeText( getActivity().getApplication(),
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

        private void getCurrenciesFullName(String currenciesFullNameJsonStr) throws IOException {
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
                    /*currenciesObj = new JSONObject(currencies);
                    String timeStamp = currenciesObj.getString(TIMESTAMP_KEY);
                    Log.i(AsynTask_TAG, timeStamp);
                    JSONObject rates = currenciesObj.getJSONObject(RATES_KEY);
                    Log.i(AsynTask_TAG, rates.getString("EUR"));*/

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

            Toast.makeText( getActivity().getApplication(),
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
    }
}