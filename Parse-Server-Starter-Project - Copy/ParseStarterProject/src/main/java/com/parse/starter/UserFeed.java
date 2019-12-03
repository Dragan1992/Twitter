package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Array;
import java.util.List;

public class UserFeed extends AppCompatActivity {
ImageView feedImg;
LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);
        Intent intent = getIntent();
        layout = (LinearLayout) findViewById(R.id.layout);
        //feedImg = (ImageView) findViewById(R.id.imageFeed);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Images");
        query.whereEqualTo("userString",intent.getStringExtra("User"));
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                Log.i("KajDone",Integer.toString(objects.size()));
                if (objects.size() > 0 && e == null) {
                    for (int i = 0; i < objects.size();i++){
                        ParseFile imgFile = objects.get(i).getParseFile("image");
                        byte[] bytes = new byte[0];
                        try {
                            bytes = imgFile.getData();
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        feedImg = new ImageView(getApplicationContext());
                        feedImg.setLayoutParams(new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        ));
                        feedImg.setImageBitmap(bitmap);
                        layout.addView(feedImg);


                    }
                }
            }
        });
    }
}
