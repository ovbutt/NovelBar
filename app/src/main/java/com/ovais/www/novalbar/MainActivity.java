package com.ovais.www.novalbar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.ovais.www.novalbar.SQLite.SQLiteHelper;
import com.ovais.www.novalbar.activities.DetailOrderActivity;
import com.ovais.www.novalbar.activities.FormulaActivity;
import com.ovais.www.novalbar.activities.MyOrderActivity;
import com.ovais.www.novalbar.activities.SearchActivity;
import com.ovais.www.novalbar.adapter.RecycleVerticalAdapterFrag1;
//import com.ovais.www.novalbar.adapter.ScreenSlidePagerAdapter;
import com.ovais.www.novalbar.shareprefer.SharePrefer;
import com.ovais.www.novalbar.testFragments.Frag1;
import com.ovais.www.novalbar.adapter.SectionsPagerAdapter;
import com.ovais.www.novalbar.testFragments.Frag2;
import com.ovais.www.novalbar.testFragments.Frag3;
import com.ovais.www.novalbar.testFragments.Frag4;
import com.ovais.www.novalbar.testFragments.Frag5;
import com.ovais.www.novalbar.testFragments.Frag6;
import com.ovais.www.novalbar.testFragments.Frag7;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MAIN-ACTIVITY";
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecycleVerticalAdapterFrag1 verticalAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SectionsPagerAdapter adapter;
    private ViewPager mViewPager;
    private SharePrefer userData;
    private TextView profileName, profileEmail;
    private ImageView profileImage;
    private PagerAdapter mPagerAdapter;
    private TextView tvBarweight, tvBulkweight;
    private Button infoButton;
    private SQLiteHelper sqLiteHelper = new SQLiteHelper(this);
    private String totalCount, totalCountBulk;
    private int size;
    private int index = 0;
    private int bulkWeight;
    private Double barWeight;
    private String bitternessFlag, calories = "0", carbCalorie = "0", category, fatCalorie = "0", name , portionFlag, portionSize = "0", proteinCalorie = "0", quantity, rda = "0", servingSize, unhealthyFlag ;
    private double calorieValue, carbCalorieValue, fatCalorieValue, proteinCalorieValue, rdaValue, portionSizeValue, calorieTotalValue, carbCalorieTotalValue, fatCalorieTotalValue, proteinCalorieTotalValue, rdaTotalValue, portionSizeTotalValue;
    private String calorieTotalValueIntent, carbCalorieTotalValueIntent, fatCalorieTotalValueIntent, proteinCalorieTotalValueIntent, rdaTotalValueIntent, portionSizeTotalValueIntent;
    private Dialog dialog;

    private static DecimalFormat decimalFormat = new DecimalFormat(".##");

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String [] data = {"Bulk", "Protein", "Vitamins/Minerals", "Fiber", "Veggies", "Nuts", "Dried Fruits"};

    private List<String> categoriesName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sqLiteHelper.setQuantityZero();
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_content_main);
        setSupportActionBar(toolbar);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        tvBarweight = findViewById(R.id.barWeightTV);
        tvBulkweight = findViewById(R.id.bulkWeightTV);

        barWeight = Double.valueOf(tvBarweight.getText().toString());
        bulkWeight = Integer.valueOf(tvBulkweight.getText().toString());

        userData = new SharePrefer(this);

        Log.d(TAG, "onCreate: NAme : " + userData.getName());

        db = FirebaseFirestore.getInstance();
        categoriesName = new ArrayList<>();

        mRecyclerView = findViewById(R.id.recyclerview_frag1);

        infoButton = findViewById(R.id.info_btn);

        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_dialogue_box);

        infoButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

//               TextView headingTv = dialog.findViewById(R.id.textViewHeading);
//               TextView calorieTV = dialog.findViewById(R.id.caloriesTextView_CustomDialogue);
//               TextView carbCalorieTV = dialog.findViewById(R.id.carbCaloriesTextView_CustomDialogue);
//               TextView fatCalorieTV = dialog.findViewById(R.id.fatCaloriesTextView_CustomDialogue);
//               TextView proteinCalorieTV = dialog.findViewById(R.id.proteinCaloriesTextView_CustomDialogue);
//               TextView rdaTV = dialog.findViewById(R.id.rdaTextView_CustomDialogue);
//               TextView portionSizeTV = dialog.findViewById(R.id.portionSizeTextView_CustomDialogue);
//
////               headingTv.setText("Information");
////               calorieTV.setText(calories);
////               carbCalorieTV.setText(carbCalorie);
////               fatCalorieTV.setText(fatCalorie);
////               proteinCalorieTV.setText(proteinCalorie);
////               rdaTV.setText(rda);
////               portionSizeTV.setText(portionSize);
//
//              calorieValue = Double.parseDouble(calorieTV.getText().toString());
//              carbCalorieValue = Double.parseDouble(carbCalorieTV.getText().toString());
//              fatCalorieValue = Double.parseDouble(fatCalorieTV.getText().toString());
//              proteinCalorieValue = Double.parseDouble(proteinCalorieTV.getText().toString());
//              rdaValue = Double.parseDouble(rdaTV.getText().toString());
//              portionSizeValue = Double.parseDouble(portionSizeTV.getText().toString());
//
//              calorieTotalValue = calorieValue + Double.parseDouble(calories);
//              carbCalorieTotalValue = carbCalorieValue + Double.parseDouble(carbCalorie);
//              fatCalorieTotalValue = fatCalorieValue + Double.parseDouble(fatCalorie);
//              proteinCalorieTotalValue = proteinCalorieValue + Double.parseDouble(proteinCalorie);
//              rdaTotalValue = rdaValue + Double.parseDouble(rda);
//              portionSizeTotalValue = portionSizeValue + Double.parseDouble(portionSize);
//
//
//
//               if (calorieValue == 0)
//               {
//                   headingTv.setText("Information");
//               calorieTV.setText(calories);
//               carbCalorieTV.setText(carbCalorie);
//               fatCalorieTV.setText(fatCalorie);
//               proteinCalorieTV.setText(proteinCalorie);
//               rdaTV.setText(rda);
//               portionSizeTV.setText(portionSize);
//               }
//               else
//               {
//                   calorieTV.setText(String.valueOf(calorieTotalValue));
//                   carbCalorieTV.setText(String.valueOf(carbCalorieTotalValue));
//                   fatCalorieTV.setText(String.valueOf(fatCalorieTotalValue));
//                   proteinCalorieTV.setText(String.valueOf(proteinCalorieTotalValue));
//                   rdaTV.setText(String.valueOf(rdaTotalValue));
//                   portionSizeTV.setText(String.valueOf(portionSizeTotalValue));
//               }

               //headingTv.setText("Information");
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
       });

        //mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.viewpager_detail_order_activity);
        mViewPager.setAdapter(mPagerAdapter);
        setUpViewPager(mViewPager);
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(mViewPager);

//        db.collection("users").whereEqualTo("email", "ovaisbutt786@gmail.com")
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot querySnapshot,
//                                        @Nullable FirebaseFirestoreException e) {
//                        if (e != null) {
//                            Log.w(TAG, "Listen error", e);
//                            return;
//                        }
//
//                        for (DocumentChange change : querySnapshot.getDocumentChanges()) {
//                            if (change.getType() == DocumentChange.Type.ADDED) {
//                                Log.d(TAG, "New city:" + change.getDocument().getData());
//                            }
//
//                            String source = querySnapshot.getMetadata().isFromCache() ?
//                                    "local cache" : "server";
//                            Log.d(TAG, "Data fetched from " + source);
//                        }
//
//                    }
//                });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_content_main);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_order: {
                                /*Toast.makeText(MainActivity.this, "ORDERR", Toast.LENGTH_SHORT).show();*/
                                startActivity(new Intent(MainActivity.this, MyOrderActivity.class));
                                break;
                            }

                            case R.id.action_search: {
//                                Toast.makeText(MainActivity.this, "SEARCH", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                                break;
                            }

                        }
                        return true;
                    }
                });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);
        profileName = headerView.findViewById(R.id.txt_name_nav_header_main);
        profileEmail = headerView.findViewById(R.id.txt_email_nav_header_main);
        profileImage = headerView.findViewById(R.id.imageView_nav_header_main);
        profileName.setText(userData.getName());
        profileEmail.setText(userData.getEmail());
//        Uri myImgUri = Uri.parse(userData.getImageURL());
        //profileImage.setImageURI(myImgUri);
        Picasso.get()
                .load(userData.getImageURL()).into(profileImage);

        //verticalAdapter = new RecycleVerticalAdapterFrag1(getContext(), data);
    }


    @Subscribe
    public void onEvent(CustomMessageEvent event) {

        String totalCount = String.valueOf(event.getCustomMessage());
        String totalCountBulk = String.valueOf(event.getCustomMessage2());
        //Log.d(TAG, "onEvent: Event Triggered " + event.getCustomMessage() + event.getCustomMessage2() + "Total Count: " + totalCount + "Total Count 2: " + totalCountBulk);

        barWeight = Double.valueOf(tvBarweight.getText().toString());
        bulkWeight = Integer.valueOf(tvBulkweight.getText().toString());

        double totalCount1 = Double.parseDouble(totalCount) + barWeight;
        int totalCount2 = Integer.parseInt(totalCountBulk) + bulkWeight;

        if( barWeight >= 0 ) {
            tvBarweight.setText(String.valueOf(decimalFormat.format(totalCount1)));
            tvBulkweight.setText(String.valueOf(totalCount2));
        }
        else
        {
            tvBulkweight.setText("0");
            tvBarweight.setText("0");
        }
        Log.d(TAG, "onEvent: Event Triggered: " + "Total Count: " + totalCount1 + "Total Count 2: " + totalCount2);

    }

    @Subscribe
    public void onEvent(InformationMessageEvent infoEvent){

        calories = String.valueOf(infoEvent.getCalories());
        carbCalorie = String.valueOf(infoEvent.getCarbCalories());
        fatCalorie = String.valueOf(infoEvent.getFatCalories());
        proteinCalorie = String.valueOf(infoEvent.getProteinCalories());
        rda = String.valueOf(infoEvent.getRda());
        portionSize = String.valueOf(infoEvent.getPortionSize());

        TextView headingTv = dialog.findViewById(R.id.textViewHeading);
        TextView calorieTV = dialog.findViewById(R.id.caloriesTextView_CustomDialogue);
        TextView carbCalorieTV = dialog.findViewById(R.id.carbCaloriesTextView_CustomDialogue);
        TextView fatCalorieTV = dialog.findViewById(R.id.fatCaloriesTextView_CustomDialogue);
        TextView proteinCalorieTV = dialog.findViewById(R.id.proteinCaloriesTextView_CustomDialogue);
        TextView rdaTV = dialog.findViewById(R.id.rdaTextView_CustomDialogue);
        TextView portionSizeTV = dialog.findViewById(R.id.portionSizeTextView_CustomDialogue);

//               headingTv.setText("Information");
//               calorieTV.setText(calories);
//               carbCalorieTV.setText(carbCalorie);
//               fatCalorieTV.setText(fatCalorie);
//               proteinCalorieTV.setText(proteinCalorie);
//               rdaTV.setText(rda);
//               portionSizeTV.setText(portionSize);

        calorieValue = Double.parseDouble(calorieTV.getText().toString());
        carbCalorieValue = Double.parseDouble(carbCalorieTV.getText().toString());
        fatCalorieValue = Double.parseDouble(fatCalorieTV.getText().toString());
        proteinCalorieValue = Double.parseDouble(proteinCalorieTV.getText().toString());
        rdaValue = Double.parseDouble(rdaTV.getText().toString());
        portionSizeValue = Double.parseDouble(portionSizeTV.getText().toString());

        calorieTotalValue = calorieValue + Double.parseDouble(calories);
        carbCalorieTotalValue = carbCalorieValue + Double.parseDouble(carbCalorie);
        fatCalorieTotalValue = fatCalorieValue + Double.parseDouble(fatCalorie);
        proteinCalorieTotalValue = proteinCalorieValue + Double.parseDouble(proteinCalorie);
        rdaTotalValue = rdaValue + Double.parseDouble(rda);
        portionSizeTotalValue = portionSizeValue + Double.parseDouble(portionSize);



        if (calorieValue == 0)
        {
            headingTv.setText("Information");
            calorieTV.setText(calories);
            carbCalorieTV.setText(carbCalorie);
            fatCalorieTV.setText(fatCalorie);
            proteinCalorieTV.setText(proteinCalorie);
            rdaTV.setText(rda);
            portionSizeTV.setText(portionSize);
        }
        else
        {
            headingTv.setText("Information");
            calorieTV.setText(String.valueOf(decimalFormat.format(calorieTotalValue)));
            carbCalorieTV.setText(String.valueOf(decimalFormat.format(carbCalorieTotalValue)));
            fatCalorieTV.setText(String.valueOf(decimalFormat.format(fatCalorieTotalValue)));
            proteinCalorieTV.setText(String.valueOf(decimalFormat.format(proteinCalorieTotalValue)));
            rdaTV.setText(String.valueOf(decimalFormat.format(rdaTotalValue)));
            portionSizeTV.setText(String.valueOf(decimalFormat.format(portionSizeTotalValue)));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        double getBulkWeight = Double.parseDouble(tvBulkweight.getText().toString());
        double getBarWeight = Double.parseDouble(tvBarweight.getText().toString());

        //noinspection SimplifiableIfStatement
        if (id == R.id.next_arrow_main) {
           if(getBulkWeight < 28.0)
            {
                Toast toast = Toast.makeText(this,"Bulk Weight must be greater than 28 Grams", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(R.color.colorPrimary);
                TextView text = (TextView) view.findViewById(android.R.id.message);
                text.setTextColor(Color.parseColor("#ffffff"));
                toast.show();
//                Toast.makeText(this, "Bulk Weight must be greater than 28 Grams", Toast.LENGTH_LONG).show();

            }
            else if (getBarWeight < 70.0)
            {
                Toast toast = Toast.makeText(this,"Bar Weight must be greater than 70 Grams", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(R.color.colorPrimary);
                TextView text = (TextView) view.findViewById(android.R.id.message);
                text.setTextColor(Color.parseColor("#ffffff"));
                toast.show();
               // Toast.makeText(this, "Bar Weight must be greater than 70 Grams", Toast.LENGTH_LONG).show();

            }
            else if (getBarWeight > 85.0)
            {
                Toast toast = Toast.makeText(this,"Bar Weight cannot be greater than 85 Grams", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(R.color.colorPrimary);
                TextView text = (TextView) view.findViewById(android.R.id.message);
                text.setTextColor(Color.parseColor("#ffffff"));
                toast.show();
                //Toast.makeText(this, "Bar Weight cannot be greater than 85 Grams", Toast.LENGTH_LONG).show();

            }
//                return true;

            else
            {
                calorieTotalValueIntent = String.valueOf(decimalFormat.format(calorieTotalValue));
                carbCalorieTotalValueIntent = String.valueOf(decimalFormat.format(carbCalorieTotalValue));
                fatCalorieTotalValueIntent = String.valueOf(decimalFormat.format(fatCalorieTotalValue));
                proteinCalorieTotalValueIntent = String.valueOf(decimalFormat.format(proteinCalorieTotalValue));
                rdaTotalValueIntent = String.valueOf(decimalFormat.format(rdaTotalValue));
                portionSizeTotalValueIntent = String.valueOf(decimalFormat.format(portionSizeTotalValue));

                Intent intent = new Intent(MainActivity.this, DetailOrderActivity.class);
                intent.putExtra("calorieTotalValueIntent", calorieTotalValueIntent);
                intent.putExtra("carbCalorieTotalValueIntent", carbCalorieTotalValueIntent);
                intent.putExtra("fatCalorieTotalValueIntent", fatCalorieTotalValueIntent);
                intent.putExtra("proteinCalorieTotalValueIntent", proteinCalorieTotalValueIntent);
                intent.putExtra("rdaTotalValueIntent", rdaTotalValueIntent);
                intent.putExtra("portionSizeTotalValueIntent", portionSizeTotalValueIntent);
                startActivity(intent);            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_formula_main_drawer_activity) {
            startActivity(new Intent(MainActivity.this, FormulaActivity.class));
        } else if (id == R.id.nav_log_out) {
            Log.d(TAG, "Log Out : ");
            // FirebaseAuth.getInstance().signOut();
            profileName.setText("No Name");
            profileEmail.setText("No Email");
            showDialogueMsg();
        } else if (id == R.id.nav_my_order) {
            startActivity(new Intent(MainActivity.this, MyOrderActivity.class));
        } else if (id == R.id.nav_search_main_drawer_activity) {
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {

        GoogleSignInClient mGoogleSignInClient;
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        FirebaseAuth.getInstance().signOut();
        // mAuth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
//                        Intent intent = new Intent(YourActivity.this, NextActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);

                        Log.d(TAG, "onComplete: logut GoogleSign In Complete");
                    }
                });
    }

    public void showDialogueMsg() {


        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Sign Out")
                .setMessage("Are You Sure! You Wanna Sign Out?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete

                        signOut();
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    public void setUpViewPager(ViewPager viewPager) {
        adapter = new SectionsPagerAdapter(getSupportFragmentManager());

            adapter.addFragment(new Frag1(), "Bulk");
            adapter.addFragment(new Frag2(), "Protein");
            adapter.addFragment(new Frag3(), "Vitamins/Minerals");
            adapter.addFragment(new Frag4(), "Fiber");
            adapter.addFragment(new Frag5(), "Veggies");
            adapter.addFragment(new Frag6(), "Nuts");
            adapter.addFragment(new Frag7(), "Dried Fruits");


        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {



            }

            @Override
            public void onPageSelected(int i) {

//                verticalAdapter = new RecycleVerticalAdapterFrag1(driedfruits);
//                mRecyclerView.setAdapter(verticalAdapter);
//                verticalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

//    private void getCategories() {
//        db = FirebaseFirestore.getInstance();
//
//        db.collection("categories")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            categoriesName.clear();
//
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                FoodCategories foodCategories = document.toObject(FoodCategories.class);
//
//                                //String result = foodCategories.getCategories();
//
//                                String result = String.valueOf(document.getData());
//                                categoriesName.add(index, result);
//                                //categoriesName.toArray();
//                                index++;
//                                categoriesName.size();
//                                //Toast.makeText(MainActivity.this, catogeriesName.toString(), Toast.LENGTH_SHORT).show();
//
//                            }
//                            //Toast.makeText(MainActivity.this, catogeriesName.toString() + catogeriesName.size() + "index:" + index, Toast.LENGTH_SHORT).show();
//                            Toast.makeText(MainActivity.this, categoriesName.toString() + categoriesName.size() + "index:" + index, Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(MainActivity.this, "Error Getting Results" + task.getException(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//
//    public void displayUI() {
//
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//
//            }
//        },3000);
//    }
}