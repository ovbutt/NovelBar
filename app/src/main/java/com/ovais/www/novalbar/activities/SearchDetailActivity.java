package com.ovais.www.novalbar.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ovais.www.novalbar.OrdersFirestore;
import com.ovais.www.novalbar.R;
import com.ovais.www.novalbar.SearchFirestore;
import com.ovais.www.novalbar.adapter.OrderAdapter;
import com.ovais.www.novalbar.adapter.SearchAdapter;
import com.ovais.www.novalbar.adapter.SearchDetailAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.json.JSONObject.wrap;

public class SearchDetailActivity extends AppCompatActivity {

    private Context ctx;
    private Toolbar toolbar;
    private Button btnOrderNow;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    int listSize;

    TextView caloriesTv, carbCaloriesTv, fatCaloriesTv, proteinCaloriesTv,rdaTv, portionSizeTv;
    //TextView ingredientName, portionSize;

    private double totalCalories = 0, totalCarbCalories=0, totalFatCalories=0, totalProteinCalories=0, totalRda=0, totalPortionSize=0;

    private static DecimalFormat decimalFormat = new DecimalFormat(".##");

    private ArrayList<String> ingredientsList = new ArrayList<>();
    private ArrayList<String> quantityList = new ArrayList<>();

    public SearchDetailActivity() {
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);

        final String path = getIntent().getExtras().getString("path", "path");
        final String barName = getIntent().getExtras().getString("barName", "null");

        Log.d("SearchDetailActivity", "onCreate: getExtra: " + path + "," + barName );

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final SearchFirestore searchFirestore = new SearchFirestore();
        //DocumentReference reference = db.document(path);

        toolbar = findViewById(R.id.toolbar_search_detail_activity);
        toolbar.setTitle("Formula Detail");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        caloriesTv = findViewById(R.id.caloriesTextView);
        carbCaloriesTv=findViewById(R.id.carbCaloriesTextView);
        fatCaloriesTv=findViewById(R.id.fatCaloriesTextView);
        proteinCaloriesTv=findViewById(R.id.proteinCaloriesTextView);
        rdaTv=findViewById(R.id.rdaTextView);
        portionSizeTv=findViewById(R.id.portionSizeTextView);

//        ingredientName = findViewById(R.id.txt_search_custom_search_row);
//        portionSize = findViewById(R.id.txt_formula_type_search_custom_row);

//        dataTV = findViewById(R.id.textViewData_DetailSearch);
//        dataTV.setMovementMethod(new ScrollingMovementMethod());
//        barNameTV = findViewById(R.id.barNameTextView);

        db.collection("recipe").document(path)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                String responce = documentSnapshot.getData().toString();
                                arrangingRecipe(responce);

                                caloriesTv.setText(String.valueOf(decimalFormat.format(totalCalories)));
                                carbCaloriesTv.setText(String.valueOf(decimalFormat.format(totalCarbCalories)));
                                fatCaloriesTv.setText(String.valueOf(decimalFormat.format(totalFatCalories)));
                                proteinCaloriesTv.setText(String.valueOf(decimalFormat.format(totalProteinCalories)));
                                rdaTv.setText(String.valueOf(decimalFormat.format(totalRda)));
                                portionSizeTv.setText(String.valueOf(decimalFormat.format(totalPortionSize)));

                                mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_search_detail_activity);
                                mRecyclerView.setHasFixedSize(true);
                                mLayoutManager = new LinearLayoutManager(ctx);
                                mRecyclerView.setLayoutManager(mLayoutManager);
                                mAdapter = new SearchDetailAdapter(ctx, ingredientsList, quantityList, listSize);
                                mRecyclerView.setAdapter(mAdapter);

                            } else {
                                Log.d("SearchDetailActivity", "onComplete: No Such Document");
                            }
                        } else {
                            Log.d("SearchDetailActivity", "onComplete: Task Failed With Exception");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SearchDetailActivity.this, "Task Failed with exception: " + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        btnOrderNow = findViewById(R.id.btn_order_now_search_detail_activity);
        btnOrderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchDetailActivity.this , RecipeOrderActivity.class);
                intent.putExtra("recipeId", path);
                intent.putExtra("barName", barName);
                startActivity(intent);
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

    private void arrangingRecipe (String responce){
        Log.d("SearchDetailActivity", "BeforeReplace: " + responce);
        responce = responce.replaceAll("\\{", "").replaceAll("\\}", "");
        responce = responce.replaceAll("\\d*=", "");
        Log.d("SearchDetailActivity", "OnReplace: " + responce);
        String[] part = responce.split("\\s*,\\s*");

        for (int s=0; s<part.length; s++)
        {
            Log.d("SearchDetailActivity", "onComplete: Split String: " + part[s]);
        }

        for (int i = 0; i < part.length; i++) {
            if (part[i].contains("name")) {
                String ingredient = part[i];
                ingredient = ingredient.replace("name","");
                ingredient = ingredient.trim();
                ingredientsList.add(ingredient);
                listSize = ingredientsList.size();
            }

            if (part[i].contains("quantity"))
            {
                String quantity = part[i];
                quantity = quantity.replace("quantity", "");
                quantity.trim();
                quantityList.add(quantity);
            }

            if (part[i].contains("calories"))
            {
                String calories = part[i];
                calories = calories.replace("calories","");
                final double calorie = Double.parseDouble(calories);
                totalCalories = totalCalories + calorie;
            }

            if (part[i].contains("carbCalorie"))
            {
                String carbCalories = part[i];
                carbCalories = carbCalories.replace("carbCalorie","");
                double carbCalorie = Double.parseDouble(carbCalories);
                totalCarbCalories = totalCarbCalories + carbCalorie;
            }

            if (part[i].contains("fatCalorie"))
            {
                String fatCalories = part[i];
                fatCalories = fatCalories.replace("fatCalorie","");
                double fatCalorie = Double.parseDouble(fatCalories);
                fatCalorie += fatCalorie;
                totalFatCalories = totalFatCalories + fatCalorie;
            }

            if (part[i].contains("proteinCalorie"))
            {
                String proteinCalories = part[i];
                proteinCalories = proteinCalories.replace("proteinCalorie","");
                double proteinCalorie = Double.parseDouble(proteinCalories);
                totalProteinCalories = totalProteinCalories + proteinCalorie;
            }

            if (part[i].contains("rda"))
            {
                String rdas = part[i];
                rdas = rdas.replace("rda","");
                double rda = Double.parseDouble(rdas);
                totalRda = totalRda + rda;
            }

            if (part[i].contains("portionSize"))
            {
                String portionSizes = part[i];
                portionSizes = portionSizes.replace("portionSize","");
                double portionSize = Double.parseDouble(portionSizes);
                totalPortionSize = totalPortionSize + portionSize;
            }
        }
    }

}

