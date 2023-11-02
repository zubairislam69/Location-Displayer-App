package com.example.locationdisplayerapp;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * An ArrayAdapter for displaying a list of Location objects in a custom layout.
 */
public class LocationAdapter extends ArrayAdapter<Location> {

    // Interface for the edit button event listener
    public interface OnEditClickListener {
        void onEditClick(Location location);
    }

    // Interface for the delete button event listener
    public interface OnDeleteClickListener {
        void onDeleteClick(Location location);
    }

    private final OnEditClickListener onEditClickListener;

    private final OnDeleteClickListener onDeleteClickListener;

    TextView tvAddress, tvLatitude, tvLongitude;

    FloatingActionButton fabDelete, fabEdit;

    /**
     * Constructs a new LocationAdapter.
     *
     * @param context            The current context.
     * @param locations          The list of Location objects to display.
     * @param onEditClickListener The event listener for the edit button.
     * @param onDeleteClickListener The event listener for the delete button.
     */
    public LocationAdapter(Context context, List<Location> locations, OnEditClickListener onEditClickListener, OnDeleteClickListener onDeleteClickListener) {
        super(context, 0, locations);
        this.onEditClickListener = onEditClickListener;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    /**
     * Get a view that displays data at a specific position in the adapter.
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The recycled view to populate.
     * @param parent      The parent view that this view will eventually be attached to.
     * @return The view for the position in the adapter.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_location_adapter, parent, false);
        }

        Location location = getItem(position);

        tvAddress = convertView.findViewById(R.id.tvAddress);
        tvLatitude = convertView.findViewById(R.id.tvLatitude);
        tvLongitude = convertView.findViewById(R.id.tvLongitude);

        tvAddress.setText(location.getAddress());
        tvLatitude.setText("Latitude: " + String.valueOf(location.getLatitude()));
        tvLongitude.setText("Longitude: " + String.valueOf(location.getLongitude()));

        fabDelete = convertView.findViewById(R.id.fabDelete);
        fabEdit = convertView.findViewById(R.id.fabEdit);

        // Edit fab event listener. The logic will be coded in the main activity.
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditClickListener.onEditClick(location);
            }
        });

        // Delete fab event listener. The logic will be coded in the main activity.
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteClickListener.onDeleteClick(location);
            }
        });

        return convertView;
    }

}