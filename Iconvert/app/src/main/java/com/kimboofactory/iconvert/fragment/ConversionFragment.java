package com.kimboofactory.iconvert.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.dao.tauxchange.TauxChangeDao;
import com.kimboofactory.iconvert.dao.tauxchange.TauxChangeDaoImpl;
import com.kimboofactory.iconvert.fragment.dialog.DeviseDialogFragment;
import com.kimboofactory.iconvert.model.Devise;
import com.kimboofactory.iconvert.model.TauxChange;

import androidx.annotation.Nullable;

/**
 * Created by khranyt on 24/10/15.
 */
public class ConversionFragment extends AbstractFragment
    implements DeviseDialogFragment.OnDialogFragmentListener {

    private static final String TAG = "ConversionFragment";
    private static final int REQUEST_CODE = 100;

    // variables on the left side
    private EditText amountEditText;


    private TextView sourceTextView;
    private TextView targetTextView;

    // variables on the right side
    private TextView convertedAmount;

    private TextView mDisplayRateTv;

    // Buttons
    private Button mConvertBtn;

    private boolean isSource = false;
    private boolean isDestination = false;


    private Double tauxChangeSource;
    private Double tauxChangeDestination;

    private TauxChangeDao tauxChangeDao;

    private void ClickedOnDeviseSource() {
        isSource = true;
        isDestination = false;
        Log.d(TAG, "clicked on left");
    }

    private void ClickedOnDeviseDestination() {
        isSource = false;
        isDestination = true;
        Log.d(TAG, "clicked on right");
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_convert;
    }

    @Override
    protected String getDebugTag() {
        return TAG;
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mConvertBtn = view.findViewById(R.id.convert_btn);

        amountEditText = view.findViewById(R.id.et_amount);
        convertedAmount = view.findViewById(R.id.tv_result_amount);

        mDisplayRateTv = view.findViewById(R.id.rateDisplay);

        //codeSourceTextView = view.findViewById(R.id.code_devise_source);
        sourceTextView = view.findViewById(R.id.tv_source_currency);
        //codeDestinationTextView = view.findViewById(R.id.code_devise_destination);
        targetTextView = view.findViewById(R.id.tv_target_currency);

        //currencyFrom = view.findViewById(R.id.rl_devise_source);
        //deviseDestination = view.findViewById(R.id.rl_devise_destination);

        //addListenerOnSpinners();
        addListenerOnButtons();

        sourceTextView.setOnClickListener(onRelativeLayoutListener());
        targetTextView.setOnClickListener(onRelativeLayoutListener());

        tauxChangeDao = new TauxChangeDaoImpl(getContext());
    }

    private View.OnClickListener onRelativeLayoutListener() {
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final int id = v.getId();
                switch (id) {
                    case R.id.tv_source_currency:
                        Toast.makeText(getContext(), String.valueOf(id), Toast.LENGTH_LONG).show();
                        ClickedOnDeviseSource();
                        showDialog();
                        break;
                    case R.id.tv_target_currency:
                        Toast.makeText(getContext(), String.valueOf(id), Toast.LENGTH_LONG).show();
                        ClickedOnDeviseDestination();
                        showDialog();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    public boolean isCurrencyFrom() {
        Log.d(TAG, "FROM value : " + isSource + " and TO value : " + isDestination);
        return isSource;
    }


    private void showDialog() {
        final DeviseDialogFragment dialogFragment = DeviseDialogFragment.newInstance();
        final String tag = DeviseDialogFragment.class.getName();
        dialogFragment.setTargetFragment(this, REQUEST_CODE);
        dialogFragment.show(getActivity().getSupportFragmentManager(), tag);
    }

    private void addListenerOnButtons() {

        mConvertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get the user input
                final String amount = amountEditText.getText().toString();

                // check if user input is empty
                if (amount.equals("") || amount.isEmpty()) {
                    if (!convertedAmount.getText().toString().isEmpty()) {
                        convertedAmount.setText("");
                    }
                    Toast.makeText(getActivity().getApplication(), amount + " field is required", Toast.LENGTH_LONG).show();
                } else {
                    // compute the rate relative isDestination left and right side
                    Double tauxChangeFinal = tauxChangeDestination / tauxChangeSource;

                    Double montantFinal = Double.valueOf(amount) * tauxChangeFinal;
                    convertedAmount.setText(montantFinal.toString());
                    mDisplayRateTv.setText("taux de change : " + tauxChangeFinal.toString());
                }
            }
        });
    }

    @Override
    public void onItemClick(View view, Devise devise) {
        Toast.makeText(getActivity(), devise.toString() + " selected!", Toast.LENGTH_LONG).show();

        //final TauxChangeByCodeAsyncTask tauxChangeByCodeAsyncTask = new TauxChangeByCodeAsyncTask(getContext());

        //String code;

        if(isCurrencyFrom()) {
            sourceTextView.setText(devise.getCode() + " - " + devise.getLibelle());
        } else {
            targetTextView.setText(devise.getCode() + " - " + devise.getLibelle());
        }

        //tauxChangeByCodeAsyncTask.execute(code);
    }

    private class TauxChangeByCodeAsyncTask extends AsyncTask<String, Void, Double> {

        private Context context;
        private TauxChangeDao tauxChangeDao;

        public TauxChangeByCodeAsyncTask(Context context) {
            this.context = context;
            tauxChangeDao = new TauxChangeDaoImpl(context);
        }

        @Override
        protected Double doInBackground(String... params) {
            final String code = params[0];
            final TauxChange tauxChange = tauxChangeDao.getByCode(code);

            return tauxChange.getTauxChange();
        }

        @Override
        protected void onPostExecute(Double tauxChange) {
            if(isCurrencyFrom()) {
                tauxChangeSource = tauxChange;
            } else {
                tauxChangeDestination = tauxChange;
            }
        }
    }

}