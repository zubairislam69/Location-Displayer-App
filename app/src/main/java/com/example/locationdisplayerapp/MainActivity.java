package com.example.locationdisplayerapp;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.SearchView;


import android.os.Bundle;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    SearchView svSearchLocation;
    FloatingActionButton fabAddLocation;

    ListView lvLocations;

    List<Location> allLocations;

    private final DataBaseHelper databaseHelper = new DataBaseHelper(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        svSearchLocation = findViewById(R.id.svSearchLocation);
        fabAddLocation = findViewById(R.id.fabAddLocation);
        lvLocations = findViewById(R.id.lvLocations);

        allLocations = databaseHelper.getAllData();

        LocationAdapter adapter = new LocationAdapter(this, allLocations);

        lvLocations.setAdapter(adapter);

    }
}