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

/**
 * The main activity of the Location Displayer App, responsible for displaying and managing location data.
 */
public class MainActivity extends AppCompatActivity {

    SearchView svSearchLocation;
    FloatingActionButton fabAddLocation;

    ListView lvLocations;

    List<Location> allLocations;

    private final DataBaseHelper databaseHelper = new DataBaseHelper(MainActivity.this);

    LocationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        svSearchLocation = findViewById(R.id.svSearchLocation);
        fabAddLocation = findViewById(R.id.fabAddLocation);
        lvLocations = findViewById(R.id.lvLocations);

        // Get all entries from DB and set to list
        allLocations = databaseHelper.getAllData();

        // Set the adapter with all the entries and listeners
       adapter = new LocationAdapter(this, allLocations, new LocationAdapter.OnEditClickListener() {

           // If edit fab is clicked in the list view, send the selected data to the LocationEditor
            @Override
            public void onEditClick(Location location) {

                Intent intent = new Intent(MainActivity.this, LocationEditor.class);

                intent.putExtra("location_id", location.getId());
                intent.putExtra("location_address", location.getAddress());
                intent.putExtra("location_latitude", location.getLatitude());
                intent.putExtra("location_longitude", location.getLongitude());

                startActivity(intent);
            }
        }, new LocationAdapter.OnDeleteClickListener() {

           // If delete fab is clicked in the list view, get the id and delete the location from DB using the id
           @Override
            public void onDeleteClick(Location location) {
                int id = location.getId();

                if (id != -1) {
                    databaseHelper.deleteLocation(id);

                    // Remove the deleted location from the list
                    allLocations.remove(location);

                    // Refresh adapter for updated changes
                    adapter.notifyDataSetChanged();
                }
            }
        });

        // Set the list view using the adapter
        lvLocations.setAdapter(adapter);

        // Add Location fab event listener to go to the add activity
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

            // When text is inputted in search, return the queried locations
            @Override
            public boolean onQueryTextChange(String newText) {
                filterLocations(newText, new LocationAdapter.OnEditClickListener() {

                    // If edit fab is clicked in the list view, send the selected data to the LocationEditor
                    @Override
                    public void onEditClick(Location location) {
                        Intent intent = new Intent(MainActivity.this, LocationEditor.class);

                        intent.putExtra("location_id", location.getId());
                        intent.putExtra("location_address", location.getAddress());
                        intent.putExtra("location_latitude", location.getLatitude());
                        intent.putExtra("location_longitude", location.getLongitude());

                        startActivity(intent);
                    }
                }, new LocationAdapter.OnDeleteClickListener() {

                    // If delete fab is clicked in the list view, get the id and delete the location from DB using the id
                    @Override
                    public void onDeleteClick(Location location) {
                        int id = location.getId();
                        Log.d("id", "id: " + id);
                        if (id != -1) {

                            databaseHelper.deleteLocation(id);

                            // Remove the deleted location from the list
                            allLocations.remove(location);

                            // Refresh adapter for updated changes
                            adapter.notifyDataSetChanged();
                        }

                    }
                });
                return false;
            }
        });
    }

    /**
     * Filters and displays a list of locations based on the provided search query.
     *
     * @param query The search query used to filter locations.
     * @param onEditClickListener The listener for the "Edit" button in the filtered list view.
     * @param onDeleteClickListener The listener for the "Delete" button in the filtered list view.
     */
    public void filterLocations(String query, LocationAdapter.OnEditClickListener onEditClickListener, LocationAdapter.OnDeleteClickListener onDeleteClickListener) {
        List<Location> filteredLocations = new ArrayList<>();

        for (Location location : allLocations) {
            if (location.getAddress().toLowerCase().contains(query.toLowerCase())) {
                filteredLocations.add(location);
            }
        }

        adapter = new LocationAdapter(this, filteredLocations, onEditClickListener, onDeleteClickListener);
        lvLocations.setAdapter(adapter);
    }

}