package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int counter = 0;
    int redcounter=0,yellowcounter=0;
    int[] red= new int[5];
    int[] yellow = new int[5];
    int winning[][] = {{1,2,3},{4,5,6},{7,8,9},{1,4,7},{3,6,9},{1,5,9},{3,5,7},{2,5,8}};
public void dropIn(View v)
{
    ImageView img = (ImageView) v;
    counter++;
    if (counter%2!=0)
    {
        img.setTranslationY(1000f);
        img.setImageResource(R.drawable.red);
        img.animate().translationYBy(-1000f).setDuration(300);
        img.setClickable(false);
        red[redcounter]=(Integer.valueOf(v.getTag().toString()));
        redcounter++;
        Arrays.sort(red);
        Log.i("red",Arrays.toString(red));
        for(int i=0;i<8;i++) {
            if (Arrays.equals(winning[i], (Arrays.c)))
                Toast.makeText(this, "red is Winner", Toast.LENGTH_LONG).show();
        }
    }
    else {
        img.setTranslationY(-1000f);
        img.setImageResource(R.drawable.yellow);
        img.animate().translationYBy(1000f).setDuration(300);
        img.setClickable(false);
        yellow[yellowcounter] = (Integer.valueOf(v.getTag().toString()));
        yellowcounter++;
        Arrays.sort(Arrays.copyOf(yellow, yellowcounter));
        for (int i = 0; i < 8; i++)
            if (Arrays.equals(winning[i], (Arrays.copyOf(yellow, yellowcounter)))) {
                Toast.makeText(this, "yellow is Winner", Toast.LENGTH_LONG).show();
            }
    }
    if(counter==9)
        Toast.makeText(this, "Tie", Toast.LENGTH_LONG).show();
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
