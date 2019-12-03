package com.example.addinganewactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public void jumpSecondActivity(View v){
        //Activity activity = new Activity();
     //   activity.setContentView(R.layout.activity_new);
        Intent intent = new Intent(getApplicationContext(),newActivity.class);
        intent.putExtra("string","data");
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView list = (ListView) findViewById(R.id.list);
        final List<String> friends = new ArrayList<String>();
        friends.add("arrr");
        friends.add("bbbbb");
        friends.add("cccc");
        friends.add("ddddd");
        friends.add("essa");
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,friends);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(),newActivity.class);
                    intent.putExtra("item",friends.get(i).toString());
                    startActivity(intent);
            }
        });
    }
}
