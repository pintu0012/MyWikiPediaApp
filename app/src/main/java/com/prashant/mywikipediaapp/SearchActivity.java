package com.prashant.mywikipediaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.prashant.mywikipediaapp.Common.ApiInterface;
import com.prashant.mywikipediaapp.Model.SearchModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchActivity extends AppCompatActivity {
    private Button btnFetchData;
    private TextView txtWikiData;
    private ProgressBar progressBar;
    private EditText etxSearch;
    private String searchText="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
// add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Search");
        }
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        etxSearch = (EditText) findViewById(R.id.etxSearch);
        txtWikiData = (TextView) findViewById(R.id.txtWikiData);

        if (getIntent().getExtras()!=null){
            searchText = getIntent().getStringExtra("searchText");
            if (!searchText.equals("")){
                etxSearch.setText(searchText);
                fetchDataFromWiki(searchText);
            }
        }
        btnFetchData = (Button) findViewById(R.id.btnFetchData);
        btnFetchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = etxSearch.getText().toString();
                fetchDataFromWiki(keyword);
            }
        });
    }




    private void fetchDataFromWiki(String keyword) {

        ApiInterface.setPreference(SearchActivity.this,"lastSearch",keyword);

        String WIKIPEDIA_URL = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro&explaintext&redirects=1&titles="+keyword;

        // Start AsyncTask
        Log.e("wikipedimUrl==>", WIKIPEDIA_URL);
        FetchWikiDataAsync fetchWikiDataAsync = new FetchWikiDataAsync();
        fetchWikiDataAsync.execute(WIKIPEDIA_URL);

    }

    private class FetchWikiDataAsync extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(SearchActivity.this, "Fetching Data. Please wait.", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String[] params) {
            try {
                String sURL = params[0];

                URL url = new URL(sURL);        // Convert String URL to java.net.URL
                // Connection: to Wikipedia API
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                String wikiData = stringBuilder.toString();

                // Parse JSON Data
                String formattedData = parseJSONData(wikiData);

                return formattedData;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String formattedData) {
            super.onPostExecute(formattedData);
            progressBar.setVisibility(View.GONE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // HTML Data
                txtWikiData.setText(Html.fromHtml
                        (formattedData, Html.FROM_HTML_MODE_LEGACY));
            } else {
                // HTML Data
                txtWikiData.setText(Html.fromHtml(formattedData));
            }
        }
    }

    private String parseJSONData(String wikiData) {
        try {
            // Convert String JSON (wikiData) to JSON Object
            JSONObject rootJSON = new JSONObject(wikiData);
            JSONObject query = rootJSON.optJSONObject("query");
            JSONObject pages = query.optJSONObject("pages");
            JSONObject number = pages.getJSONObject(pages.keys().next());
            if (number.has("extract")) {
                String formattedData = number.optString("extract");
                return formattedData;
            }
            else
                return "No data found";
        } catch (JSONException json) {
            json.printStackTrace();
        }

        return null;
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