package com.uiproject.meetingplanner;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CreateMeetingConfirm extends Activity {

	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	TextView title, desc, date, time, tracktime, attendees, location;
	
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

    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
    	title.setText(settings.getString("mtitle", "Untitled"));
    	desc.setText(settings.getString("mdesc", "No description"));
    	int month = settings.getInt("mdatem", 0) + 1;
    	int day = settings.getInt("mdated", 0);
    	int year = settings.getInt("mdatey", 0);
    	date.setText(month + "-" + day + "-" + year);
    	int sh = settings.getInt("mstarth", 0);
    	int sm = settings.getInt("mstartm", 0);
    	int eh = settings.getInt("mendh", 0);
    	int em = settings.getInt("mendm", 0);
    	time.setText(sh + ":" + sm + "-" + eh + ":" + em);
    	tracktime.setText( (double) settings.getFloat("mtracktime", (float).5) + " hours");
    	
    	//set attendees, set location
    	
    }

    
    public void confirm(View button){
    	//save meeting data into the db
    }
}
