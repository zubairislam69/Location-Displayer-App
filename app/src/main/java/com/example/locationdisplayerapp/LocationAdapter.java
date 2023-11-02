package com.example.locationdisplayerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class LocationAdapter extends ArrayAdapter<Location> {

    public interface OnEditClickListener {
        void onEditClick(Location location);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Location location);
    }

    private final OnEditClickListener onEditClickListener;

    private final OnDeleteClickListener onDeleteClickListener;

    TextView tvAddress, tvLatitude, tvLongitude;

    FloatingActionButton fabDelete, fabEdit;
    public LocationAdapter(Context context, List<Location> locations, OnEditClickListener onEditClickListener, OnDeleteClickListener onDeleteClickListener) {
        super(context, 0, locations);
        this.onEditClickListener = onEditClickListener;
        this.onDeleteClickListener = onDeleteClickListener;
    }

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
        tvLatitude.setText(String.valueOf(location.getLatitude()));
        tvLongitude.setText(String.valueOf(location.getLongitude()));

        fabDelete = convertView.findViewById(R.id.fabDelete);
        fabEdit = convertView.findViewById(R.id.fabEdit);

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditClickListener.onEditClick(location);
            }
        });

        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(LocationAdapter.this, );
                onDeleteClickListener.onDeleteClick(location);
//                Log.d("delete", "id: " + location.getId());


            }
        });

        return convertView;
    }

    public void updateData(List<Location> updatedLocations) {

        Log.d("update data", "called");
        clear(); // Clear the existing data in the adapter
        addAll(updatedLocations); // Add the updated data
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }


}