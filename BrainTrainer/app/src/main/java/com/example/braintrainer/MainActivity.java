package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    CountDownTimer timer;
    int currentResult=0,currentCell=0,totalProblems=0,correctProblems=0;
    public void clickOnPlay(View v)
    {
        v.setVisibility(View.INVISIBLE);
        currentResult=0;
        currentCell=0;
        totalProblems=0;
        correctProblems=0;

        timer.start();
        makeProblem();
        enableAnswers();

    }
    public void makeProblem(){
        Random random = new Random();
        int a = random.nextInt(100);
        int b = random.nextInt(100);
        int operator = random.nextInt(3);
        int result=0;
        char op=' ';
        int point=random.nextInt(3);
        if(operator == 0) {
            result = a + b;
            op='+';
        }
        else if(operator==1) {
            result = a - b;
            op='-';
        }
        else if(operator==2) {
            result = a * b;
            op='X';
        }
        else if(operator==3) {
            result = a / b;
            op='/';
        }
        //return a+" "+op+" "+b+" "+result;
        GridLayout grid = (GridLayout) findViewById(R.id.grid);
        TextView correct = (TextView) grid.getChildAt(point);
        currentResult=result;
        currentCell=point;
        TextView question = (TextView) findViewById(R.id.question);
        question.setText(Integer.toString(a)+" "+op+" "+Integer.toString(b)+" =");
        correct.setText(Integer.toString(result));
        makeFakeAnswers(point);
    }

    public void makeFakeAnswers(int correct)
    {
        GridLayout grid = (GridLayout) findViewById(R.id.grid);
        TextView tv;
        for(int i=0;i<grid.getChildCount();i++)
        {
            if(i!=correct)
            {
                Random random = new Random();
                int fake;
                if(currentResult<100)
                    fake=random.nextInt(100);
                else
                    fake=random.nextInt(currentCell*random.nextInt(3)+1);
                tv=(TextView) grid.getChildAt(i);
                tv.setText(Integer.toString(fake));
            }
        }
    }

    public void clickOnCell(View v)
    {
        TextView textView = (TextView) v;
        TextView message = (TextView) findViewById(R.id.message);
        if(textView.getText().equals(Integer.toString(currentResult)))
        {
            totalProblems++;
            correctProblems++;
            message.setText("Correct!");
            message.setVisibility(View.VISIBLE);

        }
        else {
            totalProblems++;
            message.setText("Wrong!");
            message.setVisibility(View.VISIBLE);
        }
        TextView counter = (TextView) findViewById(R.id.currentResult);
        counter.setText(correctProblems+"/"+totalProblems);

        makeProblem();
    }
 public void disableAnswers()
 {
     GridLayout layout = (GridLayout) findViewById(R.id.grid);
     for (int i = 0; i < layout.getChildCount(); i++) {
         View child = layout.getChildAt(i);
         child.setEnabled(false);
     }
 }

    public void enableAnswers()
    {
        GridLayout layout = (GridLayout) findViewById(R.id.grid);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            child.setEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        disableAnswers();
        timer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long l) {
                TextView timerText= (TextView) findViewById(R.id.timer);
                timerText.setText(Long.toString(l/1000));
            }

            @Override
            public void onFinish() {
                Button button = (Button) findViewById(R.id.play);
                button.setVisibility(View.VISIBLE);
                TextView result= (TextView) findViewById(R.id.currentResult);
                result.setText("0/0");
                TextView time=(TextView) findViewById(R.id.timer);
                time.setText("Time");
                disableAnswers();
            }
        };
    }
}
