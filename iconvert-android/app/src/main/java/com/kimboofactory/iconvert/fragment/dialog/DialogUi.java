package com.kimboofactory.iconvert.fragment.dialog;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kimboofactory.iconvert.R;
import com.kimboofactory.iconvert.adapter.DeviseAdapter;
import com.kimboofactory.iconvert.persistence.model.CurrencyData;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DialogUi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DialogUi extends DialogFragment {

    private static final String TAG = DialogUi.class.getName();
    private DialogUiListener listener;
    private DeviseAdapter adapter;

    @BindView(R.id.list_view_currencies)
    ListView mCurrenciesListView;

    public interface DialogUiListener {
        void onDialogItemClicked(View view, CurrencyData currency);
    }

    public DialogUi() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DialogUi.
     */
    public static DialogUi newInstance() {
        final DialogUi fragment = new DialogUi();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      /*  if(context instanceof DialogUiListener) {
            listener = (DialogUiListener) context;
        } else {
            throw new RuntimeException("DialogUiListener must be implemented.");
        }*/
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Fragment parentFragment = getTargetFragment();
        try {
            if (parentFragment instanceof DialogUiListener) {
                listener = (DialogUiListener) parentFragment;
            }
        } catch(ClassCastException e) {
            // The parent fragment doesn't implement the interface, throw exception
            throw new ClassCastException(parentFragment.toString()
                    + " must implement DialogUiListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_currencies_list_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        ButterKnife.bind(this, view);

        adapter = new DeviseAdapter(getActivity(), new ArrayList<>());
        mCurrenciesListView.setAdapter(adapter);
        getDialog().setTitle("Liste de devises");
        mCurrenciesListView.setOnItemClickListener(this::onItemClick);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //final Cursor c = (Cursor) parent.getItemAtPosition(position);
        //final CurrencyData o = deviseDao.createEntity(c);
        //Log.d(TAG, o.toString());
        //listener.onDialogItemClicked(view, o);
        CurrencyData currency = (CurrencyData) this.adapter.getItem(position);
        this.listener.onDialogItemClicked(view, currency);
        dismiss();
    }


}
