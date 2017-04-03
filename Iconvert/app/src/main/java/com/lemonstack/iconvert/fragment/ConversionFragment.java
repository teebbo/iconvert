package com.lemonstack.iconvert.fragment;

import android.content.Context;
import android.os.AsyncTask;
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

import com.lemonstack.iconvert.R;
import com.lemonstack.iconvert.asyntask.TauxChangeByCodeAsyncTask;
import com.lemonstack.iconvert.dao.tauxchange.TauxChangeDao;
import com.lemonstack.iconvert.dao.tauxchange.TauxChangeDaoImpl;
import com.lemonstack.iconvert.fragment.dialog.DeviseDialogFragment;
import com.lemonstack.iconvert.model.Devise;
import com.lemonstack.iconvert.model.TauxChange;

/**
 * Created by khranyt on 24/10/15.
 */
public class ConversionFragment extends Fragment
    implements DeviseDialogFragment.OnDialogFragmentListener {

    private final String TAG = ConversionFragment.class.getName();

    static private final String BASE_URL = "https://openexchangerates.org/api/";

    static private final String TIMESTAMP_KEY = "timestamp";
    static private final String BASE_KEY = "base";

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
        montantDestination = (TextView) getActivity().findViewById(R.id.montant_destination);

        mDisplayRateTv = (TextView) view.findViewById(R.id.rateDisplay);

        codeSourceTextView = (TextView) view.findViewById(R.id.code_devise_source);
        labelSourceTextView = (TextView) view.findViewById(R.id.libelle_devise_source);
        codeDestinationTextView = (TextView) view.findViewById(R.id.code_devise_destination);
        labelDestinationTextView = (TextView) view.findViewById(R.id.libelle_devise_destination);

        currencyFrom = (RelativeLayout) view.findViewById(R.id.rl_devise_source);
        deviseDestination = (RelativeLayout) view.findViewById(R.id.rl_devise_destination);

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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "Inside onActivityCreated");
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