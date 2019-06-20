package com.ovais.www.novalbar.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ovais.www.novalbar.FormulaFirestore;
import com.ovais.www.novalbar.MainActivity;
import com.ovais.www.novalbar.OrdersFirestore;
import com.ovais.www.novalbar.R;
import com.ovais.www.novalbar.SQLite.SQLiteHelper;
import com.ovais.www.novalbar.shareprefer.SharePrefer;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeOrderActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Button btn30Bar, btn60Bar;
    private Button btnSubmit;
    private TextView firstNameTV, lastNameTV, telephoneTV, emailTV, address1TV, cityTV, zipCodetv, creditCardTV, expiryMonthTV, expiryYearTV, ccvCodeTV ,txtTotalAmount;
    private CheckBox checkBoxAgree;
    private FirebaseFirestore db;
    private SharePrefer sp;

    public Token token1;

    String cardNumber="0", cardExpMonth="0", cardExpYear="0", cardCVC="0";

    public static final String PUBLISHABLE_KEY = "pk_test_uGBo2gTRYmpQ78swjYlVeOAc";
    public static final String URL = "https://stripe-checkout-token.herokuapp.com/stripe";
    private Card card;
    private ProgressDialog progress;



    private String firstName, lastName, telephone, email, address1, city, zipCode,
            billingZipCode, userId, recipeId="null", orderId, amount = "85", paidStatus = "paid", barName;
    private String status = "Pending";
    private String numberOfBars = "30";

//    public AddressActivity(){}
//
//    public AddressActivity(Context ctx, List<String> ingredients, List<String> quantity)
//    {
//        this.ctx=ctx;
//        this.ingredients=ingredients;
//        this.quantity=quantity;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_order);

        db = FirebaseFirestore.getInstance();
        sp = new SharePrefer(this);

        recipeId = getIntent().getExtras().getString("recipeId", "null");
        barName = getIntent().getExtras().getString("barName", "null");
        Log.d("SearchDetailActivity", "onCreate: getExtra: " + recipeId + barName );
        progress = new ProgressDialog(this);
        userId = sp.getUid();
        Log.d("Address Activity", "From SP: onCreate: GOT UID: " + userId);

        toolbar = findViewById(R.id.toolbar_separate_address_main_activity);
        toolbar.setTitle("Address");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnSubmit = findViewById(R.id.btn_submit_address_activity);
        btnSubmit.setOnClickListener(this);

            init();

        btn30Bar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                numberOfBars = btn30Bar.getText().toString();
                btn30Bar.setBackgroundColor(getResources().getColor(R.color.orange));
                btn30Bar.setTextSize(18);
                btn30Bar.setTextColor(getResources().getColor(R.color.white));

                btn60Bar.setBackgroundColor(getResources().getColor(R.color.lightGray));
                btn60Bar.setTextColor(getResources().getColor(R.color.black));
                btn60Bar.setTextSize(10);
                txtTotalAmount.setText("Total: $85");
                amount = "85";
            }
        });

        btn60Bar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                numberOfBars = btn60Bar.getText().toString();
                btn60Bar.setBackgroundColor(getResources().getColor(R.color.orange));
                btn60Bar.setTextSize(18);
                btn60Bar.setTextColor(getResources().getColor(R.color.white));

                btn30Bar.setBackgroundColor(getResources().getColor(R.color.lightGray));
                btn30Bar.setTextColor(getResources().getColor(R.color.black));
                btn30Bar.setTextSize(10);
                txtTotalAmount.setText("Total: $120");
                amount = "120";
            }
        });
    }

    private void init() {
        firstNameTV = findViewById(R.id.edit_firstname_separate_address_main_activity);
        lastNameTV = findViewById(R.id.edit_lastname_separate_address_main_activity);
        address1TV = findViewById(R.id.edit_address1_separate_address_main_activity);
        telephoneTV = findViewById(R.id.edit_telephone_address_main_activity);
        emailTV = findViewById(R.id.edit_email_address_main_activity);
        cityTV = findViewById(R.id.edit_city_separate_address_main_activity);
        zipCodetv = findViewById(R.id.edit_Zip_code_separate_address_main_activity);
        creditCardTV = findViewById(R.id.edit_credit_card_separate_address_main_activity);
        expiryMonthTV = findViewById(R.id.edit_expiry_month_separate_address_main_activity);
        expiryYearTV = findViewById(R.id.edit_expiry_year_separate_address_main_activity);
        ccvCodeTV = findViewById(R.id.edit_ccv_code_separate_address_main_activity);
        ccvCodeTV = findViewById(R.id.edit_ccv_code_separate_address_main_activity);
//        billingZipCodeTV = findViewById(R.id.edit_billing_zipCode_separate_address_main_activity);

        checkBoxAgree=findViewById(R.id.checkBoxAddressActivity);

        btn30Bar = findViewById(R.id.btn_30_bar_separaet_address_main_activity);
        btn60Bar = findViewById(R.id.btn_60_bar_separaet_address_main_activity);
        txtTotalAmount = findViewById(R.id.txt_total_amount_separate_address_main_activity);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    public void thankYou(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.thankyou_dialogue_box);
        dialog.setCancelable(false);

        Button btnThankYou = dialog.findViewById(R.id.btn_confirm_thankyou_dialoge_box);
        btnThankYou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecipeOrderActivity.this, MainActivity.class));
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void getText(){
        firstName = firstNameTV.getText().toString();
        lastName = lastNameTV.getText().toString();
        telephone = telephoneTV.getText().toString();
        email = emailTV.getText().toString();
        address1 = address1TV.getText().toString();
        city = cityTV.getText().toString();
        zipCode = zipCodetv.getText().toString();
        cardNumber = creditCardTV.getText().toString();
        cardExpMonth = expiryMonthTV.getText().toString();
        cardExpYear = expiryYearTV.getText().toString();
        cardCVC = ccvCodeTV.getText().toString();

//        if(checkboxPublic.isChecked())
//        {
//            privacy = "publish";
//        }
//        else
//        {
//            privacy = "private";
//        }
    }

    public void saveData(String recipeId){
        this.recipeId = recipeId;
        Map<String, Object> orders = new HashMap<>();
        orders.put("firstName", firstName);
        orders.put("lastName", lastName);
        orders.put("address", address1);
        orders.put("city", city);
        orders.put("postalCode", zipCode);
        orders.put("email", email);
        orders.put("mobile", telephone);
        orders.put("numberOfBars", numberOfBars);
        orders.put("uid", userId);
        orders.put("id", recipeId);
        orders.put("barName", barName);
        orders.put("status", status);

// Add a new document with a generated ID
        db.collection("orders")
                .add(orders)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        orderId = documentReference.getId();
                        Log.d("AddressActivity", "Order DocumentSnapshot added with ID: " + documentReference.getId());
                        charge(token1);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("AddressActivity", "Error adding document", e);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        final String user_email = emailTV.getText().toString().trim();
        final String validEmail ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        getText();
        card = new Card(cardNumber, Integer.parseInt(cardExpMonth), Integer.parseInt(cardExpYear), cardCVC);

        if (user_email.length()==0) {
            emailTV.requestFocus();
            emailTV.setError(Html.fromHtml("<font color='white' >Please Enter Email</font>"));
        }

        else if (firstNameTV.getText().toString().length() == 0)
        {
            firstNameTV.requestFocus();
            firstNameTV.setError(Html.fromHtml("<font color ='white' >Please Enter First Name</font>"));
        }
        else if (lastNameTV.getText().toString().length() == 0)
        {
            lastNameTV.requestFocus();
            lastNameTV.setError(Html.fromHtml("<font color ='white' >Please Enter Last Name</font>"));
        }
        else if (telephoneTV.getText().toString().length() == 0)
        {
            telephoneTV.requestFocus();
            telephoneTV.setError(Html.fromHtml("<font color ='white' >Please Enter Phone Number</font>"));
        }
        else if (address1TV.getText().toString().length() == 0)
        {
            address1TV.requestFocus();
            address1TV.setError(Html.fromHtml("<font color ='white' >Please Enter Address</font>"));
        }
        else if (cityTV.getText().toString().length() == 0)
        {
            cityTV.requestFocus();
            cityTV.setError(Html.fromHtml("<font color ='white' >Please Enter City</font>"));
        }
        else if (zipCodetv.getText().toString().length() == 0)
        {
            zipCodetv.requestFocus();
            zipCodetv.setError(Html.fromHtml("<font color ='white' >Please Enter Zip Code</font>"));
        }
        else if (creditCardTV.getText().toString().length() == 0)
        {
            creditCardTV.requestFocus();
            creditCardTV.setError(Html.fromHtml("<font color ='white' >Please Enter Credit Card Number</font>"));
        }

        else if (!card.validateNumber())
        {
            creditCardTV.requestFocus();
            creditCardTV.setError(Html.fromHtml("<font color ='white' >Card Number not valid!</font>"));
        }

        else if (!card.validateExpMonth())
        {
            expiryMonthTV.requestFocus();
            expiryMonthTV.setError(Html.fromHtml("<font color ='white' >Your Card month is not valid!</font>"));
        }

        else if (expiryMonthTV.getText().toString().length() == 0)
        {
            expiryMonthTV.requestFocus();
            expiryMonthTV.setError(Html.fromHtml("<font color ='white' >Please Enter Expiry Date</font>"));
        }
        else if (expiryYearTV.getText().toString().length() == 0)
        {
            expiryYearTV.requestFocus();
            expiryYearTV.setError(Html.fromHtml("<font color ='white' >Please Enter Expiry Date</font>"));
        }

        else if (!card.validateExpiryDate())
        {
            expiryYearTV.requestFocus();
            expiryYearTV.setError(Html.fromHtml("<font color ='white' >Your card is expired!</font>"));
        }

        else if (ccvCodeTV.getText().toString().length() == 0)
        {
            ccvCodeTV.requestFocus();
            ccvCodeTV.setError(Html.fromHtml("<font color ='white' >Please Enter CVC Code</font>"));
        }

        else if (!card.validateCVC())
        {
            ccvCodeTV.requestFocus();
            ccvCodeTV.setError(Html.fromHtml("<font color ='white' >CVC Number not valid!</font>"));
        }

        else if (!user_email.matches(validEmail))
        {
            emailTV.requestFocus();
            emailTV.setError(Html.fromHtml("<font color='white' >Please enter a valid email</font>"));
        }

        else if ( v == btnSubmit)
        {
            if(checkBoxAgree.isChecked())
            {
                buy();
            }
            else
            {
                Toast toast = Toast.makeText(this,"Please agree to terms and conditions.", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(R.color.colorPrimary);
                TextView text = (TextView) view.findViewById(android.R.id.message);
                text.setTextColor(Color.parseColor("#ffffff"));
                toast.show();
                //Toast.makeText(RecipeOrderActivity.this, "Please Agree to terms and Conditions", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void buy(){
        boolean validation = card.validateCard();
        if(validation){
            startProgress("Validating Credit Card");
            Stripe stripe = new Stripe(this, PUBLISHABLE_KEY);
            stripe.createToken(
                    card,
                    new TokenCallback() {
                        @Override
                        public void onError(Exception error) {
                            Log.d("Stripe",error.toString());
                        }

                        @Override
                        public void onSuccess(Token token) {
                            token1 = token;
                            finishProgress();
                            saveData(recipeId);
                        }
                    });
        } else if (!card.validateNumber()) {
            //     Toast.makeText(this, "The card number that you entered is invalid", Toast.LENGTH_SHORT).show();
            Log.d("Stripe","The card number that you entered is invalid");
        } else if (!card.validateExpiryDate()) {
            //    Toast.makeText(this, "The expiration date that you entered is invalid", Toast.LENGTH_SHORT).show();

            Log.d("Stripe","The expiration date that you entered is invalid");
        } else if (!card.validateCVC()) {
            //   Toast.makeText(this, "The CVC code that you entered is invalid", Toast.LENGTH_SHORT).show();

            Log.d("Stripe","The CVC code that you entered is invalid");
        } else {
            //     Toast.makeText(this, "The card details that you entered are invalid", Toast.LENGTH_SHORT).show();

            Log.d("Stripe","The card details that you entered are invalid");
        }
    }

    private void charge(final Token cardToken){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("token", cardToken.getId());
        params.put("amount",amount );
        params.put("orderId",orderId);
        params.put("email", email );
        startProgress("Purchasing Item");

        Log.d("MainActivity", "charge: " + cardToken.getId() + "\n" + "Data in Map: " + params.toString() );

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                finishProgress();
                thankYou();
                paidStatus = "paid";
                postPayment(response);
                Toast toast = Toast.makeText(RecipeOrderActivity.this,"Payment Successful!", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(R.color.colorPrimary);
                TextView text = (TextView) view.findViewById(android.R.id.message);
                text.setTextColor(Color.parseColor("#ffffff"));
                toast.show();
                //Toast.makeText(RecipeOrderActivity.this, "Payment Successful!", Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "onResponse: " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                finishProgress();
                Toast toast = Toast.makeText(RecipeOrderActivity.this,"Payment Not Successful! - " + error.toString(), Toast.LENGTH_LONG);
                View view = toast.getView();
                view.setBackgroundResource(R.color.colorPrimary);
                TextView text = (TextView) view.findViewById(android.R.id.message);
                text.setTextColor(Color.parseColor("#ffffff"));
                toast.show();
                //Toast.makeText(RecipeOrderActivity.this, "Payment Not Successful! - " + error.toString(), Toast.LENGTH_SHORT).show();
                paidStatus = "unpaid";
                Log.d("MainActivity", "onResponse: " + error.toString());
                db.collection("orders").document(orderId).delete();
            }
        }){
            @Override
            public Map<String, String> getParams ()  throws AuthFailureError {
                final Map<String, String> params = new HashMap<>();
                params.put("token", cardToken.getId());
                params.put("amount", amount );
                params.put("orderId", orderId);
                params.put("email", email );
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void startProgress(String title){
        progress.setTitle(title);
        progress.setMessage("Please Wait");
        progress.show();
    }

    private void finishProgress(){
        progress.dismiss();
    }

    private void postPayment (String response){
        Map<String, Object> paymentMap = new HashMap<>();
        paymentMap.put("charge", response);
        paymentMap.put("amount", amount);
        paymentMap.put("status", paidStatus);
        paymentMap.put("uid", userId);

        db.collection("payments").add(paymentMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("Address Activity", "onSuccess: Payment Document Added on: " + documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("AddressActivity", "onFailure: Payment Document failed to add");
            }
        });
    }
}
