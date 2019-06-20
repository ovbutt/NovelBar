package com.ovais.www.novalbar.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.ovais.www.novalbar.OrdersFirestore;
import com.ovais.www.novalbar.R;
import com.ovais.www.novalbar.activities.MyOrderClientDetailActivity;

public class OrderAdapter extends FirestoreRecyclerAdapter<OrdersFirestore, OrderAdapter.OrderHolder> {

    private OnItemClickListener listener;

    public OrderAdapter(@NonNull FirestoreRecyclerOptions<OrdersFirestore> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderHolder holder, int position, @NonNull OrdersFirestore model) {
        holder.nameTV.setText(model.getBarName());
        holder.dateTV.setText(model.getStatus());
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_custom_row, viewGroup, false);
        return new OrderHolder(view);
    }

    class OrderHolder extends RecyclerView.ViewHolder{

        TextView nameTV;
        TextView dateTV;


        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.txt_search_custom_search_row);
            dateTV = itemView.findViewById(R.id.txt_formula_type_search_custom_row);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION && listener != null)
                        {
                            listener.onItemClick(getSnapshots().getSnapshot(position),position);
                        }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick (DocumentSnapshot documentSnapshots, int position);

    }
    public void setOnItemClickListener (OnItemClickListener listener){
        this.listener = listener;
    }
}