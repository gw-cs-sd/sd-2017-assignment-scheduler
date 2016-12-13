package com.example.calendarquickstart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class eventCreate extends AppCompatActivity {

    private EditText mEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);

        MainActivity.newEvent = true;




    }


    public void onClick(View v) {

        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }




}
