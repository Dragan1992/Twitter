package com.example.timetables;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final List<Integer> list = new ArrayList<Integer>(asList(1,2,3,4,5,6,7,8,9));
        final ArrayAdapter<Integer> adapter=new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,list);
        final ListView ListView = (ListView) (findViewById(R.id.myList));
        ListView.setAdapter(adapter);
        SeekBar seekBar = (SeekBar) (findViewById(R.id.seekBar));
        seekBar.setMax(20);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                List<Integer> resultList = new ArrayList<Integer>();
                Log.i("vrednost",Integer.toString(seekBar.getProgress()));
                for(int j=0;j<list.size();j++)
                {
                    int element=list.get(j)*seekBar.getProgress();

                    resultList.add(element);

                }
                ArrayAdapter adapterResult = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,resultList);
                ListView.setAdapter(adapterResult);
                


            }
        });
    }
    }
