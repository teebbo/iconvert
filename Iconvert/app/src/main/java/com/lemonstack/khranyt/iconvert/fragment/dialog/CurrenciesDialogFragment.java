package com.lemonstack.khranyt.iconvert.fragment.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lemonstack.khranyt.iconvert.R;
import com.lemonstack.khranyt.iconvert.RetrieveCurrencyTask;
import com.lemonstack.khranyt.iconvert.adapter.CurrenciesDialogListAdapter;
import com.lemonstack.khranyt.iconvert.model.Currency;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrenciesDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrenciesDialogFragment extends DialogFragment {
    private static final String TAG = CurrenciesDialogFragment.class.getName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String EMPTY_STRING = "";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnDialogFragmentListener listener;
    private CurrenciesDialogListAdapter adapter;

    public interface OnDialogFragmentListener {
        void onItemClick(View view, Currency currency);
    }

    public CurrenciesDialogFragment() {
        // Required empty public constructor
    }

    public static CurrenciesDialogFragment newInstance() {
       return newInstance(EMPTY_STRING, EMPTY_STRING);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CurrenciesDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrenciesDialogFragment newInstance(String param1, String param2) {
        CurrenciesDialogFragment fragment = new CurrenciesDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      /*  if(context instanceof OnDialogFragmentListener) {
            listener = (OnDialogFragmentListener) context;
        } else {
            throw new RuntimeException("OnDialogFragmentListener must be implemented.");
        }*/
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        final Fragment parentFragment = getTargetFragment();
        try {
            if (parentFragment instanceof OnDialogFragmentListener) {
                listener = (OnDialogFragmentListener) parentFragment;
            }
        } catch(ClassCastException e) {
            // The parent fragment doesn't implement the interface, throw exception
            throw new ClassCastException(parentFragment.toString()
                    + " must implement OnDialogFragmentListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_currencies_list_dialog, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final ListView currenciesListView = (ListView) view.findViewById(R.id.currencies_dialog_list_view);
        adapter = new CurrenciesDialogListAdapter(getActivity(), getData());
        currenciesListView.setAdapter(adapter);
        getDialog().setTitle("Currencies List");

        currenciesListView.setOnItemClickListener(onListViewItemClick());
    }

    private AdapterView.OnItemClickListener onListViewItemClick() {
        return new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Currency o = (Currency) parent.getItemAtPosition(position);
                Log.d(TAG, o.toString());
                listener.onItemClick(view, o);
                dismiss();
            }
        };
    }

    private List<Currency> getData() {
        return new ArrayList<Currency>(){{
           add(new Currency("XAF", "Central African CFA Franc BEAC"));
            add(new Currency("EUR", "Euro"));
            add(new Currency("USD", "US Dollar"));
            add(new Currency("JPY", "Japan Yen"));
            add(new Currency("AUD", "Australian Dollar"));
            add(new Currency("CAD", "Canadian Dollar"));
            add(new Currency("XOF", "CFA Franc"));
            add(new Currency("NGN", "Nigeria Naira"));
            add(new Currency("CNY", "Chinese Yuan"));
            add(new Currency("GBP", "Great Britain Pound"));
            add(new Currency("INR", "Indian Rupee"));
            add(new Currency("CHF", "Swiss Franc"));
            add(new Currency("SGD", "Singapore Dollar"));
            add(new Currency("ARS", "Argentine Peso"));
        }};
    }
}
