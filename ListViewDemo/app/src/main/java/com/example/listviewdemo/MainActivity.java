package com.example.listviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView myList = (ListView) findViewById(R.id.MyListView);
        EditText eText;
        final List<String> list = new ArrayList<>();
        list.add("Petre");
        list.add("Mitre");
        list.add("Janko");
        list.add("Mile");
        list.add("Pile");
        list.add("Smile");
        list.add("Ratko");
        Toast.makeText(this,"hello",Toast.LENGTH_LONG).show();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        myList.setAdapter(arrayAdapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),list.get(i), Toast.LENGTH_LONG).show();
            }
        });
    }
}
