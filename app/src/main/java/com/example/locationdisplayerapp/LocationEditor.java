package com.example.locationdisplayerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

/**
 * An activity for editing location details and saving changes.
 */
public class LocationEditor extends AppCompatActivity {

    Button btnGoBack, btnSave;

    TextInputEditText etAddress, etLatitude, etLongitude;

    private final DataBaseHelper databaseHelper = new DataBaseHelper(LocationEditor.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_editor);

        etAddress = findViewById(R.id.etAddress);
        etLatitude = findViewById(R.id.etLatitude);
        etLongitude = findViewById(R.id.etLongitude);

        btnGoBack = findViewById(R.id.btnGoBack);
        btnSave = findViewById(R.id.btnSave);


        Intent intent = getIntent();

        // Get the location data from the selected location
        int id = intent.getIntExtra("location_id", -1);
        String address = intent.getStringExtra("location_address");
        double latitude = intent.getDoubleExtra("location_latitude", -1);
        double longitude = intent.getDoubleExtra("location_longitude", -1);


        // Set the fields in the edit screen to the selected location
        etAddress.setText(address);
        etLatitude.setText(String.valueOf(latitude));
        etLongitude.setText(String.valueOf(longitude));


        // Event listener for the back button. This will redirect to home screen.
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationEditor.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Event listener for the save button. This will upload location in DB and redirect back to home screen.
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String address = etAddress.getText().toString();
                String latitudeString = etLatitude.getText().toString();
                String longitudeString = etLongitude.getText().toString();

                Location location;

                if (!address.isEmpty() && !latitudeString.isEmpty() && !longitudeString.isEmpty()) {
                    double latitude = Double.parseDouble(latitudeString);
                    double longitude = Double.parseDouble(longitudeString);

                    location = new Location(id, address, latitude, longitude);

                    databaseHelper.updateLocation(location);
                }

                Intent intent = new Intent(LocationEditor.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}