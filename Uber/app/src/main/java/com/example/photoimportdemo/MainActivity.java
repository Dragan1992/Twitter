package com.example.photoimportdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class MainActivity extends AppCompatActivity {
public void onClickStarted(View v){
    Switch aSwitch = findViewById(R.id.swi);
    String state = aSwitch.getText().toString();
    Log.i("ValueSwitch",state);
    if(!aSwitch.isChecked()){
        Log.i("State","Rider");
        Intent intent = new Intent(this,RiderActivity.class);
        startActivity(intent);
    }
    else if(aSwitch.isChecked()){
        Log.i("State","Driver");
        Intent intent = new Intent(this,DriverActivity.class);
        startActivity(intent);
    }
}
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Uber");
        getSupportActionBar().hide();
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("dd455e7b6036347899b6922c173a070c338d452b")
                .clientKey("03f8a51d0981226da3cb54138c7b5c98897c0135")
                .server("http://104.211.54.192:80/parse/")
                .build()
        );
    if(ParseUser.getCurrentUser()==null){
        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user!=null && e!=null)
                Log.i("LoginResult","Succesful");
            }
        });
    }


        ParseAnalytics.trackAppOpenedInBackground(getIntent());



    }}
