package com.ovais.www.novalbar.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ovais.www.novalbar.R;
//import com.ovais.www.novalbar.testFragments.IngredientsQuantitiyHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class DetailOrderAdapter extends RecyclerView.Adapter<DetailOrderAdapter.MyViewHolder> {


    private Context ctx;
    private int listSize = 0;
    List<String> ingredients = new ArrayList<String>();
    List<String> quantity = new ArrayList<String>();

//    private String [] ingredients;
//    private String [] ingredients2;
//    public int [] quantity;
//    public int [] quantit2;

//    private RecycleVerticalAdapterFrag1 recycleVerticalAdapterFrag1 = new RecycleVerticalAdapterFrag1();
//    private IngredientsQuantitiyHelper ingredientsQuantitiyHelper = new IngredientsQuantitiyHelper();


    public DetailOrderAdapter(Context ctx, List<String> ingredients, List<String> quantity, int listSize) {
        this.ctx = ctx;
        this.ingredients=ingredients;
        this.quantity=quantity;
        this.listSize=listSize;
    }

    public DetailOrderAdapter(){
        EventBus.getDefault().register(this);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_detail_search_single, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
//        quantit2 = ingredientsQuantitiyHelper.getNumArray();
//        Log.d("Search detail adapter", "onCreateViewHolder: Values are: " + quantit2[0] + quantit2[1] + quantit2[2]);
//
//        ingredients = ingredientsQuantitiyHelper.getIngredients();
//        Log.d("Search Detail Adapter", "getQuantity: Values in Ings are: " + ingredients[0] + ingredients[1] + ingredients[2] + ingredients[3]);
//
//        arraySize = ingredients.length;
        //quantity = ingredientsQuantitiyHelper.getQuantity();
        //Log.d("Search Detail Adapter", "getQuantity: Values in adapter are: " + quantity[0] + quantity[1] + quantity[2] + quantity[3]);


//        for (int x=0; x<arraySize; x++)
//        {
//            if (quantity[x]>0)
//            {
//                arrayCount++;
//            }
//        }
//
//        for (int j=0; j<arraySize; j++)
//        {
//            if (quantity[j]>0)
//            {
//                for(int k=0; k<arrayCount; k++) {
//                    ingredients2[k] = ingredients[j];
//                }
//            }
//            else
//            {
//
//            }
//        }

        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.txtNutration1.setText(ingredients.get(i));
        myViewHolder.tvQuantity.setText(String.valueOf(quantity.get(i)));
    }

    @Override
    public int getItemCount() {
        return listSize;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView txtNutration1, tvQuantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //EventBus.getDefault().register(this);

            txtNutration1 = itemView.findViewById(R.id.txt_nutration_name_custom_drtail_search_single1);
            tvQuantity = itemView.findViewById(R.id.quantityTextView);
        }
    }

//    @Subscribe
//    public void onEvent(ArrayMessageEvent arrayMessageEvent) {
//        String customString = arrayMessageEvent.getCustomString();
//        Log.d("SearchDetailAdapter", "onEvent: Event values: " + customString);
//
//    }
}