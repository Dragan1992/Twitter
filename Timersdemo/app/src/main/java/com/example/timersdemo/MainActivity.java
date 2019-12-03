package com.example.timersdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CountDownTimer timer = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long l) {
                Log.i("coutdown:",Long.toString(l));

            }

            @Override
            public void onFinish() {
                Log.i("timer", "finish")
            }
        };
        timer.start();// posle zavrsuvanje na timerot objektot se unistuva
        /*
         final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                Log.i("In run","run");
                handler.postDelayed(this,3000);
            }
        };

        handler.post(run);
*/
    }


}
