package com.uiproject.meetingplanner;

import java.util.Date;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import com.google.android.maps.MapActivity;

public class DisplayMeeting extends MapActivity {
	
	private static final String TAG = "MeetingTracker";
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meetingdetails);
    	
		Date myDate = new Date();
		String [] myPeople = {"John", "Jane"};
		MeetingInstance identifier = new MeetingInstance(1,37,42,"Meeting 1 - Test", myDate, myPeople);
		
    	Log.v(TAG,"The identifier is" + identifier.getMeetingID() + "and subject " + identifier.getMeetingSubject());
    	TextView meetingID = (TextView) findViewById(R.id.meetingID);
    	Log.v(TAG, "Printing meetingID:" + meetingID);
    	meetingID.setText("ID: "+identifier.getMeetingID());
    	
    	TextView meetingDesc = (TextView) findViewById(R.id.meetingSubject);
    	meetingDesc.setText("Subject: " + identifier.getMeetingSubject());
    	
    	TextView meetingLoc = (TextView) findViewById(R.id.meetingLocation);
    	meetingLoc.setText(identifier.getMeetingLat() + identifier.getMeetingLong());
    	
    	TextView meetingTime = (TextView) findViewById(R.id.meetingTime);
    	meetingTime.setText(identifier.getMeetingDate().toString());
    	
    	//Display all the attendees of the meeting
    	TableLayout attendees = (TableLayout) findViewById(R.id.attendees);
    	for (int i = 0; i < myPeople.length; i++)
    	{
    		//Display the names one by one
    		attendees.setTag(myPeople[i]);
    	}	
    	
    	Button myButton = (Button) findViewById(R.id.OKButton);
    	myButton.setText("OK");
    	
    }
    
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    
}
