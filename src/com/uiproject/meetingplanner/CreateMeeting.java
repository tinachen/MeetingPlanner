package com.uiproject.meetingplanner;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateMeeting extends Activity {
	
	private EditText mname;
	
	//for date picker
	private TextView mDateDisplay;
	private Button mPickDate;
	private int mYear;
	private int mMonth;
	private int mDay;
	static final int DATE_DIALOG_ID = 0;
	
	//for time pickers; s=start time; e=end time
	private TextView msTimeDisplay;
    private Button msPickTime;
    private int msHour;
    private int msMinute;
    static final int TIME_DIALOG_ID_START = 1;
    
	private TextView meTimeDisplay;
    private Button mePickTime;
    private int meHour;
    private int meMinute;
    static final int TIME_DIALOG_ID_END = 2;
    
    protected double trackTime;
	
    // set listeners
	private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDateDisplay();
                }
            };
            
    private TimePickerDialog.OnTimeSetListener msTimeSetListener =
    	    new TimePickerDialog.OnTimeSetListener() {
    	        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    	            msHour = hourOfDay;
    	            msMinute = minute;
    	            updateTimeDisplay(true);
    	        }
    	    };
    private TimePickerDialog.OnTimeSetListener meTimeSetListener =
    	    new TimePickerDialog.OnTimeSetListener() {
    	        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    	            meHour = hourOfDay;
    	            meMinute = minute;
    	            updateTimeDisplay(false);
    	        }
    	    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createmeeting);
        
        TextView form_label = (TextView) findViewById(R.id.form_label);
        form_label.setText("Create a new meeting");
        
        // meeting name text field---------------------------
        mname = (EditText) findViewById(R.id.mname_field);
        mname.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                  // Perform action on key press
                  Toast.makeText(CreateMeeting.this, mname.getText(), Toast.LENGTH_SHORT).show();
                  return true;
                }
                return false;
            }
        });
        
        // date picker---------------------
        
        // capture our View elements
        mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
        mPickDate = (Button) findViewById(R.id.pickDate);

        // add a click listener to the button
        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // display the current date (this method is below)
        updateDateDisplay();
        
        // edit start time ------------------------- // capture our View elements
        msTimeDisplay = (TextView) findViewById(R.id.startTimeDisplay);
        msPickTime = (Button) findViewById(R.id.startPickTime);

        // add a click listener to the button
        msPickTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID_START);
            }
        });

        // get the current time
        msHour = c.get(Calendar.HOUR_OF_DAY);
        msMinute = c.get(Calendar.MINUTE);

        // display the current time
        updateTimeDisplay(true);
        
        // edit end time ------------------------- 
        // capture our View elements
        meTimeDisplay = (TextView) findViewById(R.id.endTimeDisplay);

        mePickTime = (Button) findViewById(R.id.endPickTime);

        // add a click listener to the button
        mePickTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID_END);
            }
        });

        // get the current time
        meHour = (msHour+2) % 24;
        meMinute = msMinute;

        // display the current time
        updateTimeDisplay(false);
        
        //------------------set tracking time

        Spinner spinner = (Spinner) findViewById(R.id.tracker_selector);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.tracker_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		
	}
	

	 // updates the date in the TextView
   private void updateDateDisplay() {
       mDateDisplay.setText(
           new StringBuilder()
                   // Month is 0 based so add 1
                   .append(mMonth + 1).append("-")
                   .append(mDay).append("-")
                   .append(mYear).append(" "));
   }
   
   @Override
   protected Dialog onCreateDialog(int id) {
       switch (id) {
       case DATE_DIALOG_ID:
           return new DatePickerDialog(this,
                       mDateSetListener,
                       mYear, mMonth, mDay);
       case TIME_DIALOG_ID_START:
           return new TimePickerDialog(this,
                   msTimeSetListener, msHour, msMinute, false);
       case TIME_DIALOG_ID_END:
           return new TimePickerDialog(this,
                   meTimeSetListener, meHour, meMinute, false);
       }
       return null;

   }
   
   private void updateTimeDisplay(boolean start) {
	   if(start){
		    msTimeDisplay.setText(
		            new StringBuilder()
		                    .append(pad(msHour)).append(":")
		                    .append(pad(msMinute)));
	   }else{
		    meTimeDisplay.setText(
		            new StringBuilder()
		                    .append(pad(meHour)).append(":")
		                    .append(pad(meMinute)));
	   }
	}

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
    
    
    public void saveMeeting(View button){
    	String s = "meeting saved!";
    	Toast.makeText(CreateMeeting.this, s, Toast.LENGTH_SHORT).show();
    	Intent intent = new Intent(CreateMeeting.this, Search.class);
    	CreateMeeting.this.startActivity(intent);
    	// add to db and get id back, go to add ppl page
    }
    
    public void selectLocation(View button){    	
    	//String s = "TESTING!!!!!!!!!!!!!!!! YAY";
    	//Toast.makeText(CreateMeeting.this, s, Toast.LENGTH_SHORT).show();
    	int myLat = 34020105;
    	int myLon = -118289821;
    	Intent intent = new Intent(CreateMeeting.this, SelectLocation.class);
    	Bundle bundle = new Bundle();
    	bundle.putInt("lat", myLat);
    	bundle.putInt("lon", myLon);
    	intent.putExtras(bundle);
    	CreateMeeting.this.startActivity(intent);
    	
    }
    
    // for the tracker spinner
    public class MyOnItemSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
        	trackTime = Double.parseDouble(parent.getItemAtPosition(pos).toString());
        }

        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
        }
    }
}
