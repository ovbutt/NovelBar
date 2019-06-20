package com.ovais.www.novalbar.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.ovais.www.novalbar.OrdersFirestore;
import com.ovais.www.novalbar.R;

public class MyOrderClientDetailActivity extends AppCompatActivity {

    private static final String TAG = "HTAG";
    private Toolbar toolbar;
    private CardView cardViewFormulaDetail;
    String id, barName;
    private TextView statusTV, addressTV, nameTV, cityTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_client_detail);
        toolbar = findViewById(R.id.toolbar_my_order_client_detail_activity);
        toolbar.setTitle("Order Detail");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final OrdersFirestore ordersFirestore = new OrdersFirestore();


        statusTV = findViewById(R.id.txt_status_my_order_client_detail_activity);
        addressTV = findViewById(R.id.txt_detail_address_my_order_client_detail_activity);
        nameTV = findViewById(R.id.txt_name_my_order_client_detail_activity);
        cityTV = findViewById(R.id.txt_city_my_order_client_detail_activity);

        cardViewFormulaDetail = findViewById(R.id.cardview_formula_detail_my_order_client_detail);
        cardViewFormulaDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //formulaDetailDialogue();
                Intent intent = new Intent(MyOrderClientDetailActivity.this, SearchDetailActivity.class);
                intent.putExtra("path", id);
                intent.putExtra("barName", barName);
                startActivity(intent);
                Log.d(TAG, "onClick: SetID: " + id);
            }
        });

        String path = getIntent().getExtras().getString("path", "path");
        Log.d(TAG, "onCreate: getExtra: " + path );

        db.document(path)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists())
                            {
                                OrdersFirestore ordersFirestore = documentSnapshot.toObject(OrdersFirestore.class);

                                statusTV.setText(ordersFirestore.getStatus());
                                nameTV.setText(ordersFirestore.getUserName());
                                addressTV.setText(ordersFirestore.getAddress());
                                cityTV.setText(ordersFirestore.getCity());
                                id = ordersFirestore.getId();
                                barName = ordersFirestore.getBarName();

                                Log.d(TAG, "onComplete: " + documentSnapshot.getData());
                            }
                            else 
                            {
                                Log.d(TAG, "onComplete: No Such Document");
                            }
                        }
                        else {
                            Log.d(TAG, "onComplete: Task Failed With Exception");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MyOrderClientDetailActivity.this, "Task Failed with exception: " + e.toString() , Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    private void formulaDetailDialogue() {
        final Dialog dialog = new Dialog(MyOrderClientDetailActivity.this);
        dialog.setContentView(R.layout.custom_formula_detail_scrollable_dialogue_box);

        ImageButton btnCross = dialog.findViewById(R.id.btn_iamge_cross_dialogue_custom_dialogue_box);
        btnCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}
