package com.ovais.www.novalbar.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ovais.www.novalbar.R;
import com.ovais.www.novalbar.adapter.DetailOrderAdapter;
import com.ovais.www.novalbar.adapter.RecycleVerticalAdapterFrag1;
import com.ovais.www.novalbar.adapter.SearchDetailAdapter;
import com.ovais.www.novalbar.testFragments.Frag1;
import com.ovais.www.novalbar.testFragments.Frag2;
//import com.ovais.www.novalbar.testFragments.IngredientsQuantitiyHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DetailOrderActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private AddressActivity addressActivity;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecycleVerticalAdapterFrag1 recycleVerticalAdapterFrag1;
  //  private IngredientsQuantitiyHelper ingredientsQuantitiyHelper;
    public List<String> arrquantity;
    public List<String> arrIngredients;
    public int listSize=0;
    private String calorieTotalValueIntent, carbCalorieTotalValueIntent, fatCalorieTotalValueIntent, proteinCalorieTotalValueIntent, rdaTotalValueIntent, portionSizeTotalValueIntent;

    private String [] ingredients = {"Oatmeal","Wheat Germ", "Corn Flakes", "Rice Cereals", "Soy", "Whey Powder", "Spirulina",
            "Vitamin B,C,D Complex", "Soluble Fiber", "Beans", "Kale", "Carrots", "Beets", "Pecans", "Cashew", "Pine", "Peanuts",
            "Walnuts", "Almonds", "Cranberries", "Apricots", "Banana Chips", "Dates"};
    private FirebaseFirestore db;
    private int quantity[];
    private String array1String;
    int [] arrayInt;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TextView calorieTV, carbCalorieTV, fatCalorieTV, proteinCalorieTV, rdaTV, portionSizeTV;

    private static DecimalFormat decimalFormat = new DecimalFormat(".##");

    String pref1,pref2, pref3, pref4, pref5, pref6, pref7, concatinatedPref;
    String [] words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);

//        EventBus.getDefault().register(this);

        db = FirebaseFirestore.getInstance();

        toolbar = findViewById(R.id.toolbar_detail_order_activity);
        toolbar.setTitle("Detail Order");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

      //  ingredientsQuantitiyHelper = new IngredientsQuantitiyHelper();

        Intent intent = getIntent();
        calorieTotalValueIntent = intent.getStringExtra("calorieTotalValueIntent");
        carbCalorieTotalValueIntent = intent.getStringExtra("carbCalorieTotalValueIntent");
        fatCalorieTotalValueIntent = intent.getStringExtra("fatCalorieTotalValueIntent");
        proteinCalorieTotalValueIntent = intent.getStringExtra("proteinCalorieTotalValueIntent");
        rdaTotalValueIntent = intent.getStringExtra("rdaTotalValueIntent");
        portionSizeTotalValueIntent = intent.getStringExtra("portionSizeTotalValueIntent");

        calorieTV = findViewById(R.id.caloriesTextView);
        carbCalorieTV = findViewById(R.id.carbCaloriesTextView);
        fatCalorieTV = findViewById(R.id.fatCaloriesTextView);
        proteinCalorieTV = findViewById(R.id.proteinCaloriesTextView);
        rdaTV = findViewById(R.id.rdaTextView);
        portionSizeTV = findViewById(R.id.portionSizeTextView);

        calorieTV.setText(calorieTotalValueIntent);
        carbCalorieTV.setText(carbCalorieTotalValueIntent);
        fatCalorieTV.setText(fatCalorieTotalValueIntent);
        proteinCalorieTV.setText(proteinCalorieTotalValueIntent);
        rdaTV.setText(rdaTotalValueIntent);
        portionSizeTV.setText(portionSizeTotalValueIntent);

        getPrefs();
        cleaningData();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_detail_order_activity);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)

        mAdapter = new DetailOrderAdapter(this, arrIngredients, arrquantity, listSize);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // for Next Page
        int id = item.getItemId();
        if (id == R.id.next_arrow_main) {
            if((arrIngredients.size()) !=0 && (arrquantity.size()!=0)) {
                Intent intent = new Intent(DetailOrderActivity.this, AddressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("arrIngredient", (ArrayList<String>) arrIngredients);
                bundle.putStringArrayList("arrQuantity", (ArrayList<String>) arrquantity);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            }
            else {
                Toast.makeText(DetailOrderActivity.this, "No ingredients found. Please add ingredients in your bar.", Toast.LENGTH_SHORT).show();
            }
        }

        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

//    @Subscribe
//    public void onEvent(ArrayMessageEvent arrayMessageEvent) {
//        int arr [] = arrayMessageEvent.getArray1();
//        Log.d("DetailOrderActivity", "onEvent: Event values: " + arrayMessageEvent.getArray1().toString());
//
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    Frag1 frag1 = new Frag1();
                    return frag1;
                case 1:
                    Frag2 frag2 = new Frag2();
                    return frag2;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

    }

    public void getPrefs (){
        SharedPreferences sharedPreferences1 = this.getSharedPreferences("Pref" , 0);
        pref1 = sharedPreferences1.getString("words", "0");
        sharedPreferences1 = this.getSharedPreferences("Pref2" , 0);
        pref2 = sharedPreferences1.getString("words", "0");
        sharedPreferences1 = this.getSharedPreferences("Pref3" , 0);
        pref3 = sharedPreferences1.getString("words", "0");
        sharedPreferences1 = this.getSharedPreferences("Pref4" , 0);
        pref4 = sharedPreferences1.getString("words", "0");
        sharedPreferences1 = this.getSharedPreferences("Pref5" , 0);
        pref5 = sharedPreferences1.getString("words", "0");
        sharedPreferences1 = this.getSharedPreferences("Pref6" , 0);
        pref6 = sharedPreferences1.getString("words", "0");
        sharedPreferences1 = this.getSharedPreferences("Pref7" , 0);
        pref7 = sharedPreferences1.getString("words", "0");

        //sharedPreferences1.edit().clear().apply();
        concatinatedPref = pref1+pref2+pref3+pref4+pref5+pref6+pref7;
        Log.d("DetailOrderActivity", "getPrefs: " + concatinatedPref);

        words = concatinatedPref.split(",");
        Log.d("DetailOrderActivity", "getPrefs: " + words.length);
    }

    public void cleaningData () {

        arrquantity = new ArrayList<String>();
        arrIngredients = new ArrayList<String>();

        for (int y = 0; y < words.length; y++) {

            if (words[y].equals("0") || words[y].contains("null") || words[y].contains("00")) {

            } else {
                arrquantity.add(words[y]);
                arrIngredients.add(ingredients[y]);
            }
        }

        listSize = arrIngredients.size();
    }


}
