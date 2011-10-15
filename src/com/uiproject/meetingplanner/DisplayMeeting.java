package com.uiproject.meetingplanner;

import java.util.Date;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.google.android.maps.MapActivity;

public class DisplayMeeting extends MapActivity {
	
	private static final String TAG = "MeetingTracker";
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meetingdetails);
    	
		Date myDate = new Date();
		String [] myPeople = {"John", "Jane"};
		MeetingInstance identifier = new MeetingInstance(1,37,42,"My test meeting", myDate, myPeople);
		
    	TextView meetingID = (TextView) findViewById(R.id.meetingID);
    	CharSequence meetingIDText = "ID: " + identifier.getMeetingID() + "\n";
    	meetingID.setText(meetingIDText);
    	
    	TextView meetingDesc = (TextView) findViewById(R.id.meetingSubject);
    	CharSequence meetingSubject = "Subject: " + identifier.getMeetingSubject() + "\n";
    	meetingDesc.setText(meetingSubject);
    	
    	TextView meetingLoc = (TextView) findViewById(R.id.meetingLocation);
    	CharSequence meetingLocation = "Location: " + "Lat " + identifier.getMeetingLat() + ", Long " + identifier.getMeetingLong() + "\n";
    	meetingLoc.setText(meetingLocation);
    	
    	TextView meetingTime = (TextView) findViewById(R.id.meetingTime);
    	CharSequence meetingDate = "Date/Time: " + identifier.getMeetingDate().toString() + "\n";
    	meetingTime.setText(meetingDate);
    	
    	//Display all the attendees of the meeting
    	TextView attendeesHeader = (TextView) findViewById(R.id.attHeader);
    	CharSequence header = "List of attendees: \n";
    	attendeesHeader.setText(header);
    	
    	ListView lv = (ListView)findViewById(R.id.attendees);
    	lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , myPeople));
    	
    	Button myButton = (Button) findViewById(R.id.OKButton);
    	CharSequence OKbut = "OK";
    	myButton.setText(OKbut);
    	
    }
    
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    
}
