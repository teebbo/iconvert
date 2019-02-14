package com.kimboofactory.iconvert.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.common.BaseFragment;
import com.kimboofactory.iconvert.fragment.dialog.DialogUi;
import com.kimboofactory.iconvert.persistence.model.CurrencyData;
import com.kimboofactory.iconvert.ui.main.FavoritePresenter;
import com.kimboofactory.iconvert.ui.main.FavoritesAdapter;

import java.util.LinkedList;

import butterknife.BindView;

/**
 * Created by khranyt on 24/10/15.
 */
public class ConversionFragment extends BaseFragment
    implements DialogUi.DialogUiListener {

    private static final String TAG = "ConversionFragment";
    private static final int REQUEST_CODE = 100;

    // variables on the left side
    //@BindView(R.id.edit_text_amount)
    TextInputEditText amountEditText;

    //@BindView(R.id.currency_from)
    RelativeLayout currencyFrom;
    //@BindView(R.id.currency_to)
    RelativeLayout currencyTo;

    // variables on the right side
    //@BindView(R.id.text_view_result)
    TextView convertedAmount;

    private TextView mDisplayRateTv;

    // Buttons
    //@BindView(R.id.convert_btn)
    Button mConvertBtn;

    @BindView(R.id.rl_currency)
    RelativeLayout currencyRL;
    @BindView(R.id.tv_update_date)
    TextView updateDateTV;
    @BindView(R.id.et_amount)
    EditText amountET;
    @BindView(R.id.lv_favorites)
    ListView favoritesCurrenciesLV;

    private FavoritesAdapter mAdapter;


    private boolean isSource = false;
    private boolean isDestination = false;

    private FavoritePresenter presenter;

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
        return TAG;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.presenter = new FavoritePresenter();
    }

    @Override
    public void onInitialize() {
        //currencyFrom.setOnClickListener(this::onClick);
        //currencyTo.setOnClickListener(this::onClick);
        //mConvertBtn.setOnClickListener(this::onClick);

        mAdapter = new FavoritesAdapter(getActivity(), new LinkedList<>());
        favoritesCurrenciesLV.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.presenter = null;
    }

    public static ConversionFragment newInstance() {
        final ConversionFragment fragment = new ConversionFragment();
        return fragment;
    }

    private void onClick(View v) {
        final int id = v.getId();
        /*switch (id) {
            case R.id.currency_from:
                Toast.makeText(getContext(), String.valueOf(id), Toast.LENGTH_LONG).show();
                ClickedOnDeviseSource();
                showDialog();
                break;
            case R.id.currency_to:
                Toast.makeText(getContext(), String.valueOf(id), Toast.LENGTH_LONG).show();
                ClickedOnDeviseDestination();
                showDialog();
                break;
            case R.id.convert_btn:
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
                    Double tauxChangeFinal = 0.0;//tauxChangeDestination / tauxChangeSource;

                    Double montantFinal = Double.valueOf(amount) * tauxChangeFinal;
                    convertedAmount.setText(montantFinal.toString());
                    mDisplayRateTv.setText("taux de change : " + tauxChangeFinal.toString());
                }
                break;
            default:
                break;
        }*/
    }


    public boolean isCurrencyFrom() {
        Log.d(TAG, "FROM value : " + isSource + " and TO value : " + isDestination);
        return isSource;
    }


    private void showDialog() {
        final DialogUi dialogFragment = DialogUi.newInstance();
        final String tag = DialogUi.class.getSimpleName();
        dialogFragment.setTargetFragment(ConversionFragment.this, REQUEST_CODE);
        dialogFragment.show(getParentActivity().getSupportFragmentManager(), tag);
    }

    @Override
    public void onDialogItemClicked(View view, CurrencyData currency) {
        Toast.makeText(getActivity(), currency.toString() + " selected!", Toast.LENGTH_LONG).show();
        if(isCurrencyFrom()) {
            TextView from = currencyFrom.findViewById(R.id.text_view_code);
            from.setText(currency.getCode() + " - " + currency.getLibelle());
        } else {
            TextView to = currencyTo.findViewById(R.id.text_view_code);
            to.setText(currency.getCode() + " - " + currency.getLibelle());
        }
    }

}