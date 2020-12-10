package com.prashant.mywikipediaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.prashant.mywikipediaapp.Adapter.FeaturedImagesAdapter;
import com.prashant.mywikipediaapp.Adapter.RandomArticlesAdapter;
import com.prashant.mywikipediaapp.Common.ApiInterface;
import com.prashant.mywikipediaapp.Model.AllCategories;
import com.prashant.mywikipediaapp.Model.CategoryModel;
import com.prashant.mywikipediaapp.Model.ContinueModel;
import com.prashant.mywikipediaapp.Model.FeaturedImagesModel;
import com.prashant.mywikipediaapp.Model.ImageInfo;
import com.prashant.mywikipediaapp.Model.RandomArticles;
import com.prashant.mywikipediaapp.Model.Revisions;
import com.prashant.mywikipediaapp.Model.SearchModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeLayout;
    private ContinueModel continueModel;
    private ArrayList<RandomArticles> randomArticlesArrayList;
    private ArrayList<RandomArticles> featuredImagesModelArrayList;
    private ArrayList<RandomArticles> categoryList;
    private AutoCompleteTextView autoCompleteTextView;
    private Button searchButton;
    private ImageView drawerImageview;
    private DrawerLayout drawer_layout;
    private NavigationView nav_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);

        nav_view = findViewById(R.id.nav_view);
        drawer_layout = findViewById(R.id.drawer_layout);
        drawerImageview = findViewById(R.id.drawerImageview);
        searchButton = findViewById(R.id.search_button);
        autoCompleteTextView = findViewById(R.id.search_autocomplete_textview);
        swipeLayout = findViewById(R.id.swipeLayout);
        recyclerView = findViewById(R.id.recycler);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        nav_view.setNavigationItemSelectedListener(this);
//        progressDialog.show();

        if (isNetworkAvailable()){

            FetchWikiDataAsync fetchFeaturedImagesAsync = new FetchWikiDataAsync();
            fetchFeaturedImagesAsync.execute();

        }else {
            Toast.makeText(this, "No Internet Available", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomePageActivity.this, SavedActivity.class));
            finish();
        }

//        getHomeData();


        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FetchWikiDataAsync fetchFeaturedImagesAsync = new FetchWikiDataAsync();
                fetchFeaturedImagesAsync.execute();
                swipeLayout.setRefreshing(false);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = "";
                searchText = autoCompleteTextView.getText().toString();
                Intent intent = new Intent(HomePageActivity.this, MainActivity.class);
                intent.putExtra("searchText", searchText);
                startActivity(intent);
                autoCompleteTextView.setText("");
            }
        });

        drawerImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer_layout.isDrawerOpen(nav_view)){
                    drawer_layout.closeDrawer(nav_view);
                    return;
                }
                drawer_layout.openDrawer(nav_view);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_search:
                startActivity(new Intent(HomePageActivity.this, MainActivity.class));
                break;
            case R.id.nav_saved:
                startActivity(new Intent(HomePageActivity.this, SavedActivity.class));
                break;
            case R.id.nav_settings:
                startActivity(new Intent(HomePageActivity.this, MainActivity.class));
                break;

        }
        drawer_layout.closeDrawer(nav_view);
        return false;
    }

    private class FetchWikiDataAsync extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
            Toast.makeText(HomePageActivity.this, "Fetching Data. Please wait.", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String[] params) {
            getCategoryList();
            return "";

        }

        @Override
        protected void onPostExecute(String formattedData) {
            super.onPostExecute(formattedData);
//            progressDialog.hide();
        }
    }

    private void getRandomArticles() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiInterface.getRandomArticlesApi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            try {
//                                Log.e("RESPONSE     ", response);
                                JSONObject jsonObj = new JSONObject(response);
//                                String status = jsonObj.getString("code");
//                                String message = jsonObj.getString("message");
                                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                JsonParser jp = new JsonParser();
                                JsonElement je = jp.parse(response);
                                String prettyJsonString = gson.toJson(je);
                                Log.e("randomResponse     ", prettyJsonString);

//                                JSONObject continueObject = jsonObj.getJSONObject("continue");
//                                ContinueModel continueModel = new ContinueModel();
//                                continueModel.setContinue_str(continueObject.getString("continue"));
//                                continueModel.setGcmcontinue(continueObject.getString("gcmcontinue"));

                                randomArticlesArrayList = new ArrayList<>();
                                JSONObject query = jsonObj.getJSONObject("query");
                                JSONObject pages = query.getJSONObject("pages");

                                Iterator<String> keys = pages.keys();

                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    Log.v("**********", "**********");
                                    Log.v("category key", key);

                                    JSONObject jsonObject = pages.getJSONObject(key);

                                    Log.v("**********", jsonObject.getString("title"));
                                    RandomArticles randomArticles = new RandomArticles();
                                    randomArticles.setPageId(jsonObject.getString("pageid"));
                                    randomArticles.setTitle(jsonObject.getString("title"));
                                    randomArticles.setExtract(jsonObject.optString("extract"));
                                    randomArticles.setType("Random Article");

                                    randomArticlesArrayList.add(randomArticles);
                                }

                                getHomeData();

//                                recyclerView.setLayoutManager(new LinearLayoutManager(HomePageActivity.this, LinearLayoutManager.VERTICAL, false));
//                                RandomArticlesAdapter professionalServicesAdapter = new RandomArticlesAdapter(HomePageActivity.this, randomArticlesArrayList);
//                                recyclerView.setAdapter(professionalServicesAdapter);
//                                Log.e("Adpter==", "CALLED");
//                                Log.e("Adpter==", String.valueOf(randomArticlesArrayList.size()));

//                                if ((progressDialog != null) && progressDialog.isShowing()) {
//                                    progressDialog.dismiss();
//                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
//                                if ((progressDialog != null) && progressDialog.isShowing()) {
//                                    progressDialog.dismiss();
//                                }
                            }
                        } else {
//                            if ((progressDialog != null) && progressDialog.isShowing()) {
//                                progressDialog.dismiss();
//                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if ((progressDialog != null) && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                Log.e("params   ", params.toString());
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void getCategoryList() {

        String lastSearched = "";

        lastSearched = ApiInterface.getPreference(HomePageActivity.this,"lastSearch");

        System.out.println("lastSearchedString-->" + lastSearched);
        System.out.println("CategoryListApi-->" + Request.Method.GET + ApiInterface.getCategoryListApi + lastSearched);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiInterface.getCategoryListApi + lastSearched,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            try {
//                                Log.e("RESPONSE     ", response);
                                JSONObject jsonObj = new JSONObject(response);
//                                String status = jsonObj.getString("code");
//                                String message = jsonObj.getString("message");
                                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                JsonParser jp = new JsonParser();
                                JsonElement je = jp.parse(response);
                                String prettyJsonString = gson.toJson(je);
                                Log.e("categoryResponse     ", prettyJsonString);

                                categoryList = new ArrayList<>();

                                RandomArticles catModel = new RandomArticles();

                                JSONObject query = jsonObj.getJSONObject("query");
                                JSONArray allcategories = query.getJSONArray("allcategories");
                                ArrayList<AllCategories> allCategoriesArrayList = new ArrayList<>();
                                StringBuilder categoryStrings = new StringBuilder();

                                for (int i = 0; i < allcategories.length(); i++) {
                                    JSONObject catObj = allcategories.getJSONObject(i);
                                    AllCategories allCategoriesModel = new AllCategories();
                                    allCategoriesModel.setCategory(catObj.getString("category"));
                                    allCategoriesArrayList.add(allCategoriesModel);
                                    categoryStrings.append(catObj.getString("category")+"\n");

                                }
                                catModel.setType("Category List");
                                catModel.setCategoryModelArrayList(allCategoriesArrayList);
                                catModel.setCategory(String.valueOf(categoryStrings));

                                categoryList.add(catModel);
                                getRandomArticles();

                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if ((progressDialog != null) && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                Log.e("params   ", params.toString());
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void getHomeData() {

        String continueStr = "";
        String gcmcontinue = "";
        if (continueModel != null) {
            if (continueModel.getContinue_str() != null && !continueModel.getContinue_str().equals("")) {
                continueStr = continueModel.getContinue_str();
            }
            if (continueModel.getGcmcontinue() != null && !continueModel.getGcmcontinue().equals("")) {
                gcmcontinue = continueModel.getGcmcontinue();
            }
        }

        Log.e("StringRequest-->", ApiInterface.getFeaturedImagesApi + "&continue=" + continueStr +
                "&gcmcontinue=" + gcmcontinue);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiInterface.getFeaturedImagesApi + "&continue=" + continueStr +
                "&gcmcontinue=" + gcmcontinue,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            try {
//                                Log.e("RESPONSE     ", response);
                                JSONObject jsonObj = new JSONObject(response);
//                                String status = jsonObj.getString("code");
//                                String message = jsonObj.getString("message");
                                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                JsonParser jp = new JsonParser();
                                JsonElement je = jp.parse(response);
                                String prettyJsonString = gson.toJson(je);
                                Log.e("RESPONSE     ", prettyJsonString);

                                JSONObject continueObject = jsonObj.getJSONObject("continue");
                                ContinueModel continueModel = new ContinueModel();
                                continueModel.setContinue_str(continueObject.getString("continue"));
                                continueModel.setGcmcontinue(continueObject.getString("gcmcontinue"));

                                featuredImagesModelArrayList = new ArrayList<>();
                                JSONObject query = jsonObj.getJSONObject("query");
                                JSONObject pages = query.getJSONObject("pages");

                                Iterator<String> keys = pages.keys();

                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    Log.v("**********", "**********");
                                    Log.v("category key", key);

                                    JSONObject jsonObject = pages.getJSONObject(key);
                                    RandomArticles randomArticles = new RandomArticles();
                                    randomArticles.setPageId(jsonObject.getString("pageid"));
                                    randomArticles.setTitle(jsonObject.getString("title").replace("File:", "").replace(".jpg", ""));
                                    randomArticles.setType("Featured Image");

                                    JSONArray imageinfo = jsonObject.getJSONArray("imageinfo");

                                    ArrayList<ImageInfo> imageInfoArrayList = new ArrayList<>();
                                    for (int k = 0; k < imageinfo.length(); k++) {
                                        JSONObject dataObject = imageinfo.getJSONObject(k);
                                        randomArticles.setImageUrl(dataObject.getString("url"));
                                        ImageInfo imageInfo = new ImageInfo();
                                        imageInfo.setTimestamp(dataObject.getString("timestamp"));
                                        imageInfo.setUser(dataObject.getString("user"));
                                        imageInfo.setUrl(dataObject.getString("url"));
                                        imageInfo.setDescriptionurl(dataObject.getString("descriptionurl"));
                                        imageInfo.setDescriptionshorturl(dataObject.getString("descriptionshorturl"));
                                        imageInfoArrayList.add(imageInfo);
                                    }
                                    randomArticles.setImageInfoList(imageInfoArrayList);
                                    featuredImagesModelArrayList.add(randomArticles);
                                }

                                if ((progressDialog != null) && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                CombineArrayListAndShow();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                if ((progressDialog != null) && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }
                        } else {
                            if ((progressDialog != null) && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if ((progressDialog != null) && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                Log.e("params   ", params.toString());
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }

    private void CombineArrayListAndShow() {

        ArrayList<RandomArticles> NewList = new ArrayList<>();
        if (randomArticlesArrayList != null && featuredImagesModelArrayList != null) {
            if (randomArticlesArrayList != null && randomArticlesArrayList.size() != 0) {
                NewList.addAll(randomArticlesArrayList);
            }
            if (featuredImagesModelArrayList != null && featuredImagesModelArrayList.size() != 0) {
                NewList.addAll(featuredImagesModelArrayList);
            }
            if (categoryList != null && categoryList.size() != 0) {
                NewList.addAll(categoryList);
            }
            if (NewList!=null && NewList.size()!=0) {
                Collections.shuffle(NewList);
                recyclerView.setLayoutManager(new LinearLayoutManager(HomePageActivity.this, LinearLayoutManager.VERTICAL, false));
                RandomArticlesAdapter professionalServicesAdapter = new RandomArticlesAdapter(HomePageActivity.this, NewList);
                recyclerView.setAdapter(professionalServicesAdapter);
                Log.e("Adpter==", "CALLED");
                Log.e("Adpter==", String.valueOf(NewList.size()));
            }

        }
    }

}
