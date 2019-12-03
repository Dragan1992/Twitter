package com.example.sqldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
        SQLiteDatabase database = this.openOrCreateDatabase("users",MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR, age INT(3))");
        database.execSQL("INSERT INTO users (name,age) VALUES ('Dragan',27)");
        database.execSQL("INSERT INTO users(name,age) VALUES('Rob',34)");
        database.execSQL("DELETE FROM users WHERE name= 'Dragan' LIMIT 1 ");
        database.execSQL("CREATE TABLE IF NOT EXISTS newUsers (name VARCHAR,age INTEGER(3), id INTEGER PRIMARY KEY)");
        Cursor c = database.rawQuery("SELECT * FROM users",null);
        int indexName = c.getColumnIndex("name");
        int indexAge = c.getColumnIndex("age");
        c.moveToFirst();
        while(c!=null){
            Log.i("Name",c.getString(indexName));
            Log.i("indexA",Integer.toString(c.getInt(indexAge)));
            c.moveToNext();
        }
    }
        catch (Exception e){
            e.printStackTrace();
        }

        }
}
