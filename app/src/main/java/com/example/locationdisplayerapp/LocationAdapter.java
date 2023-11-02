package com.example.locationdisplayerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class LocationAdapter extends ArrayAdapter<Location> {

    TextView tvAddress, tvLatitude, tvLongitude;

    public LocationAdapter(Context context, List<Location> locations) {
        super(context, 0, locations);
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
        tvLatitude.setText(location.getLatitude());
        tvLongitude.setText(location.getLongitude());


        return convertView;
    }

}