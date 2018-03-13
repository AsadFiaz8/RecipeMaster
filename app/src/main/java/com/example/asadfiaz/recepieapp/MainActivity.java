package com.example.asadfiaz.recepieapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.rey.material.drawable.CircleDrawable;
import com.rey.material.drawable.CircularProgressDrawable;
import com.rey.material.widget.ProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView searchResultList;
    TextView txtSearchItem;
    ProgressView progressView;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        setTitle("Recipe Master");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MobileAds.initialize(this, "ca-app-pub-5437498675074247~4206613567");
        adView= (AdView) findViewById(R.id.adView);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        txtSearchItem = (TextView) findViewById(R.id.txtSerachItem);
        Typeface font=Typeface.createFromAsset(getAssets(),"fonts/kottaone-regular.ttf");
        txtSearchItem.setTypeface(font);



        progressView= (ProgressView) findViewById(R.id.progressView);

        progressView.setVisibility(View.INVISIBLE);



        searchResultList = (ListView) findViewById(R.id.listView);
        searchResultList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String url = ((TextView) view.findViewById(R.id.url)).getText().toString();
                        url = url.substring(5);


                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(i);
                    }
                }
        );


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu, menu);
        MenuItem searchBar = menu.findItem(R.id.menuSearch);

        SearchView searchView = (SearchView) searchBar.getActionView();
        searchView.setQueryHint("Search...");
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {

                        txtSearchItem.setVisibility(View.GONE);
                        searchResultList.setVisibility(View.VISIBLE);
                        query = query.replaceAll("\\s+", "%20");
                        String API_URL = "http://www.recipepuppy.com/api/?q=";
                        String jsonURL = API_URL + query;

                        progressView.setVisibility(View.VISIBLE);

                        progressView.start();

                        JsonObjectRequest request = new JsonObjectRequest(
                                com.android.volley.Request.Method.GET, jsonURL, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        ArrayList<RecipeModel> recipeModellist = new ArrayList<RecipeModel>();

                                        try {

                                            JSONArray array = response.getJSONArray("results");

                                            for (int i = 0; i < array.length(); i++) {

                                                JSONObject object = array.getJSONObject(i);

                                                RecipeModel recipemodel = new RecipeModel();

                                                recipemodel.setTitle(object.getString("title"));
                                                recipemodel.setIngredients(object.getString("ingredients"));
                                                recipemodel.setUrl(object.getString("href"));
                                                recipemodel.setPoster(object.getString("thumbnail"));

                                                recipeModellist.add(recipemodel);

                                                RecipeListAdapter adapter = new RecipeListAdapter(MainActivity.this, R.layout.custom_row_list, recipeModellist);
                                                searchResultList.setAdapter(adapter);

                                                progressView.stop();
                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        );
                        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                        queue.add(request);

                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                }
        );

        return super.onCreateOptionsMenu(menu);
    }
}














