package com.kimboofactory.iconvert.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.dao.tauxchange.TauxChangeDao;
import com.kimboofactory.iconvert.dao.tauxchange.TauxChangeDaoImpl;
import com.kimboofactory.iconvert.fragment.dialog.DeviseDialogFragment;
import com.kimboofactory.iconvert.model.Devise;
import com.kimboofactory.iconvert.model.TauxChange;

/**
 * Created by khranyt on 24/10/15.
 */
public class ConversionFragment extends BaseFragment
    implements DeviseDialogFragment.OnDialogFragmentListener {

    private static final String TAG = "ConversionFragment";
    private static final int REQUEST_CODE = 100;

    // variables on the left side
    private RelativeLayout currencyFrom;
    private EditText mLeftEditText;

    private TextView codeSourceTextView;
    private TextView labelSourceTextView;
    private TextView codeDestinationTextView;
    private TextView labelDestinationTextView;

    // variables on the right side
    private RelativeLayout deviseDestination;
    private TextView montantDestination;

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
    public int getLayoutResId() {
        return R.layout.fragment_convert;
    }

    @Override
    public String getClassName() {
        return "ConversionFragment";
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
    public void initialize(View view) {

        mConvertBtn = view.findViewById(R.id.convert_btn);

        mLeftEditText = view.findViewById(R.id.montant);
        montantDestination = view.findViewById(R.id.montant_destination);

        mDisplayRateTv = view.findViewById(R.id.rateDisplay);

        codeSourceTextView = view.findViewById(R.id.code_devise_source);
        labelSourceTextView = view.findViewById(R.id.libelle_devise_source);
        codeDestinationTextView = view.findViewById(R.id.code_devise_destination);
        labelDestinationTextView = view.findViewById(R.id.libelle_devise_destination);

        currencyFrom = view.findViewById(R.id.rl_devise_source);
        deviseDestination = view.findViewById(R.id.rl_devise_destination);

        //addListenerOnSpinners();
        addListenerOnButtons();

        currencyFrom.setOnClickListener(onRelativeLayoutListener());
        deviseDestination.setOnClickListener(onRelativeLayoutListener());

        tauxChangeDao = new TauxChangeDaoImpl(getContext());
    }

    private View.OnClickListener onRelativeLayoutListener() {
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final int id = v.getId();
                switch (id) {
                    case R.id.rl_devise_source:
                        Toast.makeText(getContext(), String.valueOf(id), Toast.LENGTH_LONG).show();
                        ClickedOnDeviseSource();
                        showDialog();
                        break;
                    case R.id.rl_devise_destination:
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
                final String amount = mLeftEditText.getText().toString();

                // check if user input is empty
                if (amount.equals("") || amount.isEmpty()) {
                    if (!montantDestination.getText().toString().isEmpty()) {
                        montantDestination.setText("");
                    }
                    Toast.makeText(getActivity().getApplication(), amount + " field is required", Toast.LENGTH_LONG).show();
                } else {
                    // compute the rate relative isDestination left and right side
                    Double tauxChangeFinal = tauxChangeDestination / tauxChangeSource;

                    Double montantFinal = Double.valueOf(amount) * tauxChangeFinal;
                    montantDestination.setText(montantFinal.toString());
                    mDisplayRateTv.setText("taux de change : " + tauxChangeFinal.toString());
                }
            }
        });
    }

    @Override
    public void onItemClick(View view, Devise devise) {
        Toast.makeText(getActivity(), devise.toString() + " selected!", Toast.LENGTH_LONG).show();

        final TauxChangeByCodeAsyncTask tauxChangeByCodeAsyncTask = new TauxChangeByCodeAsyncTask(getContext());

        String code;

        if(isCurrencyFrom()) {
            codeSourceTextView.setText(devise.getCode());
            labelSourceTextView.setText(devise.getLibelle());
            code = codeSourceTextView.getText().toString();

        } else {
            codeDestinationTextView.setText(devise.getCode());
            labelDestinationTextView.setText(devise.getLibelle());
            code = codeDestinationTextView.getText().toString();
        }

        tauxChangeByCodeAsyncTask.execute(code);
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