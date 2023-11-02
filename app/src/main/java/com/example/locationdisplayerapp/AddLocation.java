package com.example.locationdisplayerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddLocation extends AppCompatActivity {

    Button btnGoBack, btnSave;

//    EditText etAddress, etLatitude, etLongitude;
    TextInputEditText etAddress, etLatitude, etLongitude;


    private final DataBaseHelper databaseHelper = new DataBaseHelper(AddLocation.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        etAddress = findViewById(R.id.etAddress);
        etLatitude = findViewById(R.id.etLatitude);
        etLongitude = findViewById(R.id.etLongitude);


        btnGoBack = findViewById(R.id.btnGoBack);
        btnSave = findViewById(R.id.btnSave);

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddLocation.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String address = etAddress.getText().toString();
                String latitudeString = etLatitude.getText().toString();
                String longitudeString = etLongitude.getText().toString();

                if (!address.isEmpty() && !latitudeString.isEmpty() && !longitudeString.isEmpty()) {
                    double latitude = Double.parseDouble(latitudeString);
                    double longitude = Double.parseDouble(longitudeString);

                    databaseHelper.addLocation(address, latitude, longitude);

                }




                Intent intent = new Intent(AddLocation.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}