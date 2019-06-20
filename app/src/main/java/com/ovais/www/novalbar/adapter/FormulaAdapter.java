package com.ovais.www.novalbar.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ovais.www.novalbar.FormulaFirestore;
import com.ovais.www.novalbar.OrdersFirestore;
import com.ovais.www.novalbar.R;
import com.ovais.www.novalbar.activities.FormulaActivity;

public class FormulaAdapter extends FirestoreRecyclerAdapter<FormulaFirestore, FormulaAdapter.FormulaHolder> {

    private OnItemClickListener listener;
    private Context context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FormulaActivity fa = new FormulaActivity();
    String id;



    public FormulaAdapter(@NonNull FirestoreRecyclerOptions<FormulaFirestore> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FormulaHolder holder, int position, @NonNull FormulaFirestore model) {

        holder.nameTV.setText(model.getBarName());
        String switchState = model.getPrivacy();

        if (switchState.equals("publish"))
        {
            holder.publicSwitch.setChecked(true);
        }

        else
        {
            holder.publicSwitch.setChecked(false);
        }
    }

    @NonNull
    @Override
    public FormulaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_client_formula_single_row, viewGroup, false);
        return new FormulaHolder(view);
    }

    class FormulaHolder extends RecyclerView.ViewHolder{

        TextView nameTV;
        Switch publicSwitch;


        public FormulaHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.txt_search_custom_search_row);
            publicSwitch = itemView.findViewById(R.id.switch_formula_type_search_custom_row);

            publicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                    SharedPreferences sharedPreferences = context.getSharedPreferences("id" , 0);
//                    id = sharedPreferences.getString("id", "null");
//                    Log.d("Address Activity", "onCreate: GOT ID: " + id);

                    if (isChecked)
                    {
                        db.collection("recipe").document().update("privacy", "public");

                    }
                    else
                    {
                        db.collection("recipe").document().update("privacy", "private");
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION && listener != null)
                            listener.onItemClick(getSnapshots().getSnapshot(position),position);
                        {
                        }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshots, int position);

    }
    public void setOnItemClickListener (OnItemClickListener listener){
        this.listener = listener;
    }
}