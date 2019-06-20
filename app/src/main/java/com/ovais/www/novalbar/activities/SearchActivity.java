package com.ovais.www.novalbar.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Query;
import com.algolia.search.saas.Index;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ovais.www.novalbar.R;
import com.ovais.www.novalbar.SearchFirestore;
import com.ovais.www.novalbar.adapter.SearchAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class SearchActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference reference = db.collection("recipe");
    private SearchAdapter adapter;
    private Client client;
    private Toolbar toolbar;
    private List<String> barNames = new ArrayList<>();
    private SearchFirestore searchFirestore;

    String query1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toolbar = findViewById(R.id.toolbar_search_activity);
        toolbar.setTitle("Search Recipe");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        searchFirestore = new SearchFirestore();
        setUpRecyclerView();
        client = new Client("CZU9PD976L", "7f704d5bfa8ec952c779204a1383b72b");

        reference.whereEqualTo("privacy", "publish").orderBy("barName", com.google.firebase.firestore.Query.Direction.ASCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    barNames.add(documentSnapshot.getString("barName"));

                    Log.d("SearchActivity", "onSuccess: " + barNames.size());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("SearchActivity", "onFailure: " + e.toString());
            }
        });

        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation_search_activity);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_order: {
                                startActivity(new Intent(SearchActivity.this, MyOrderActivity.class));
                                finish();
                                break;
                            }

                            case R.id.action_search: {
                                break;
                            }
                        }
                        return true;
                    }
                });
    }



    private void setUpRecyclerView() {
        com.google.firebase.firestore.Query query = reference.whereEqualTo("privacy", "publish").orderBy("barName", com.google.firebase.firestore.Query.Direction.ASCENDING);

//        barNames = new ArrayList<>();
//        barNames.add(query.toString());

        FirestoreRecyclerOptions<SearchFirestore> options = new FirestoreRecyclerOptions.Builder<SearchFirestore>()
                .setQuery(query, SearchFirestore.class)
                .build();

//        Log.d("SearchActivity", "setUpRecyclerView: BarNames: " + barNames.size() + barNames.get(0));

        adapter = new SearchAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_search_activity);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        adapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshots, int position) {
                SearchFirestore searchFirestore = documentSnapshots.toObject(SearchFirestore.class);
                String id = documentSnapshots.getId();
                String barName = searchFirestore.getBarName();
                String path = documentSnapshots.getReference().getPath();
                documentSnapshots.getReference().getId();
               // Toast.makeText(SearchActivity.this, "Position: " + position + " , " + "ID: " + id , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SearchActivity.this, SearchDetailActivity.class);
                intent.putExtra("path", id);
                intent.putExtra("barName", barName);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.onActionViewExpanded();
        searchView.setQueryHint("Search with bar name");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //String searchWord;
                //query1 = query.trim();

                Log.d("SearchActivity", "onQueryTextSubmit: " + query1 );
                return true;
            }

            @Override
            public boolean onQueryTextChange( String newText) {

                String userInput = newText.toLowerCase().trim();
                List<String> list = new ArrayList<>();

                for (String barName : barNames)
                {
                    if (barName.toLowerCase().contains(userInput))
                    {
                        list.add(barName);
                    }
                }

                adapter.updateList(list);

//                Index index = client.getIndex("recipe");
//                com.algolia.search.saas.Query query = new com.algolia.search.saas.Query(newText)
//                        .setAttributesToRetrieve("barName", "privacy", "uid")
//                        .setHitsPerPage(50);
//                index.searchAsync(query, new CompletionHandler() {
//                    @Override
//                    public void requestCompleted(JSONObject content, AlgoliaException error) {
//                        try {
//                            JSONArray hits = content.getJSONArray("hits");
//                            List<String> list = new ArrayList<>();
//                            for (int i=0; i<hits.length(); i++)
//                            {
//                                JSONObject jsonObject = hits.getJSONObject(i);
//                                String barName = jsonObject.getString("barName");
//                                list.add(barName);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        Log.d("SearchActivity", "requestCompleted: Algoli Content = " + content);
//                    }
//                });
                return true;
            }

        });
        return true;
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
