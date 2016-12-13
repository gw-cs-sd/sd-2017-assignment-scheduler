package com.example.calendarquickstart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.api.services.calendar.model.Event;

public class eventCreate extends AppCompatActivity {

    private EditText mEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);

        Spinner dropdown = (Spinner)findViewById(R.id.type);
        String[] items = new String[]{"Reading", "Math Problems", "Essay"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        MainActivity.cEvent = true;


//        Event event = new Event()
//                .setSummary("Reading assignment")
//                .setLocation("GWU")
//                .setDescription("New Event");




    }


    public void onClick(View v) {

        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }




}
