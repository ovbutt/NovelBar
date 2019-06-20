package com.ovais.www.novalbar.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ovais.www.novalbar.CustomMessageEvent;
import com.ovais.www.novalbar.MainActivity;
import com.ovais.www.novalbar.R;
import com.ovais.www.novalbar.SQLite.IngredientData;
import com.ovais.www.novalbar.SQLite.SQLiteHelper;
import com.ovais.www.novalbar.shareprefer.SharePrefer;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "HTAG";
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private SignInButton signInButton;
    private SharePrefer userData;
    private ProgressBar progressBar;
    private  IngredientData ingredientData;
    ProgressDialog progressDialog;

    private int size;
    private int index = 0;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userData = new SharePrefer(this);

        Log.d(TAG, "onCreate: In Login Activity");

        progressBar = findViewById(R.id.progressBar_login_activity);
        ingredientData = new IngredientData();

        getIngredients();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

//        signInButton = findViewById(R.id.btn_signin_login_activity);
//        signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signIn();
//            }
//        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //  signIn();
        if (currentUser != null) {

            userId = mAuth.getCurrentUser().getUid();
            //sharedPreferences1 = this.getSharedPreferences("uid", 0);
            FirebaseUser user = mAuth.getCurrentUser();
            userData.setUid(user.getUid());

            Log.d(TAG, "onCreate: UID: " + userId);

            Log.d(TAG, "onCreate: Current USer is : " + currentUser.getDisplayName());
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Log.d(TAG, "onCreate: No User Log In");
            progressBar.setVisibility(View.VISIBLE);
            signIn();
        }

    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        Log.d(TAG, "onStart: Email " + currentUser.getEmail());
//        Log.d(TAG, "onStart: Name : " +  currentUser.getDisplayName());
//        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish();
//    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 101) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ... yet to handle failed login here
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

//                            SharedPreferences.Editor editor = sharedPreferences1.edit();
//                            editor.putString("uid", userId);
//                            editor.apply();

                            FirebaseUser user = mAuth.getCurrentUser();

                            userData.setName(user.getDisplayName());
                            userData.setEmail(user.getEmail());
                            userData.setImageURL(user.getPhotoUrl().toString());
                            userData.setUid(user.getUid());


                            String name = userData.getName();
                            String email = userData.getEmail();
                            String imageURL = userData.getImageURL();
                            final String uid = userData.getUid();

                            Map<String, Object> userMap = new HashMap<>();
                            userMap.put("displayName", name);
                            userMap.put("email", email);
                            userMap.put("isAdmin", "false");
                            userMap.put("photoURL", imageURL);
                            userMap.put("uid", uid);

                            db.collection("users").document(uid).set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: " + uid);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });


                            Log.d(TAG, "onComplete: Name : " + userData.getName());
                            Log.d(TAG, "onComplete: Email " + userData.getEmail());
                            Log.d(TAG, "onComplete: Url : " + userData.getImageURL());
                            Log.d(TAG, "onComplete: UID : " + userData.getUid());


                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            //updateUI(user);
                            Log.d(TAG, "My signInWithCredential:success");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(TAG, "My signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            // updateUI(null);
                        }

                    }
                });
    }

    private void getIngredients() {
        db = FirebaseFirestore.getInstance();

        final SQLiteHelper sqLiteHelper = new SQLiteHelper(this);

        db.collection("ingredients")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                ingredientData = document.toObject(IngredientData.class);
                                sqLiteHelper.addData(ingredientData);
                                String data = document.getData().toString();

                                Log.d(TAG, "onComplete: Data fetched " + data);
                            }
                            }
                    }
                });


    }

}


