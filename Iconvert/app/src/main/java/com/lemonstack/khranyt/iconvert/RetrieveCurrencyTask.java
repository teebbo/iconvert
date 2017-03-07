package com.lemonstack.khranyt.iconvert;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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

import com.lemonstack.khranyt.iconvert.data.CurrencyContract;

/**
 * Created by khranyt on 01/11/2015.
 */
public class RetrieveCurrencyTask extends AsyncTask<String, Void, String> {

    private String TAG = RetrieveCurrencyTask.class.getSimpleName();
    private Context mContext;

    Map<String, Double> ratesMap = new HashMap<String, Double>();
    Map<String, String> currenciesFullNameMap = new HashMap<String, String>();

    private TextView mCurrencies;

    public RetrieveCurrencyTask(Context context){
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i(TAG, "Inside onPreExecute");
        Toast.makeText(mContext,
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
                Log.d(TAG, "currenciesFullNameMap is empty");
            }//else {
                //Log.d(TAG, "currenciesFullNameMap is not empty");
                /*for (String key : currenciesFullNameMap.keySet()){
                    //Log.e(TAG, key + " <-> " + currenciesFullNameMap.get(key));
                }*/
            //}
        }
    }

    private void getCurrenciesRates(String currenciesRatesJsonStr) throws IOException {
        Log.d(TAG, "Inside getCurrenciesRates");
        BufferedReader buffer = new BufferedReader(new StringReader(currenciesRatesJsonStr));
        String line;

        while ((line = buffer.readLine()) != null) {
            if (line.equals("{") || line.equals("}"))
                continue;

            String[] vals = line.split(":\\s+");
            //Log.d(TAG, "" + vals.length);

            if (vals.length == 2) {
                //Log.d(TAG, vals[0] + " " + vals[1]);
                String currency = vals[0];
                //Log.d(TAG, "line -> " + line);

                String rate = vals[1];

                if (Pattern.matches("\\d+(\\.\\d+)?,", rate)) {
                    rate = rate.substring(0, rate.indexOf(","));
                    //Log.d(TAG, currency + " <-->" + rate);
                    ratesMap.put(currency, Double.valueOf(rate));
                }
            }
        }

        if (ratesMap.size() == 0){
            Log.d(TAG, "RatesMap is empty");
        }//else{
            //Log.d(TAG, "ratesMap is not empty");
            /*for (String key : ratesMap.keySet()){
                //Log.e(TAG, key + " <<--->> " + String.valueOf(ratesMap.get(key)));
            }*/
        //}
    }

    /**
     * inserts all the currencies rates
     * @param currencies
     */
    private void insertCurrency(String currencies){

        Log.d(TAG, "Inside insertCurrency");
        // fill out the currency table
        if (currencies != null) {
            Log.i(TAG, "currencies rate operation");
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

    /**
     * Inserts all the currencies name
     * @param currenciesFullName
     *
     */
    private void insertCurrencyFullName(String currenciesFullName){
        // fill out the currencyFullName table

        if (currenciesFullName != null) {
            Vector<ContentValues> cVVector = new Vector<ContentValues>();
            try {
                //JSONObject obj = new JSONObject(currenciesFullNameMap);
                getCurrenciesFullName(currenciesFullName);
                for (String key : currenciesFullNameMap.keySet()) {
                    ContentValues cv = new ContentValues();

                    String name = key.trim().substring(1, 4);

                    String fullname = currenciesFullNameMap.get(key).trim();
                    fullname = fullname.substring(fullname.indexOf("\"") + 1, fullname.lastIndexOf("\""));

                    //Log.wtf(TAG, fullname);

                    cv.put(CurrencyContract.CurrencyFullName.COLUMN_NAME, name);
                    cv.put(CurrencyContract.CurrencyFullName.COLUMN_FULL_NAME, fullname);
                    cVVector.add(cv);
                }


                if (cVVector.size() > 0) {
                    ContentValues[] cvArray = new ContentValues[cVVector.size()];
                    cVVector.toArray(cvArray);
                    int inserted = mContext.getContentResolver().bulkInsert(CurrencyContract.CurrencyFullName.CONTENT_URI, cvArray);
                    Log.d(TAG, "Retrieve Task currencies full name Complete. " + inserted + " Inserted");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected String doInBackground(String... urls) {

        Log.i(TAG, "Inside doInBackground");

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
            //mCurrencies.setText(currencies);
            Log.d(TAG, "onPostExecute done without error");
        else
            //mCurrencies.setText("Currencies is null");
            Log.d(TAG, "onPostExecute is not OK");

        Toast.makeText( mContext,
                "Currencies extraction from OpenExchangeRates is terminated",
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
            Log.e(TAG, "Malformed URL Exception");
        } catch (IOException e){
            e.printStackTrace();
            Log.e(TAG, "IOException");
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
            Log.d(TAG, e.getMessage());
        }
        finally {
            if(buffer != null)
                try {
                    buffer.close();
                } catch (IOException e) {
                    Log.d(TAG, e.getMessage());
                }
        }
        return sb.toString();
    }
}
