package com.uiproject.meetingplanner;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateMeetingWhen extends Activity {

	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
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
    

    protected double trackTime;
	
    private Spinner spinner;
    
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
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.createmeetingwhen);
	        

	        mPickDate = (Button) findViewById(R.id.pickDate);
	        msPickTime = (Button) findViewById(R.id.startPickTime);
	        mePickTime = (Button) findViewById(R.id.endPickTime);
	        spinner = (Spinner) findViewById(R.id.tracker_selector);
	        

	    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
	    	
	        // get the current date
	        final Calendar c = Calendar.getInstance();
	        mYear = settings.getInt("mdatey", c.get(Calendar.YEAR));
	        mMonth = settings.getInt("mdatem", c.get(Calendar.MONTH));
	        mDay = settings.getInt("mdated", c.get(Calendar.DAY_OF_MONTH));
	        
	        updateDateDisplay();

	        // get the current time
	        msHour = settings.getInt("mstarth", c.get(Calendar.HOUR_OF_DAY));
	        msMinute = settings.getInt("mstartm", c.get(Calendar.MINUTE));
	        
	        // display the current time
	        updateTimeDisplay(true);
	        
	        // add 2 hours to current time
	        meHour = settings.getInt("mendh",(msHour+2) % 24);
	        meMinute = settings.getInt("mendhm",msMinute);

	        // display the current time
	        updateTimeDisplay(false);
	        
	        //------------------set tracking time

	        
	        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	                this, R.array.tracker_array, android.R.layout.simple_spinner_item);
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        spinner.setAdapter(adapter);
	        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
	        trackTime = (double) settings.getFloat("mtrackingtime", (float) .5);
	        
	        //this doesn't work yet TODO
	        int spinnerPosition = adapter.getPosition(trackTime + "");
	        spinner.setSelection(spinnerPosition);
	        
	        
	    }

	    public void back(View button){
	    	onBackPressed();
	    }

	    public void cancel(View button){


	    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
	    	SharedPreferences.Editor editor = settings.edit();
	    	editor.remove("mtitle");
	    	editor.remove("mdesc");
	    	editor.remove("mdatem");
	    	editor.remove("mdated");
	    	editor.remove("mdatey");
	    	editor.remove("mstarth");
	    	editor.remove("mstartm");
	    	editor.remove("mendh");
	    	editor.remove("mendm");
	    	editor.remove("mtracktime");
	    	
	    	//remove people
	    	//remove location
	    	editor.commit();

	    	
	    	CreateMeetingWhen.this.setResult(R.string.cancel_create);
	    	CreateMeetingWhen.this.finish();
			//need to clear the previous activities too
	    }
	    
	    public void next(View button){
	    	saveData();
			Intent intent = new Intent(CreateMeetingWhen.this, CreateMeetingWho.class);
			CreateMeetingWhen.this.startActivityForResult(intent, 0);
	    	
	    }
	    
	    @Override
	    public void onBackPressed(){
	    	saveData();
	    	
	    	CreateMeetingWhen.this.finish();
	    	
	    }

	    private void saveData(){

	    	//save data in shared preferences
	    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
	    	SharedPreferences.Editor editor = settings.edit();
	    	editor.putBoolean("cmwhen", true);
	    	editor.putInt("mdatem", mMonth);
	    	editor.putInt("mdated", mDay);
	    	editor.putInt("mdatey", mYear);
	    	editor.putInt("mstarth", msHour);
	    	editor.putInt("mstartm", msMinute);
	    	editor.putInt("mendh", meHour);
	    	editor.putInt("mendm", meMinute);
	    	editor.putFloat("mtracktime", (float) trackTime);
	    	editor.commit();
	    	
	    }
	    

	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        if (resultCode == R.string.cancel_create) {
	            this.setResult(R.string.cancel_create);
	            this.finish();
	        }
	    }
	    
	    public void changeDate(View button){

            Toast.makeText(getBaseContext(), "trying to change date", Toast.LENGTH_SHORT).show();
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
