package com.ovais.www.novalbar.testFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ovais.www.novalbar.R;
import com.ovais.www.novalbar.adapter.RecycleVerticalAdapterFrag1;
import com.ovais.www.novalbar.adapter.RecycleVerticalAdapterFrag3;
import com.ovais.www.novalbar.adapter.SectionsPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class Frag3 extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SectionsPagerAdapter sectionsPagerAdapter;

    private String []bulk = {"Oatmeal","Wheat Germ","Corn Flakes","Rice Cereals",};
    private String []protein = {"Soy","Whey Powder","Spirulina",};
    private String []vitamins = {"Vitamin B,C,D Complex",};
    private String []fiber = {"Soluble Fiber",};
    private String []veggies = {"Beans","kale","Carrots","Beets",};
    private String []nuts = {"Pecans","Cashew","Pine","Peanuts","Walnuts", "Almonds",};
    private String []driedfruits = {"Cranberries","Apricots","Banana Chips","Dates",};

    public Frag3() {
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

        mAdapter = new RecycleVerticalAdapterFrag3(getContext(), vitamins);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        return view;
    }

}
