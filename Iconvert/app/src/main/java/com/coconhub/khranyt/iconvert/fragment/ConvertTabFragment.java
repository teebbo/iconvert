package com.coconhub.khranyt.iconvert.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DecimalFormat;

import com.coconhub.khranyt.iconvert.R;
import com.coconhub.khranyt.iconvert.data.CurrencyContract;

/**
 * Created by khranyt on 24/10/15.
 */
public class ConvertTabFragment extends Fragment {
    // variables on the left side
    private Spinner mLeftSpinner;
    private EditText mLeftEditText;


    // variables on the right side
    private Spinner mRightSpinner;
    private TextView mResult;

    private TextView mDisplayRateTv;

    // Buttons
    private Button mResetBtn;
    private Button mConvertBtn;

    //private TextView mCurrencies;

    String mLeftState = "";
    String mRightState = "";

    boolean fromCurrency = false;
    boolean toCurrency = false;

    private final String TAG = ConvertTabFragment.class.getSimpleName();

    static private final String BASE_URL = "https://openexchangerates.org/api/";
    static private final String APP_ID = "8c01fe27397241a78baf9ee8b72f6a37";

    static private final String END_POINT_LATEST = BASE_URL + "latest.json?app_id=" + APP_ID;
    static private final String END_POINT_FULL_NAME = BASE_URL + "currencies.json?app_id=" + APP_ID;

    static private final String DISCLAIMER_KEY = "disclaimer";
    static private final String LICENSE_KEY = "license";
    static private final String TIMESTAMP_KEY = "timestamp";
    static private final String BASE_KEY = "base";
    static private final String RATES_KEY = "ratesMap";

    JSONObject currenciesObj = null;

    Resources mRes;
    ContentResolver mResolver;

    double mLeftRate;
    double mRightRate;

    private double queryCurrencyTable(Uri uri){
        Log.wtf(TAG, "URI : -> " + CurrencyContract.Currency.getCurrencyNameFromURI(uri));

        double rate = 0.0;

        String[] projection  = {CurrencyContract.Currency.COLUMN_RATE};
        String selection = String .format("%s.%s = ?",
                CurrencyContract.Currency.TABLE_CURRENCY,
                CurrencyContract.Currency.COLUMN_NAME);

       // Log.wtf(TAG, "getNameFromURI : -> " + CurrencyContract.Currency.getCurrencyNameFromURI(uri));

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
       // Log.wtf(TAG, "Rate : -->>" + rate);
        return rate;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_convert_tab, container, false);
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

       mConvertBtn = (Button) getActivity().findViewById(R.id.convert_btn);
        //ImageView mSwapBtn = (ImageView) getActivity().findViewById(R.id.swap_iv);
        //mResetBtn = (Button) getActivity().findViewById(R.id.reset_btn);

        mLeftEditText = (EditText) getActivity().findViewById(R.id.left_edit_text);
        mLeftSpinner = (Spinner) getActivity().findViewById(R.id.left_text_view);

       mRightSpinner = (Spinner) getActivity().findViewById(R.id.right_text_view);
        mResult = (TextView) getActivity().findViewById(R.id.right_text_view_result);

       mDisplayRateTv = (TextView) getActivity().findViewById(R.id.rateDisplay);
       //mCurrencies = (TextView) getActivity().findViewById(R.id.currencies);

        mRes = getResources();
        mResolver = getActivity().getContentResolver();

        // initialize up and down state
        mLeftState =  mRes.getString(R.string.euro_text_view);
        mRightState = mRes.getString(R.string.usd_currency_text_view);

        addListenerOnSpinners();
        addListenerOnButtons();
    }

    private void addListenerOnSpinners() {
        mLeftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Uri leftURI = CurrencyContract.Currency.buildCurrencyRateURI(selectedItem);
                mLeftRate = queryCurrencyTable(leftURI);
                //Log.w(TAG, "Letf rate : " + mLeftRate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mRightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Uri rightURI = CurrencyContract.Currency.buildCurrencyRateURI(selectedItem);
                mRightRate = queryCurrencyTable(rightURI);
                //Log.w(TAG, "right rate : " + mRightRate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void addListenerOnButtons() {

        mConvertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get text view text of the left side
                String leftStr = String.valueOf(mLeftSpinner.getSelectedItem());

                // get text view text of the right side
                //String rightStr = String.valueOf(mRightSpinner.getSelectedItem());

                // get the user input
                String amount = mLeftEditText.getText().toString();

                // check if user input is empty
                if (amount.equals("") || amount.isEmpty()) {
                    if (!mResult.getText().toString().isEmpty()) {
                        mResult.setText("");
                    }
                    Toast.makeText(getActivity().getApplication(), leftStr + " field is required", Toast.LENGTH_LONG).show();
                } else {
                    // compute the rate relative to left and right side
                    double rate = mRightRate / mLeftRate;

                    double finalAmount = Double.valueOf(amount) * rate;
                    mResult.setText(String.valueOf(new DecimalFormat("#.###").format(finalAmount)));
                    mDisplayRateTv.setText("Rate : " + String.valueOf(new DecimalFormat("#.####").format(rate)));
                }
            }
        });

       /* mResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeftEditText.setText("");
                mResult.setText("");
                mDisplayRateTv.setText("");
            }
        });*/

                    /*mSwapBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String fromStr = mLeftSpinner.getSelectedItem().toString();
                    String toStr = mRightSpinner.getSelectedItem().toString();

                    if (mLeftState.equals(fromStr) &
                            mRightState.equals(toStr)) {

                        mLeftSpinner.setText(toStr);
                        mRightSpinner.setText(fromStr);

                        mLeftState = toStr;
                        mRightState = fromStr;
                    } else {

                        mLeftSpinner.setText(fromStr);
                        mRightSpinner.setText(toStr);

                        mLeftState = fromStr;
                        mRightState = toStr;
                    }
                }
            });*/
    }
}