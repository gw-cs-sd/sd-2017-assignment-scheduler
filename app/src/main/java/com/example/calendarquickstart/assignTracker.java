package com.example.calendarquickstart;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class assignTracker extends AppCompatActivity {

    public TextView tv;
    Button bStart;
    Button bPause;
    Button bEnd;
    private boolean isPaused = false;

    private boolean isCanceled = false;

    private long timeRemaining = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_tracker);

        tv =(TextView)findViewById(R.id.tv);
        bStart = (Button) findViewById(R.id.start);
        bPause = (Button) findViewById(R.id.pause);
        bEnd = (Button) findViewById(R.id.end);

        bPause.setEnabled(false);

        bEnd.setEnabled(false);







        CountDownTimer cTimer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                tv.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                tv.setText("done!");
            }
        };


    }

    public void start(View v) {
        timer=(TextView)findViewById(R.id.tv);



    }

    public void end(View v) {


        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

}
