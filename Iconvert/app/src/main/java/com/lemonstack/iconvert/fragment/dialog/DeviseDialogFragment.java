package com.lemonstack.iconvert.fragment.dialog;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lemonstack.iconvert.R;
import com.lemonstack.iconvert.adapter.DeviseDialogListAdapter;
import com.lemonstack.iconvert.dao.devise.DeviseDao;
import com.lemonstack.iconvert.dao.devise.DeviseDaoImpl;
import com.lemonstack.iconvert.model.Devise;
import com.lemonstack.iconvert.model.TauxChange;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeviseDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeviseDialogFragment extends DialogFragment {
    private static final String TAG = DeviseDialogFragment.class.getName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String EMPTY_STRING = "";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnDialogFragmentListener listener;
    private DeviseDialogListAdapter adapter;

    private DeviseDao deviseDao;

    public interface OnDialogFragmentListener {
        void onItemClick(View view, Devise devise);
    }

    public DeviseDialogFragment() {
        // Required empty public constructor
    }

    public static DeviseDialogFragment newInstance() {
       return newInstance(EMPTY_STRING, EMPTY_STRING);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeviseDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeviseDialogFragment newInstance(String param1, String param2) {
        DeviseDialogFragment fragment = new DeviseDialogFragment();
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
        deviseDao = new DeviseDaoImpl(context);
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
        final ListView devisesListView = (ListView) view.findViewById(R.id.devises_dialog_list_view);
        adapter = new DeviseDialogListAdapter(getActivity(), devises());
        devisesListView.setAdapter(adapter);
        getDialog().setTitle("Liste de devises");

        devisesListView.setOnItemClickListener(onListViewItemClick());
    }

    private AdapterView.OnItemClickListener onListViewItemClick() {
        return new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Devise o = (Devise) parent.getItemAtPosition(position);
                Log.d(TAG, o.toString());
                listener.onItemClick(view, o);
                dismiss();
            }
        };
    }

    /**
     * retourne la liste des devises
     * @return
     */
    private List<Devise> devises() {
        final List<Devise> devises = new ArrayList<>();
        final Cursor c = deviseDao.list();

        while (c.moveToNext()) {
            devises.add(new Devise(c.getLong(0), c.getString(1), c.getString(2)));
        }
        return devises;
    }
}
