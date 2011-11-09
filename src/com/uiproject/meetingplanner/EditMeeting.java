package com.uiproject.meetingplanner;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class EditMeeting extends Activity {
	
	private EditText mname;
	
	//for date picker
	private Button mPickDate;
	private int mYear;
	private int mMonth;
	private int mDay;
	static final int DATE_DIALOG_ID = 0;
	
	//for time pickers
    private Button msPickTime;
    private int msHour;
    private int msMinute;
    static final int TIME_DIALOG_ID_START = 1;
    
    private Button mePickTime;
    private int meHour;
    private int meMinute;
    static final int TIME_DIALOG_ID_END = 2;
    static final int DESC_DIALOG = 3;
    
    protected double trackTime;
	
    private Spinner spinner;
    
    TextView location, attendees;
    
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
        setContentView(R.layout.editmeeting);
        
        mname = (EditText) findViewById(R.id.mname_field);
        mPickDate = (Button) findViewById(R.id.pickDate);
        msPickTime = (Button) findViewById(R.id.startPickTime);
        mePickTime = (Button) findViewById(R.id.endPickTime);
        spinner = (Spinner) findViewById(R.id.tracker_selector);
        location = (TextView) findViewById(R.id.location);
        attendees = (TextView) findViewById(R.id.attendees);

        // get meeting info from db
        mYear = 2011;
        mMonth = 11;
        mDay = 8;
        msHour = 14;
        msMinute = 30;
        meHour = 19;
        meMinute = 0;
        
        // update date and time displays
        updateDateDisplay();
        updateTimeDisplay(true);
        updateTimeDisplay(false);
        
        // set tracking time

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.tracker_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        //TODO put in current spinner
        
        // set location
        // set attendees
        
		
	}
	
	@Override // this is called when we come back from child activity
	public void onActivityResult(int requestCode, int resultCode, Intent data) {     
		super.onActivityResult(requestCode, resultCode, data); 
			  
		switch(requestCode) { 
			case (1) : { // location
				 if (resultCode == Activity.RESULT_OK) { 
					String lat = data.getStringExtra("lat");
					String lon = data.getStringExtra("lon");
					// do more stuff with this
				} 
			}case (2): { // people
				 if (resultCode == Activity.RESULT_OK) { 
					// do more stuff 
				} 
				
			}
			break; 
		} 
	}
	
	public void changeDate(View button){
        showDialog(DATE_DIALOG_ID);
	}
	
	public void changeStart(View button){
        showDialog(TIME_DIALOG_ID_START);
	}
	
	public void changeEnd(View button){
        showDialog(TIME_DIALOG_ID_END);
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
   
	
	 // updates the date in the TextView
   private void updateDateDisplay() {
     mPickDate.setText(
         new StringBuilder()
                 // Month is 0 based so add 1
                 .append(mMonth + 1).append("-")
                 .append(mDay).append("-")
                 .append(mYear).append(" "));
   }
   
   private void updateTimeDisplay(boolean start) {
	   if(start){
		    msPickTime.setText(
		            new StringBuilder()
		                    .append(pad(msHour)).append(":")
		                    .append(pad(msMinute)));
	   }else{
		    mePickTime.setText(
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
    	Toast.makeText(EditMeeting.this, s, Toast.LENGTH_SHORT).show();
    	//save in db
    	finish();
    	
    }
    
    public void invite(View button){
    	Intent intent = new Intent(EditMeeting.this, Search.class);
    	EditMeeting.this.startActivity(intent);
    }
    
    public void selectLocation(View button){    	
    	
    	int myLat = 34020105;
    	int myLon = -118289821;
    	Intent intent = new Intent(EditMeeting.this, SelectLocation.class);
    	Bundle bundle = new Bundle();
    	bundle.putInt("lat", myLat);
    	bundle.putInt("lon", myLon);
    	intent.putExtras(bundle);
    	EditMeeting.this.startActivityForResult(intent, 1);
    	
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
