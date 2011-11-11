package com.uiproject.meetingplanner;

import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseManager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CreateMeetingConfirm extends Activity {

	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	TextView title, desc, date, time, tracktime, attendees, location;
	private MeetingPlannerDatabaseManager db;
	private String mtitle, mdesc, maddr, mdate, mstarttime, mendtime;
	private int mtracktime, mlon, mlat;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createmeetingconfirm);
        title = (TextView) findViewById(R.id.title);
        desc = (TextView) findViewById(R.id.desc);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        tracktime = (TextView) findViewById(R.id.tracktime);
        attendees = (TextView) findViewById(R.id.attendees);
        location = (TextView) findViewById(R.id.location);

        // Get var values from sharedpreferences
    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
    	int month = settings.getInt("mdatem", 0) + 1;
    	int day = settings.getInt("mdated", 0);
    	int year = settings.getInt("mdatey", 0);
    	int sh = settings.getInt("mstarth", 0);
    	int sm = settings.getInt("mstartm", 0);
    	int eh = settings.getInt("mendh", 0);
    	int em = settings.getInt("mendm", 0);
    	mtitle = settings.getString("mtitle", "Untitled");
    	mdesc = settings.getString("mdesc", "No description");
    	maddr = settings.getString("maddr", "default addr");
    	mdate = month + "-" + day + "-" + year;
    	mstarttime = sh + ":" + sm;
    	mendtime = eh + ":" + em;
    	mtracktime = (int) ((double) settings.getFloat("mtracktime", (float).5) * 60);
    	mlon = settings.getInt("mlon", 0);
    	mlat = settings.getInt("mlat", 0);
    	
    	// Set the view
    	title.setText(mtitle);
    	desc.setText(mdesc);
    	date.setText(mdate);
    	time.setText(mstarttime + "-" + mendtime);
    	tracktime.setText( (double) settings.getFloat("mtracktime", (float).5) + " hours");
    	location.setText(maddr);
    	
    	//set attendees
    	
    	
    	// Hook up with database
	    db = new MeetingPlannerDatabaseManager(this, 2);
	    db.open();
    }

	public void back(View Button){
		onBackPressed();
	}
	
    public void cancel(View Button){

    	clearData();
    	CreateMeetingConfirm.this.setResult(R.string.cancel_create);
    	CreateMeetingConfirm.this.finish();
    	
    }
	
    @Override
    public void onBackPressed(){
    	finish();
    	
    }
    
    public void confirm(View button){
    	// Pass meeting details to server TODO
    	// Retrieve meeting ID and set it
    	int meetingID;
    	
    	//save meeting data into the db

    	Toast.makeText(CreateMeetingConfirm.this, "meeting saved!", Toast.LENGTH_SHORT).show();
    	// add to db and get id back, go to add ppl page
    	
    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
    	//int latitude = 1234567;
    	//int longitude = 2222222;

    	int initiatorID = settings.getInt("uid", 0);
    	db.createMeeting(mtitle, mlat, mlon, mdesc, maddr, mdate, mstarttime, mendtime, mtracktime, initiatorID); //TODO need meeting ID

    	clearData();
    	CreateMeetingConfirm.this.setResult(R.string.cancel_create);
    	CreateMeetingConfirm.this.finish();
    }
    
    public void clearData(){

    	// delete data from shared pref
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
    	editor.remove("maddr");
    	editor.remove("mlat");
    	editor.remove("mlon");
    	
    	//remove people
    	//remove location
    	editor.commit();
    		
    
    }
}
