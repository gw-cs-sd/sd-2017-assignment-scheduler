package com.example.calendarquickstart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class evenDetail extends ActionBarActivity implements android.view.View.OnClickListener{

    Button btnSave ,  btnDelete;
    Button btnClose;
    EditText editTextName;
    EditText editTextDes;
    EditText editTextTask;
    private int _Even_Id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextDes = (EditText) findViewById(R.id.editTextDes);
        editTextTask = (EditText) findViewById(R.id.editTextTask);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);


        _Even_Id =0;
        Intent intent = getIntent();
        _Even_Id =intent.getIntExtra("even_Id", 0);
        EvenRepo repo = new EvenRepo(this);
        Even even = new Even();
        even = repo.getEvenById(_Even_Id);

        editTextTask.setText(String.valueOf(even.task));
        editTextName.setText(even.name);
        editTextDes.setText(even.des);
    }



    public void onClick(View view) {
        if (view == findViewById(R.id.btnSave)){
            EvenRepo repo = new EvenRepo(this);
            Even even = new Even();
            even.task= Integer.parseInt(editTextTask.getText().toString());
            even.des=editTextDes.getText().toString();
            even.name=editTextName.getText().toString();
            even.even_ID=_Even_Id;

            if (_Even_Id==0){
                _Even_Id = repo.insert(even);

                Toast.makeText(this,"New even Insert",Toast.LENGTH_SHORT).show();
            }else{

                repo.update(even);
                Toast.makeText(this,"even Record updated",Toast.LENGTH_SHORT).show();
            }
        }else if (view== findViewById(R.id.btnDelete)){
            EvenRepo repo = new EvenRepo(this);
            repo.delete(_Even_Id);
            Toast.makeText(this, "even Record Deleted", Toast.LENGTH_SHORT);
            finish();
        }else if (view== findViewById(R.id.btnClose)){
            finish();
        }


    }

}
