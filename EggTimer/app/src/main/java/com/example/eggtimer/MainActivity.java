package com.example.eggtimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public String timeFormat(int sec)
    {
       // Log.i("i",Integer.toString(i));
       int minutes= sec/60;
        int seconds= sec-(minutes*60);
        if(seconds<9)
            return Integer.toString(minutes)+":0"+Integer.toString(seconds);
        else
            return Integer.toString(minutes)+":"+Integer.toString(seconds);
    }


    public TextView tView;
    SeekBar seekBar;
    CountDownTimer timer=null;
    boolean counterIsActive=false;
    public void go(View v) {
        if(counterIsActive==false) {
            timer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    tView.setText(timeFormat((int) l / 1000));
                }

                @Override
                public void onFinish() {
                    MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.play);
                    mPlayer.start();
                    seekBar.setEnabled(true);

                }
            };

            Button button = (Button) v;
            button.setText("Stop");
            seekBar.setEnabled(false);
            timer.start();
            counterIsActive=true;
        }

        else if(counterIsActive==true)
        {
          timer.cancel();
          Log.i("vo else","cancel");
          Button button = (Button) v;
          button.setText("GO!");
          seekBar.setEnabled(true);
          counterIsActive=false;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tView= (TextView) findViewById(R.id.textView);
         seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMin(30);
        seekBar.setMax(270);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                tView.setText(timeFormat(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
