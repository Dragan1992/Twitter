package com.example.guessthecelebrity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;

public class MainActivity extends AppCompatActivity {
    List<String> list;
    List<Bitmap> bitmaps;
    int random;
    public void makeQuestion(int index,List<String> names,List<Bitmap> bitmaps)
    {
        int randomButton = new Random().nextInt(4);
        LinearLayout layout = (LinearLayout) findViewById(R.id.buttons);
            Button  TrueButton= (Button) layout.getChildAt(randomButton);
            TrueButton.setText(names.get(index));
            for(int i=0;i<layout.getChildCount();i++){
                if(i!=randomButton){
                    Button FalseButton = (Button) layout.getChildAt(i);

                    int randomFalse= new Random().nextInt(names.size());
                    if(randomFalse==random){
                        while(true){
                            randomFalse= new Random().nextInt(names.size());
                            if(randomFalse!=random){
                                break;
                            }
                        }
                    }
                    FalseButton.setText(names.get(randomFalse));
                }
            }
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmaps.get(index));

    }
    public void onClick(View v){
        Button b = (Button)v;
        String buttonText = b.getText().toString();
        if(buttonText.equals(list.get(random)))
            Toast.makeText(getApplicationContext(),"true",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(),"false",Toast.LENGTH_SHORT).show();
        random = new Random().nextInt(list.size());
        makeQuestion(random,list,bitmaps);
    }
    public class DownloadImages extends AsyncTask<String,Void, Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url = null;
            Bitmap bitmaps = null;
            HttpURLConnection connection;
            try {
                url= new URL(strings[0]);
                connection= (HttpURLConnection) url.openConnection();
                InputStream input = connection.getInputStream();
                bitmaps = BitmapFactory.decodeStream(input);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmaps;
        }
    }
public class DownloadHTML extends AsyncTask<String,Void,String>
{

    @Override
    protected String doInBackground(String... strings) {
        String htmlCode="";
        URL url = null;
        HttpURLConnection connection=null;
        try {
            url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader input = new InputStreamReader(inputStream);
            int data = input.read();
            while(data!=-1){
                char c=(char) data;
                htmlCode+=c;
                data=input.read();

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return htmlCode;
    }
}
public class MenageData{
    String html;
    List<Bitmap> bitmaps;
    public MenageData(String html){
        this.html=html;
        bitmaps = new ArrayList<Bitmap>();
    }
    public List<String> getNames (){
        String[] lines=html.split("\\r?\\n");
        Log.i("lines",Integer.toString(lines.length));
        List<String> names = new ArrayList<String>();
        for(int i=0;i<lines.length;i++){
            //Log.i("printaj",lines[i]);
            if(lines[i].contains("img src=") && lines[i].contains("alt=") && !lines[i].contains("&quot;")){
            String[] pom=lines[i].split(" ");
            String pom2;
            if(pom.length>3)
            pom2=pom[2]+" "+pom[3];
            else
                pom2=pom[2];
            int index1=pom2.indexOf('"')+1;
            int index2=pom2.lastIndexOf('"');
                pom2 = pom2.substring(index1, index2);
               // Log.i("counter", Integer.toString(pom2.length()));
                names.add(pom2);
                try {
                    Bitmap bitmap = new DownloadImages().execute(pom[1].substring(5,pom[1].length()-1)).get();
                    bitmaps.add(bitmap);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return names;
    }
    public List<Bitmap> getBitmaps(){
        return bitmaps;
    }
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String htmlCode="";
        try {
            htmlCode = new DownloadHTML().execute("http://www.posh24.se/kandisar").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MenageData menageData = new MenageData(htmlCode);
        list = menageData.getNames();
        bitmaps = menageData.getBitmaps();
        random= new Random().nextInt(list.size());
        makeQuestion(random,list,bitmaps);
        Log.i("list",Integer.toString(list.size()));
        Log.i("bitmaps",Integer.toString(bitmaps.size()));
    }
}
