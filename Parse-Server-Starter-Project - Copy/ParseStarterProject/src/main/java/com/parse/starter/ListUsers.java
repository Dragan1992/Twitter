package com.parse.starter;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ListUsers extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(getApplicationContext()).inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add){
            Intent intent = new Intent(getApplicationContext(),PhotoImport.class);
            intent.putExtra("User",user);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);
        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        Toast.makeText(this,user.toString(),Toast.LENGTH_SHORT).show();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername().toString());
        query.addAscendingOrder("username");
        final List<String> list= new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,list);
        final ListView listView = (ListView) findViewById(R.id.list);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null){
                    if (objects.size() > 0) {

                        for (ParseUser user : objects) {
                            list.add(user.getUsername());
                            Log.i("UseName",user.getUsername());

                        }
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);

                    }

                } else {

                    e.printStackTrace();

                }

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentFeed = new Intent(getApplicationContext(),UserFeed.class);
                intentFeed.putExtra("User",list.get(i));
                startActivity(intentFeed);
            }
        });
        Log.i("UserList",Integer.toString(list.size()));

        }
    }

