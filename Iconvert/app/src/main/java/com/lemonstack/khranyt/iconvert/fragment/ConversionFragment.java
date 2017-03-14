package com.lemonstack.khranyt.iconvert.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;

import com.lemonstack.khranyt.iconvert.R;
import com.lemonstack.khranyt.iconvert.data.CurrencyContract;
import com.lemonstack.khranyt.iconvert.fragment.dialog.CurrenciesDialogFragment;
import com.lemonstack.khranyt.iconvert.model.Currency;

/**
 * Created by khranyt on 24/10/15.
 */
public class ConversionFragment extends Fragment
    implements CurrenciesDialogFragment.OnDialogFragmentListener {

    private final String TAG = ConversionFragment.class.getSimpleName();

    static private final String BASE_URL = "https://openexchangerates.org/api/";
    static private final String APP_ID = "8c01fe27397241a78baf9ee8b72f6a37";

    static private final String END_POINT_LATEST = BASE_URL + "latest.json?app_id=" + APP_ID;
    static private final String END_POINT_FULL_NAME = BASE_URL + "currencies.json?app_id=" + APP_ID;

    static private final String DISCLAIMER_KEY = "disclaimer";
    static private final String LICENSE_KEY = "license";
    static private final String TIMESTAMP_KEY = "timestamp";
    static private final String BASE_KEY = "base";
    static private final String RATES_KEY = "ratesMap";

    @Override
    public void onItemClick(View view, Currency currency) {
        Toast.makeText(getActivity(), currency.toString() + " selected!", Toast.LENGTH_LONG).show();

        if(isCurrencyFrom()) {
            codeFromTextView.setText(currency.getName());
            labelFromTextView.setText(currency.getFullname());
        } else {
            codeToTextView.setText(currency.getName());
            labelToTextView.setText(currency.getFullname());
        }
    }

    private static final int REQUEST_CODE = 100;
    // variables on the left side
    private RelativeLayout currencyFrom;
    private EditText mLeftEditText;

    private TextView codeFromTextView;
    private TextView labelFromTextView;
    private TextView codeToTextView;
    private TextView labelToTextView;

    // variables on the right side
    private RelativeLayout currencyTo;
    private TextView mResult;

    private TextView mDisplayRateTv;

    // Buttons
    private Button mResetBtn;
    private Button mConvertBtn;

    //private TextView mCurrencies;

    private String mLeftState = "";
    private String mRightState = "";

    private boolean from = false;
    private boolean to = false;

    JSONObject currenciesObj = null;

    Resources mRes;
    ContentResolver mResolver;

    double mLeftRate;
    double mRightRate;
    private View conversionView;

    private void ClickedOnCurrencyFrom() {
        from = true;
        to = false;
        Log.d(TAG, "clicked on left");
    }

    private void ClickedOnCurrencyTo() {
        from = false;
        to = true;
        Log.d(TAG, "clicked on right");
    }

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

    public static ConversionFragment newInstance() {
        return newInstance("");
    }

    public static ConversionFragment newInstance(final String param) {
        final ConversionFragment fragment = new ConversionFragment();
        Bundle args = new Bundle();
        args.putString("PARAM_1", param);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() method");
        if(getArguments() != null) {
            final String param = getArguments().getString("PARAM_1");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_conversion, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach --> " + context.toString());

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Inside onViewCreated");

        mConvertBtn = (Button) view.findViewById(R.id.convert_btn);
        //ImageView mSwapBtn = (ImageView) getActivity().findViewById(R.id.swap_iv);
        //mResetBtn = (Button) getActivity().findViewById(R.id.reset_btn);

        mLeftEditText = (EditText) view.findViewById(R.id.montant);
       // mLeftSpinner = (Spinner) getActivity().findViewById(R.id.left_text_view);

       // mRightSpinner = (Spinner) getActivity().findViewById(R.id.right_text_view);
        //mResult = (TextView) getActivity().findViewById(R.id.right_text_view_result);

        mDisplayRateTv = (TextView) view.findViewById(R.id.rateDisplay);
        //mCurrencies = (TextView) getActivity().findViewById(R.id.currencies);

        mRes = getResources();
        mResolver = getActivity().getContentResolver();

        codeFromTextView = (TextView) view.findViewById(R.id.code_currency_from);
        labelFromTextView = (TextView) view.findViewById(R.id.label_currency_from);
        codeToTextView = (TextView) view.findViewById(R.id.code_currency_to);
        labelToTextView = (TextView) view.findViewById(R.id.label_currency_to);

        currencyFrom = (RelativeLayout) view.findViewById(R.id.currency_from_rl);
        currencyTo = (RelativeLayout) view.findViewById(R.id.currency_to_rl);
        // initialize up and down state
        mLeftState =  mRes.getString(R.string.euro_text_view);
        mRightState = mRes.getString(R.string.usd_currency_text_view);

        //addListenerOnSpinners();
        addListenerOnButtons();

        currencyFrom.setOnClickListener(onRelativeLayoutListener());
        currencyTo.setOnClickListener(onRelativeLayoutListener());
    }

    private View.OnClickListener onRelativeLayoutListener() {
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final int id = v.getId();
                switch (id) {
                    case R.id.currency_from_rl:
                        Toast.makeText(getContext(), String.valueOf(id), Toast.LENGTH_LONG).show();
                        ClickedOnCurrencyFrom();
                        showDialog();
                        break;
                    case R.id.currency_to_rl:
                        Toast.makeText(getContext(), String.valueOf(id), Toast.LENGTH_LONG).show();
                        ClickedOnCurrencyTo();
                        showDialog();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    public boolean isCurrencyFrom() {
        Log.d(TAG, "FROM value : " + from + " and TO value : " + to);
        return from;
    }

    public boolean isCurrencyTo() {
        return to;
    }

    private void setTextView() {

    }

    private void showDialog() {
        final CurrenciesDialogFragment dialogFragment = CurrenciesDialogFragment.newInstance();
        final String tag = CurrenciesDialogFragment.class.getName();
        dialogFragment.setTargetFragment(this, REQUEST_CODE);
        dialogFragment.show(getActivity().getSupportFragmentManager(), tag);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "Inside onActivityCreated");
    }

    /*private void addListenerOnSpinners() {
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
    }*/

    private void addListenerOnButtons() {

        mConvertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get text view text of the left side
                String leftStr = String.valueOf(mLeftEditText.getText());

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
    }
}