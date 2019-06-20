package com.ovais.www.novalbar.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ovais.www.novalbar.R;
//import com.ovais.www.novalbar.testFragments.IngredientsQuantitiyHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class SearchDetailAdapter extends RecyclerView.Adapter<SearchDetailAdapter.MyViewHolder> {

//
   private Context ctx;
    private int listSize = 0;
    private String [] ingredientData;
    private String [] quantityData;

    List<String> ingridientsString, quantityString;

    public SearchDetailAdapter() {
    }

    public SearchDetailAdapter(Context ctx, List<String> ingridientsString, List<String> quantityString, int listSize) {
        this.ctx = ctx;
        this.quantityString = quantityString;
        this.ingridientsString = ingridientsString;
        this.listSize = listSize;

//        Log.d("SearchDetailAdapter", "SearchDetailAdapter: " + data[0] + "\n" + quantityData[0]);
    }


    public SearchDetailAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_detail_search_single, viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
//        ingredientData = ingridientsString.split(",");
//        quantityData = quantityString.split(",");
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.txtNutration1.setText(ingridientsString.get(i));
        myViewHolder.tvQuantity.setText(quantityString.get(i));
    }

    @Override
    public int getItemCount() {
        return listSize;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView txtNutration1, tvQuantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNutration1 = itemView.findViewById(R.id.txt_nutration_name_custom_drtail_search_single1);
            tvQuantity = itemView.findViewById(R.id.quantityTextView);
        }
    }
}