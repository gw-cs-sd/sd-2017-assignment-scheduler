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
        String[] items1 = new String[]{"reading", "math", "essay"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        dropdown.setAdapter(adapter1);


        Spinner timeDay = (Spinner)findViewById(R.id.time);
        String[] items2 = new String[]{"morning", "evening", "night"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        timeDay.setAdapter(adapter2);


        MainActivity.cEvent = true;
        Event createEvent = new Event();





        MainActivity.newEvent = createEvent;
    }


    public void onClick(View v) {

        EditText summary = (EditText) findViewById(R.id.summary);
        EditText description = (EditText) findViewById(R.id.description);
        EditText et = (EditText) findViewById(R.id.et);
        Spinner type =(Spinner) findViewById(R.id.type);
        Spinner time =(Spinner) findViewById(R.id.time);


        MainActivity.newEvent.setSummary(summary.getText().toString());
        MainActivity.newEvent.setDescription(type.getSelectedItem().toString());
        //MainActivity.newEvent.setLocation();
        MainActivity.assignment = type.getSelectedItem().toString();
        MainActivity.timeOfDay = time.getSelectedItem().toString();



        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

}





