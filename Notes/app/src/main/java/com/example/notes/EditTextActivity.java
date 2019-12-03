package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EditTextActivity extends AppCompatActivity {
    EditText editText;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        editText = (EditText) findViewById(R.id.editText);
        intent = getIntent();
        String val=intent.getStringExtra("itemValue");
        if(val!=null)
        editText.setText(val);



    }
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        try {
            List<String>  list= (List<String>) ObjectSerializer.deserialize(sharedPreferences.getString("list",""));
            if(list==null){
                list = new ArrayList<String>();
            }
            int i=intent.getIntExtra("noItem",0);
            if(i==0)
            list.add(editText.getText().toString());
            else{
                list.remove(i);
                list.add(i,editText.getText().toString());
            }
            sharedPreferences.edit().putString("list",ObjectSerializer.serialize((Serializable) list)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
