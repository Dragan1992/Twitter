package com.example.photoimportdemo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import bolts.Task;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriverActivity extends AppCompatActivity {
    LocationListener locationListener;
    LocationManager manager;
    double lat,lng;
    List<ParseGeoPoint> list;
    List<String> listId;
    ArrayAdapter<String> adapter;
    ListView listView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0 && permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Location l = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                lat=l.getLatitude();
                lng=l.getLongitude();
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }


    public void findRequests() throws ParseException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Riders");
        List<ParseObject> result = query.find();
        for(int i=0;i<result.size();i++){
            list.add(result.get(i).getParseGeoPoint("GeoPoint"));
            listId.add(result.get(i).getString("username"));
        }
        Log.i("FindDone","find method is done");
    }
    public List<String> calculateDistance(){
        ParseGeoPoint driverGeoPoint = new ParseGeoPoint();
        driverGeoPoint.setLongitude(lng);
        driverGeoPoint.setLatitude(lat);
        Log.i("lng",Double.toString(lng));
        Log.i("lat",Double.toString(lat));
        List<String> result= new ArrayList<>();
        for(int i=0;i<list.size();i++){
            double distance =driverGeoPoint.distanceInKilometersTo(list.get(i));
            result.add(String.format("%.1f",distance));
        }
        Log.i("CalculateDone","calculate is done");
        return result;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        setTitle("Nearby Requests");
        list = new ArrayList<>();
        listId = new ArrayList<>();
        listView = findViewById(R.id.list);
        locationListener= new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                lng = location.getLongitude();
                adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,calculateDistance());
                adapter.notifyDataSetChanged();
               listView.setAdapter(adapter);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);

        }else{
            Location l = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            lat=l.getLatitude();
            lng=l.getLongitude();
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        try {
            findRequests();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i("CalculateSize",Integer.toString(calculateDistance().size()));
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,calculateDistance());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ShowMap.class);
                intent.putExtra("ParseGeo",list.get(position));
                intent.putExtra("CurrentLocation",new LatLng(lat,lng));
                startActivity(intent);
            }
        });
    }
}
