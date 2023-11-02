package com.example.locationdisplayerapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * A helper class for geocoding operations to retrieve addresses from coordinates.
 */
public class GeocodingHelper {

    private final Geocoder geocoder;

    /**
     * Creates a new GeocodingHelper instance using the provided context.
     *
     * @param context The Android context used to create the Geocoder.
     */
    public GeocodingHelper (Context context) {
        geocoder = new Geocoder(context, Locale.getDefault());
    }

    /**
     * Retrieves an address from latitude and longitude coordinates using geocoding.
     *
     * @param latitude  The latitude coordinate.
     * @param longitude The longitude coordinate.
     * @return The address associated with the given coordinates, or null if no address is found.
     */
    public String getAddressFromCoordinates(double latitude, double longitude) {
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                return address.getAddressLine(0); // Get the first address line
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

