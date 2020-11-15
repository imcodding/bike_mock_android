package com.mia.bikeproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.mia.bikeproject.models.BikeInfo;

import java.util.ArrayList;


public class BikeListFragment extends Fragment implements TextWatcher {

    final String TAG = "BikeFragment";
    EditText editSearch;
    BikeListAdapter adapter;

    ArrayList<BikeInfo> bikeList;
    public static BikeListFragment newInstance(ArrayList<BikeInfo> bikeList) {
        BikeListFragment fragment = new BikeListFragment();
        Bundle args = new Bundle();
        args.putSerializable("bikeList", bikeList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bikeList = (ArrayList<BikeInfo>) getArguments().getSerializable("bikeList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bike_list, container, false);

        RecyclerView rvBikeList = view.findViewById(R.id.rv_bike_list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvBikeList.setLayoutManager(manager);

        adapter = new BikeListAdapter(bikeList);
        rvBikeList.setAdapter(adapter);

        editSearch = view.findViewById(R.id.edit_search);
        editSearch.addTextChangedListener(this);
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        // 검색 동작
                        break;
                    default:
                        // 기본 엔터키 동작
                        return false;
                }
                return true;
            }
        });


        return view;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        adapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}