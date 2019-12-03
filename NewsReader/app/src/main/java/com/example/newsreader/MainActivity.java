package com.example.newsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase database;
    ListView listView;
    List<String> url = new ArrayList<>();

public void updateList(){
    Cursor cursor = database.rawQuery("SELECT * FROM items ",null);
    int titleIndex = cursor.getColumnIndex("title");
    int urlIndex = cursor.getColumnIndex("url");
    cursor.moveToFirst();
    List<String> lista = new ArrayList<>();
    Log.i("title index count",Integer.toString(titleIndex));
    while(cursor.moveToNext()){
        lista.add(cursor.getString(titleIndex));
        url.add(cursor.getString(urlIndex));
       Log.i("result from database",cursor.getString(titleIndex));
    }
    cursor.close();
    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,lista);
    adapter.notifyDataSetChanged();
    listView.setAdapter(adapter);

}

     class getItems extends AsyncTask<List<String>,Void,List<String>>{

        @Override
        protected List<String> doInBackground(List<String>... lists) {
            List<String> resultList=new ArrayList<>();
            try {
                for (int i = 0; i < lists[0].size(); i++) {
                    String result = "";

                    URL url = new URL(lists[0].get(i));
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream input = urlConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(input);
                    int read = reader.read();
                    while (read != -1) {
                        char c = (char) read;
                        result += c;
                        read = reader.read();
                    }
                resultList.add(result);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try{
            for(int i=0;i<resultList.size();i++) {
                JSONObject jsonObject = new JSONObject(resultList.get(i));
                String title = jsonObject.getString("title");
                String url = jsonObject.getString("url");
                int id = jsonObject.getInt("id");

                Log.i("title", title);
                Log.i("url", url);
                Log.i("id", Integer.toString(id));
                String sql = "INSERT INTO items (idItem, url, title ) VALUES (?, ?, ?)";
                SQLiteStatement statement = database.compileStatement(sql);
                statement.bindString(1, Integer.toString(id));
                statement.bindString(2, url);
                statement.bindString(3, title);
                statement.execute();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
            return resultList;

    }
}


public class GetNews extends AsyncTask<String,Void,List<String>>{
    List<String> listOfNews;
    public GetNews()  {
        listOfNews = new ArrayList<>();
    }

    @Override
    protected List<String> doInBackground(String... strings) {
        String result="";
        try {
            database.execSQL("DELETE FROM items");
            URL urlItem = new URL("https://hacker-news.firebaseio.com/v0/item/");
            URL urlNews = new URL("https://hacker-news.firebaseio.com/v0/topstories.json");
            HttpURLConnection connection = (HttpURLConnection) urlNews.openConnection();
            InputStream input = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(input);
            int read = reader.read();
            while(read!=-1){
                char c=(char) read;
                result+=c;
                read=reader.read();
            }
                JSONArray  jsonArray= new JSONArray(result);
                for(int i=0;i<20;i++) {
                    listOfNews.add(urlItem + jsonArray.getString(i) + ".json");
                }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listOfNews;
    }
    }



    public MainActivity(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         database = this.openOrCreateDatabase("items",Context.MODE_PRIVATE,null);
         database.execSQL("CREATE TABLE IF NOT EXISTS items(id INTEGER PRIMARY KEY , idItem VARCHAR , url VARCHAR , title VARCHAR )");
        listView = findViewById(R.id.listView);
        List<String> listItems = new ArrayList<>();
        List<String> result = new ArrayList<>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),WebView.class);
                intent.putExtra("url",url.get(i));
                startActivity(intent);
            }
        });
        try {

            listItems = new GetNews().execute("").get();
            Log.i("im in activity",listItems.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //getItems getItems = new getItems();
        try {
            result = new getItems().execute(listItems).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(result.size()>0){
            updateList();
        }


    }
}
