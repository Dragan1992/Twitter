package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.sharedpreferences", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username","dragan").apply();
        String username = sharedPreferences.getString("username","");
        List<String> friends = new ArrayList<String>(asList("monica","chendler","victor"));
        try {
            sharedPreferences.edit().putString("list",ObjectSerializer.serialize((Serializable) friends)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            List<String> newFriends = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("list",""));
            Log.i("username",newFriends.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
