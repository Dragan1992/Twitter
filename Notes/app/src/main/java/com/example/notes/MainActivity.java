package com.example.notes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public List<String> notes;
    ArrayAdapter<String>  adapter;
    ListView listView;
    public void getData(){
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        try {
            notes= (List<String>) ObjectSerializer.deserialize(sharedPreferences.getString("list",""));
            if(notes==null){
                notes = new ArrayList<String>();
                notes.add("Example note");
                sharedPreferences.edit().putString("list",ObjectSerializer.serialize((Serializable) notes)).apply();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,notes);
        listView.setAdapter(adapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add)
        {
            Intent intent = new Intent(this,EditTextActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        getData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),EditTextActivity.class);
                intent.putExtra("itemValue",notes.get(i));
                intent.putExtra("noItem",i);
                startActivity(intent);

            }

        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("delete?").setMessage("Are you sure to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes",Context.MODE_PRIVATE);
                                try {
                                    List<String> list = (List<String>) ObjectSerializer.deserialize(sharedPreferences.getString("list",""));
                                    list.remove(i);
                                    notes=list;
                                    sharedPreferences.edit().putString("list",ObjectSerializer.serialize((Serializable) list)).apply();
                                    adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,notes);
                                    //adapter.notifyDataSetChanged();
                                    listView.setAdapter(adapter);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).setNegativeButton("No",null).show();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

    }
}
