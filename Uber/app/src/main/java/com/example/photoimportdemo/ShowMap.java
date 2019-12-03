package com.example.photoimportdemo;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseGeoPoint;

public class ShowMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ParseGeoPoint geoPoint;
    LatLng driverCurrentLocation;
    public void navigate(View v){
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr="+Double.toString(geoPoint.getLatitude())+
                        ","+Double.toString(geoPoint.getLongitude())+"&daddr="+
                        Double.toString(driverCurrentLocation.latitude)+","+
                        Double.toString(driverCurrentLocation.longitude)));
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);
        Intent intent = getIntent();
        geoPoint = intent.getParcelableExtra("ParseGeo");
        driverCurrentLocation = intent.getParcelableExtra("CurrentLocation");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        final LatLng riderLatLng = new LatLng(geoPoint.getLatitude(),geoPoint.getLongitude());
        mMap.addMarker(new MarkerOptions().position(riderLatLng).title("Rider Point"));
        mMap.addMarker(new MarkerOptions().position(driverCurrentLocation).title("My Location"));
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                LatLngBounds bounds = new LatLngBounds(riderLatLng,driverCurrentLocation);
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds,10));
            }
        });

    }
}
