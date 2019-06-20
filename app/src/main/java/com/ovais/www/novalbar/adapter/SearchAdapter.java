package com.ovais.www.novalbar.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import com.ovais.www.novalbar.R;
import com.ovais.www.novalbar.SearchFirestore;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SearchAdapter extends FirestoreRecyclerAdapter<SearchFirestore, SearchAdapter.SearchHolder> {

    private OnItemClickListener listener;
    private List<String> barName = new ArrayList<>();
    private List<String> privacy = new ArrayList<>();

    public SearchAdapter(@NonNull FirestoreRecyclerOptions<SearchFirestore> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull SearchHolder holder, final int position, @NonNull SearchFirestore model) {
        barName.add(model.getBarName());
        privacy.add(model.getPrivacy());
        holder.nameTV.setText(barName.get(position));
       // holder.dateTV.setText(privacy.get(position));
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_custom_row, viewGroup, false);
        return new SearchHolder(view);
    }

    class SearchHolder extends RecyclerView.ViewHolder{

        TextView nameTV;
        TextView dateTV;


        public SearchHolder(@NonNull View itemView) {
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

    public void updateList (List<String> newList){

        barName = new ArrayList<>();
        barName.addAll(newList);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshots, int position);

    }
    public void setOnItemClickListener (OnItemClickListener listener){
        this.listener = listener;
    }
}