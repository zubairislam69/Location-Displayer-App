package com.example.locationdisplayerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class AddLocation extends AppCompatActivity {

    Button btnGoBack, btnSave;

    TextInputEditText etAddress, etLatitude, etLongitude;


    private final DataBaseHelper databaseHelper = new DataBaseHelper(AddLocation.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        // START FIND VIEWS
        etAddress = findViewById(R.id.etAddress);
        etLatitude = findViewById(R.id.etLatitude);
        etLongitude = findViewById(R.id.etLongitude);

        btnGoBack = findViewById(R.id.btnGoBack);
        btnSave = findViewById(R.id.btnSave);
        // END FIND VIEWS

        // Event listener for the back button. This will redirect to home screen.
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddLocation.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Event listener for the save button. This will save location to DB and redirect back to home screen.
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get the user's input
                String address = etAddress.getText().toString();
                String latitudeString = etLatitude.getText().toString();
                String longitudeString = etLongitude.getText().toString();

                // if input is not empty, add to DB
                if (!address.isEmpty() && !latitudeString.isEmpty() && !longitudeString.isEmpty()) {
                    double latitude = Double.parseDouble(latitudeString);
                    double longitude = Double.parseDouble(longitudeString);

                    databaseHelper.addLocation(address, latitude, longitude);
                }

                // Go to home screen
                Intent intent = new Intent(AddLocation.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}