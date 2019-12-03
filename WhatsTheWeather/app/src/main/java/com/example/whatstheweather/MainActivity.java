package com.example.whatstheweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
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

public class MainActivity extends AppCompatActivity {
 class DownloadTask extends AsyncTask<String,Void,String>
{

    @Override
    protected String doInBackground(String... strings) {
        URL url = null;
        HttpURLConnection urlConnection= null;
        String result = new String("");
        try {
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream input = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(input);
            int read = reader.read();

            while(read!=-1)
            {
                char c= (char) read;
                result+="";
                read = reader.read();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject object = new JSONObject(s);
            String weatherInfo = object.getString("weather");
            Log.i("Weather content",weatherInfo);
            JSONArray arr=new JSONArray(weatherInfo);
            for(int i=0;i<arr.length();i++){
                JSONObject jsonPart = arr.getJSONObject(i);
                Log.i("main",jsonPart.getString("main"));
                Log.i("description", jsonPart.getString("description"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            String s = new DownloadTask().execute("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
