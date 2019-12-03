package com.example.downloadingwebcontent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
public class DownloadTask extends AsyncTask<String,Void,String>
{

    @Override
    protected String doInBackground(String... strings) {

        Log.i("URL",strings[0]);
        String result = "";
        URL url;
        HttpURLConnection connection= null;
        try {

            url= new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
           
            InputStream input = connection.getInputStream();

            InputStreamReader reader = new InputStreamReader(input);
            int data = reader.read();
            while (data!=-1){
                char current = (char) data;
                result+=current;
                data=reader.read();

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadTask downloadTask = new DownloadTask();
        String result = null;
        try {
            result = downloadTask.execute("http://www.google.com").get();
        }catch (ExecutionException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("result",result);
    }
}

