package com.example.locationdisplayerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;


import android.os.Bundle;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
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

        LocationAdapter adapter = new LocationAdapter(this, allLocations, new LocationAdapter.OnEditClickListener() {
            @Override
            public void onEditClick(Location location) {

                Log.d("edit", "YOOOOOOOOOOO");
                Intent intent = new Intent(MainActivity.this, LocationEditer.class);

                intent.putExtra("location_id", location.getId());
                intent.putExtra("location_address", location.getAddress());
                intent.putExtra("location_latitude", location.getLatitude());
                intent.putExtra("location_longitude", location.getLongitude());

                startActivity(intent);
            }
        }, new LocationAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(Location location) {

            }
        });

        lvLocations.setAdapter(adapter);

        fabAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddLocation.class);
                startActivity(intent);
            }
        });

        svSearchLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterLocations(newText, new LocationAdapter.OnEditClickListener() {
                    @Override
                    public void onEditClick(Location location) {
                        Intent intent = new Intent(MainActivity.this, LocationEditer.class);

                        intent.putExtra("location_id", location.getId());
                        intent.putExtra("location_address", location.getAddress());
                        intent.putExtra("location_latitude", location.getLatitude());
                        intent.putExtra("location_longitude", location.getLongitude());

                        startActivity(intent);
                    }
                }, new LocationAdapter.OnDeleteClickListener() {
                    @Override
                    public void onDeleteClick(Location location) {

                    }
                });
                return false;
            }
        });
    }

    public void filterLocations(String query, LocationAdapter.OnEditClickListener onEditClickListener, LocationAdapter.OnDeleteClickListener onDeleteClickListener) {
        List<Location> filteredNotes = new ArrayList<>();

        for (Location location : allLocations) {
            if (location.getAddress().toLowerCase().contains(query.toLowerCase())) {
                filteredNotes.add(location);
            }
        }

        // Update the ListView with the filtered list of notes
        LocationAdapter adapter = new LocationAdapter(this, filteredNotes, onEditClickListener, onDeleteClickListener);
        lvLocations.setAdapter(adapter);
    }
}