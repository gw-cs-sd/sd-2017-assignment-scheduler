package com.example.calendarquickstart;

import android.app.usage.UsageEvents;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.api.services.calendar.model.Event;

public class assignTracker extends AppCompatActivity {

    public TextView tv;
    Button bStart;
    Button bPause;
    Button bEnd;
    private boolean isPaused = false;

    private boolean isCanceled = false;

    private long timeRemaining = 0;
    public long eventLength = 0;
    public long mod = 0;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_tracker);

        eventLength = Math.abs(MainActivity.currentEvent.getEnd().getDateTime().getValue() - MainActivity.currentEvent.getStart().getDateTime().getValue());


        tv =(TextView)findViewById(R.id.tv);
        bStart = (Button) findViewById(R.id.start);
        bPause = (Button) findViewById(R.id.pause);
        bEnd = (Button) findViewById(R.id.end);

        bPause.setEnabled(false);
        bEnd.setEnabled(false);

        tv.setText("Seconds Remaining" + (eventLength/1000));

        bStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                isPaused = false;
                isCanceled = false;

                //Disable the start and pause button
                bStart.setEnabled(false);

                //Enabled the pause and cancel button
                bPause.setEnabled(true);
                bEnd.setEnabled(true);

                CountDownTimer timer;
                long millisInFuture = eventLength; //30 seconds
                long countDownInterval = 1000; //1 second


                //Initialize a new CountDownTimer instance
                timer = new CountDownTimer(millisInFuture,countDownInterval){
                    public void onTick(long millisUntilFinished){
                        //do something in every tick
                        if(isPaused || isCanceled)
                        {
                            //If the user request to cancel or paused the
                            //CountDownTimer we will cancel the current instance
                            cancel();
                        }
                        else {
                            //Display the remaining seconds to app interface
                            //1 second = 1000 milliseconds
                            tv.setText("" + millisUntilFinished / 1000);
                            //Put count down timer remaining time in a variable
                            timeRemaining = millisUntilFinished;
                        }
                    }
                    public void onFinish(){
                        //Do something when count down finished
                        tv.setText("Done");

                        //Enable the start button
                        bStart.setEnabled(true);
                        //Disable the pause, resume and cancel button
                        bPause.setEnabled(false);

                        bEnd.setEnabled(false);
                    }
                }.start();
            }
        });

        bPause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                 if(isPaused){
                     bStart.setEnabled(false);
                     bPause.setEnabled(true);
                     bEnd.setEnabled(true);
                     //Specify the current state is not paused and canceled.
                     isPaused = false;
                     isCanceled = false;

                     long millisInFuture = timeRemaining;
                     long countDownInterval = 1000;
                     new CountDownTimer(millisInFuture, countDownInterval){
                         public void onTick(long millisUntilFinished){
                             //Do something in every tick
                             if(isPaused || isCanceled)
                             {
                                 //If user requested to pause or cancel the count down timer
                                 cancel();
                             }
                             else {
                                 tv.setText("" + millisUntilFinished / 1000);
                                 //Put count down timer remaining time in a variable
                                 timeRemaining = millisUntilFinished;
                             }
                         }
                         public void onFinish(){
                             //Do something when count down finished
                             tv.setText("Done");
                             //Disable the pause, resume and cancel button
                             bPause.setEnabled(false);

                             bEnd.setEnabled(false);
                             //Enable the start button
                             bStart.setEnabled(true);
                         }
                     }.start();
                     bEnd.setOnClickListener(new View.OnClickListener(){
                         @Override
                         public void onClick(View v){
                             //When user request to cancel the CountDownTimer
                             isCanceled = true;

                             //Disable the cancel, pause and resume button
                             bPause.setEnabled(false);

                             bEnd.setEnabled(false);
                             //Enable the start button
                             bStart.setEnabled(true);

                             mod = (long)((timeRemaining * (0.333)));
                             //Notify the user that CountDownTimer is canceled/stopped
                             tv.setText("Time Tracker has stopped");
                         }
                     });
                 }
                else
                 {
                     isPaused = true;

                     bEnd.setEnabled(true);
                     //Disable the start and pause button
                     bStart.setEnabled(false);

                 }

            }
        });

        bEnd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //When user request to cancel the CountDownTimer
                isCanceled = true;

                //Disable the cancel, pause and resume button
                bPause.setEnabled(false);

                bEnd.setEnabled(false);
                //Enable the start button
                bStart.setEnabled(true);
                mod = (long)((timeRemaining * (0.333)));
                //Notify the user that CountDownTimer is canceled/stopped
                tv.setText("CountDownTimer Canceled/stopped." + timeRemaining);
            }
        });



    }


    public void returns(View v){

        if(MainActivity.currentEvent.getDescription().equals("reading")){
            MainActivity.rMod -= mod;
        }
        else if(MainActivity.currentEvent.getDescription().equals("math")){
            MainActivity.mMod -= mod;
        }
        else{
            MainActivity.eMod -= mod;
        }
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }


}
