package com.prashant.mywikipediaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.prashant.mywikipediaapp.Adapter.FeaturedImagesAdapter;
import com.prashant.mywikipediaapp.Adapter.RandomArticlesAdapter;
import com.prashant.mywikipediaapp.Common.SQLiteDatabaseHandler;
import com.prashant.mywikipediaapp.Model.Player;
import com.prashant.mywikipediaapp.Model.RandomArticles;

import java.util.ArrayList;
import java.util.List;

public class SavedActivity extends AppCompatActivity {

    private SQLiteDatabaseHandler db;
    private RecyclerView savedRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
// add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Saved Articles");
        }

        savedRecycler = findViewById(R.id.savedRecycler);
        db = new SQLiteDatabaseHandler(this);

//        db.deleteAll();

        List<RandomArticles> articles = db.allArticles();

        if (articles != null) {

            savedRecycler.setLayoutManager(new LinearLayoutManager(SavedActivity.this, LinearLayoutManager.VERTICAL, true));
            FeaturedImagesAdapter professionalServicesAdapter = new FeaturedImagesAdapter(SavedActivity.this, articles);
            savedRecycler.setAdapter(professionalServicesAdapter);

        }

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
