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
        String[] items = new String[]{"reading", "math", "essay"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        MainActivity.cEvent = true;
        Event createEvent = new Event();


//        EditText summary = (EditText) findViewById(R.id.summary);
//        EditText description = (EditText) findViewById(R.id.description);
//        EditText et = (EditText) findViewById(R.id.et);
//        Spinner type =(Spinner) findViewById(R.id.type);
//
//        MainActivity.newEvent.setSummary(summary.getText().toString());
//        MainActivity.newEvent.setDescription(description.getText().toString());
//        MainActivity.assignment = type.getSelectedItem().toString();


//        Event event = new Event()
//                .setSummary("Reading assignment")
//                .setLocation("GWU")
//                .setDescription("New Event");


        MainActivity.newEvent = createEvent;
    }


    public void onClick(View v) {

        EditText summary = (EditText) findViewById(R.id.summary);
        EditText description = (EditText) findViewById(R.id.description);
        EditText et = (EditText) findViewById(R.id.et);
        Spinner type =(Spinner) findViewById(R.id.type);

        MainActivity.newEvent.setSummary(summary.getText().toString());
        MainActivity.newEvent.setDescription(description.getText().toString());
        MainActivity.assignment = type.getSelectedItem().toString();


        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }




}
