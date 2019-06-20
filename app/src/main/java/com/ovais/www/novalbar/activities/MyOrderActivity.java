package com.ovais.www.novalbar.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.ovais.www.novalbar.OrdersFirestore;
import com.ovais.www.novalbar.R;
import com.ovais.www.novalbar.adapter.OrderAdapter;
import com.ovais.www.novalbar.shareprefer.SharePrefer;

public class MyOrderActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference reference = db.collection("orders");
    private OrderAdapter adapter;
    private String userId;
    private Toolbar toolbar;
    private SharePrefer sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        toolbar = findViewById(R.id.toolbar_my_order);
        toolbar.setTitle("My Orders");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setUpRecyclerView();

        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation_search_activity);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_order: {
                                break;
                            }
                            case R.id.action_search: {
                                startActivity(new Intent(MyOrderActivity.this, SearchActivity.class));
                                finish();
                                break;
                            }
                        }
                        return true;
                    }
                });
    }

    private void setUpRecyclerView() {

//        SharedPreferences sharedPreferences1 = this.getSharedPreferences("uid" , 0);
//        userId = sharedPreferences1.getString("uid", "null");

        sp = new SharePrefer(this);
        userId = sp.getUid();
        Log.d("MyOrderActivity", "onCreate: GOT UID: " + userId);

        Query query = reference.whereEqualTo("uid", userId);

        FirestoreRecyclerOptions<OrdersFirestore> options = new FirestoreRecyclerOptions.Builder<OrdersFirestore>()
                .setQuery(query, OrdersFirestore.class)
                .build();

        adapter = new OrderAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recyclerview_my_order_activity);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.startListening();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshots, int position) {
                OrdersFirestore ordersFirestore = documentSnapshots.toObject(OrdersFirestore.class);
                String id = documentSnapshots.getId();
                String path = documentSnapshots.getReference().getPath();
                documentSnapshots.getReference().getId();
               // Toast.makeText(MyOrderActivity.this, "Position: " + position + " , " + "ID: " + id , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MyOrderActivity.this, MyOrderClientDetailActivity.class);
                intent.putExtra("path", path);
                startActivity(intent);
            }
        });
        //adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}