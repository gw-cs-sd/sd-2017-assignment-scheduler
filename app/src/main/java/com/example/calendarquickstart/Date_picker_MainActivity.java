package com.example.date_picker;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class Date_picker_MainActivity extends Activity {
	Button btn1=null;
	 TimePicker tp=null;
	 DatePicker dp=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker__main);
           dp = (DatePicker)findViewById(R.id.datePicker);
           tp = (TimePicker) findViewById(R.id.timePicker);
            
           Button btn_1=(Button)findViewById(R.id.btn1);
           
           btn_1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            	 
                String strDateTime = dp.getYear() + "-" + (dp.getMonth() + 1) + "-" + dp.getDayOfMonth() + " "+ tp.getCurrentHour() + ":" + tp.getCurrentMinute();

                Toast.makeText(getApplicationContext(), "User selected Date and Time....=" + strDateTime , Toast.LENGTH_LONG).show();  
               
                
            }

       	});
				
		
    

    }
    
    
}
