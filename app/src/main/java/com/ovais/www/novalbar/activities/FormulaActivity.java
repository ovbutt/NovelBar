package com.ovais.www.novalbar.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.ovais.www.novalbar.FormulaFirestore;
import com.ovais.www.novalbar.R;
import com.ovais.www.novalbar.SQLite.IngredientData;
import com.ovais.www.novalbar.SQLite.SQLiteHelper;
import com.ovais.www.novalbar.adapter.FormulaAdapter;
import com.ovais.www.novalbar.shareprefer.SharePrefer;

import java.util.List;

public class FormulaActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference reference = db.collection("recipe");
    private FormulaAdapter adapter;
    private String userId;
    private Toolbar toolbar;
    private Switch privacySwitch;
    public String id, barName;
    private SharePrefer sp;

    private SQLiteHelper sqLiteHelper = new SQLiteHelper(this);

    //private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_formula);
        toolbar = findViewById(R.id.toolbar_client_formula_activity);
        toolbar.setTitle("Formula");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        privacySwitch = findViewById(R.id.switch_formula_type_search_custom_row);
//        //textView = findViewById(R.id.dbText);
//
//        Cursor res = sqLiteHelper.getDataToPost();
//        if (res.getCount() == 0)
//        {
//            Log.d("MainActivity", "onClick: NoCount of Cursor");
//        }
//        else
//        {
//            Log.d("MainActivity", "onClick: Count of Cursor: " + res.getCount());
//            StringBuffer stringBuffer = new StringBuffer();
//            while (res.moveToNext()){
//                stringBuffer.append("bitternessFlag: " + res.getString(1)+ "\n");
//                stringBuffer.append("calories: " + res.getString(2)+ "\n");
//                stringBuffer.append("carbCalorie: " + res.getString(3)+ "\n");
//                stringBuffer.append("category: " + res.getString(4)+ "\n");
//                stringBuffer.append("fatCalorie: " + res.getString(5)+ "\n");
//                stringBuffer.append("name: " + res.getString(6)+ "\n");
//                stringBuffer.append("portionFlag: " + res.getString(7)+ "\n");
//                stringBuffer.append("portionSize: " + res.getString(8)+ "\n");
//                stringBuffer.append("proteinCalorie: " + res.getString(9)+ "\n");
//                stringBuffer.append("quantity: " + res.getString(10)+ "\n");
//                stringBuffer.append("rda: " + res.getString(11)+ "\n");
//                stringBuffer.append("servingSize: " + res.getString(12)+ "\n");
//                stringBuffer.append("unhealthyFlag: " + res.getString(13)+ "\n\n");
//            }
//            //textView.setMovementMethod(new ScrollingMovementMethod());
//           // textView.setText(stringBuffer.toString());
//        }

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {

        sp = new SharePrefer(this);
        userId = sp.getUid();
        Log.d("MyOrderActivity", "onCreate: GOT UID: " + userId);
//        SharedPreferences sharedPreferences1 = this.getSharedPreferences("uid" , 0);
//        userId = sharedPreferences1.getString("uid", "null");
//        Log.d("MyOrderActivity", "onCreate: GOT UID: " + userId);

        Query query = reference.whereEqualTo("uid",userId);

        FirestoreRecyclerOptions<FormulaFirestore> options = new FirestoreRecyclerOptions.Builder<FormulaFirestore>()
                .setQuery(query, FormulaFirestore.class)
                .build();

        adapter = new FormulaAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recyclerview_client_formula_activity);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new FormulaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshots, int position) {
                FormulaFirestore FormulaFirestore = documentSnapshots.toObject(FormulaFirestore.class);
                id = documentSnapshots.getId();
                barName = FormulaFirestore.getBarName();
                //final String path = documentSnapshots.getReference().getPath();
                documentSnapshots.getReference().getId();


                SharedPreferences sharedPreferences = getSharedPreferences("id", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id", id);
                editor.apply();

                Intent intent = new Intent(FormulaActivity.this, SearchDetailActivity.class);
                intent.putExtra("path", id);
                intent.putExtra("barName", barName);
                startActivity(intent);
            }
        });


        adapter.notifyDataSetChanged();


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
