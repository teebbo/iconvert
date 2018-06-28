package com.kimboo.iconvert;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kimboo.iconvert.dao.NetworkDao;
import com.kimboo.iconvert.dao.devise.DeviseDaoImpl;
import com.kimboo.iconvert.dao.devise.DeviseDao;
import com.kimboo.iconvert.dao.tauxchange.TauxChangeDao;
import com.kimboo.iconvert.dao.tauxchange.TauxChangeDaoImpl;
import com.kimboo.iconvert.model.Devise;
import com.kimboo.iconvert.model.TauxChange;

import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by khranyt on 01/11/2015.
 */
public class RetrieveCurrencyTask extends AsyncTask<String, Void, String> {

    private String TAG = RetrieveCurrencyTask.class.getSimpleName();
    private Context context;
    private DeviseDao deviseDao;
    private TauxChangeDao tauxChangeDao;

    private NetworkDao networkDao = new NetworkDao();

    public RetrieveCurrencyTask(Context context){
        this.context = context;
        deviseDao = new DeviseDaoImpl(context);
        tauxChangeDao = new TauxChangeDaoImpl(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i(TAG, "Inside onPreExecute");
        Toast.makeText(context,
                "Start retrieving currencies from OpenExchangeRates",
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected String doInBackground(String... urls) {

        Log.i(TAG, "Inside doInBackground");

        String latestEndPoint = urls[0];
        final String devisesEndPoint = urls[1];

        final JSONObject currenciesJson =  networkDao.connect(latestEndPoint);
        final JSONObject devisesJson = networkDao.connect(devisesEndPoint);

        JSONObject tauxChangesJson = null;

        try {
            tauxChangesJson = currenciesJson.getJSONObject("rates");
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        deviseDao.save(devisesJson);
        tauxChangeDao.save(tauxChangesJson);

        return String.format("%s\n\n\n%s", currenciesJson.toString(), devisesJson.toString());
    }

    @Override
    protected void onPostExecute(String currencies) {
        super.onPostExecute(currencies);

        if(null != currencies)
            //mCurrencies.setText(currencies);
            Log.d(TAG, "onPostExecute done without error");
        else
            //mCurrencies.setText("Devise is null");
            Log.d(TAG, "onPostExecute is not OK");

        Toast.makeText( context,
                "Devise extraction from OpenExchangeRates is terminated",
                Toast.LENGTH_LONG).show();
    }

}
