package com.example.zatest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
class download extends AsyncTask<String,Void,String>{

    @Override
    protected String doInBackground(String... strings) {
        String string="";
        for(int i=0;i<10000;i++){
            if(i%2==0){
                string+=Integer.toString(i);
            }
        }
        return string;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.i("InPostExecute","InPost");
    }
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            String dw = new download().execute("a").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("InMain","in my activity");
    }
}
