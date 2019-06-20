package com.ovais.www.novalbar.testFragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ovais.www.novalbar.R;
import com.ovais.www.novalbar.adapter.RecycleVerticalAdapterFrag1;
import com.ovais.www.novalbar.adapter.SectionsPagerAdapter;

public class Frag1 extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SectionsPagerAdapter sectionsPagerAdapter;

    private String []bulk = {"Oatmeal","Wheat Germ","Corn Flakes","Rice Cereal",};

    public Frag1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag1, container, false);

        // recycler View Code

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_frag1);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecycleVerticalAdapterFrag1(getContext(), bulk);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        return view;
    }
}
