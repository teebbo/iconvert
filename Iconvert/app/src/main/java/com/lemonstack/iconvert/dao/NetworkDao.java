package com.lemonstack.iconvert.dao;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by khranyt on 31/03/2017.
 */

public class NetworkDao {

    private static final String TAG = NetworkDao.class.getName();

    public NetworkDao(){}

    public JSONObject connect(final String url) {
        URL urlObj;
        HttpsURLConnection urlConn = null;

        try {
            urlObj = new URL(url);
            urlConn = (HttpsURLConnection) urlObj.openConnection();
            urlConn.setDoInput(true);

            // Open communications link (network traffic occurs here).
            urlConn.connect();

            final int responseCode = urlConn.getResponseCode();

            if(responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }

            return readStream(urlConn.getInputStream());
        } catch (MalformedURLException e){
            e.printStackTrace();
            Log.e(TAG, "Malformed URL Exception");
            throw new RuntimeException(e);
        } catch (IOException e){
            e.printStackTrace();
            Log.e(TAG, "IOException");
            throw new RuntimeException(e);
        }
        finally {
            if(urlConn != null)
                urlConn.disconnect();
        }
    }

    /**
     * Reads an InputStream
     * @param in
     * @return
     */
    private JSONObject readStream(final InputStream in) throws UnsupportedEncodingException {

        final BufferedReader buffer = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        final StringBuilder sb = new StringBuilder();

        try {
            String line;
            while((line = buffer.readLine()) != null) {
                sb.append(line);
            }

        } catch(IOException e) {
            Log.d(TAG, e.getMessage());
        }
        finally {
            try {
                if(buffer != null) {
                    buffer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            return new JSONObject(sb.toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
