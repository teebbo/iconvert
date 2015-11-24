package fr.enst.igr201.kanmogne.iconvert;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DecimalFormat;

import fr.enst.igr201.kanmogne.iconvert.data.CurrencyContract;

/**
 * Created by joffrey on 24/10/15.
 */
public class ConvertTabFragment extends Fragment {
    // variables on the left side
    private TextView mLeftTextView;
    private EditText mLeftEditText;


    // variables on the right side
    private TextView mRightTextView;
    private TextView mResult;

    private TextView mDisplayRateTv;

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

        Button mConvertBtn = (Button) getActivity().findViewById(R.id.convert_btn);
        ImageView mSwapBtn = (ImageView) getActivity().findViewById(R.id.swap_iv);
        Button resetBtn = (Button) getActivity().findViewById(R.id.reset_btn);

        //Button mRateBtn = (Button) getActivity().findViewById(R.id.currencies_btn);
        mLeftEditText = (EditText) getActivity().findViewById(R.id.left_edit_text);
        mLeftTextView = (TextView) getActivity().findViewById(R.id.left_text_view);

        mRightTextView = (TextView) getActivity().findViewById(R.id.right_text_view);
        mResult = (TextView) getActivity().findViewById(R.id.right_text_view_result);

        mDisplayRateTv = (TextView) getActivity().findViewById(R.id.rateDisplay);
       // mCurrencies = (TextView) getActivity().findViewById(R.id.currencies);

        mRes = getResources();
        mResolver = getActivity().getContentResolver();

        // initialize up and down state
        mLeftState =  mRes.getString(R.string.euro_text_view);
        mRightState = mRes.getString(R.string.usd_currency_text_view);

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
                    mResult.setText(String.valueOf(new DecimalFormat("#.###").format(finalAmount)));
                    mDisplayRateTv.setText("Rate : " + String.valueOf(rate));
                }
            }
        });

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

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeftEditText.setText("");
                mResult.setText("");
                mDisplayRateTv.setText("");
            }
        });

        /*if(mRateBtn != null) {
            mRateBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    RetrieveCurrencyTask retrieveCurrencyTask = new RetrieveCurrencyTask(getActivity().getApplicationContext());
                    retrieveCurrencyTask.execute(END_POINT_LATEST, END_POINT_FULL_NAME);
                }
            });
        } else
            Log.d(TAG, "rate btn is null");*/
    }
}