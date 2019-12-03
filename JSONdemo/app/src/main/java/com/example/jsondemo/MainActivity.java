package com.example.jsondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

class DownloadJSON extends AsyncTask<String,Void,StringBuilder>
{


    @Override
    protected StringBuilder doInBackground(String... strings) {
        HttpURLConnection connection = null;
        InputStream input = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            input = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(input);
            int read;
            read = reader.read();
            while(read!=-1){
                char c= (char) read;
                stringBuilder.append(c);
                read=reader.read();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder;
    }

    @Override
    protected void onPostExecute(StringBuilder s) {
        try {
            JSONObject jsonObject = new JSONObject(s.toString());
            Log.i("json",jsonObject.getString("main"));
            JSONArray jsonArray = new JSONArray(jsonObject.getString("main"));

            for(int i=0;i<jsonObject.length();i++){// ne e tocno davaq greska
                JSONObject object = jsonObject.;
                Log.i("object test", object.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onPostExecute(s);
    }
}
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            StringBuilder stringBuilder = new DownloadJSON().execute("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}