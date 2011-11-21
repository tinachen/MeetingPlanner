package com.uiproject.meetingplanner;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseHelper;
import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseManager;

public class DisplayPending extends Activity {

	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	TextView title, desc, date, time, tracktime, attendees, location;
	Button acceptbutton, declinebutton;
	MeetingInstance meeting;
	private MeetingPlannerDatabaseManager db;
	private int uid;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayaccepted);
        title = (TextView) findViewById(R.id.title);
        desc = (TextView) findViewById(R.id.desc);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        tracktime = (TextView) findViewById(R.id.tracktime);
        attendees = (TextView) findViewById(R.id.attendees);
        location = (TextView) findViewById(R.id.location);
        acceptbutton = (Button) findViewById(R.id.acceptbutton);
        declinebutton = (Button) findViewById(R.id.declinebutton);

    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
    	uid = settings.getInt("uid", -1);

        int mid = getIntent().getIntExtra("mid", -1);
	    db = new MeetingPlannerDatabaseManager(this, MeetingPlannerDatabaseHelper.DATABASE_VERSION);
	    db.open();
	    meeting = db.getMeeting(mid);
	    db.close();
	   	    
    	String mtitle = meeting.getMeetingTitle();
    	String mdesc = meeting.getMeetingDescription();
    	String maddr = meeting.getMeetingAddress();
    	String mdate = meeting.getMeetingDate();
    	String mtime = meeting.getMeetingStartTime() + "-" + meeting.getMeetingEndTime();
    	String mtracktime = meeting.getMeetingTrackTime() + " minutes";
    	HashMap<Integer, UserInstance> users = meeting.getMeetingAttendees();
    	
    	String mnames = "";
    	boolean added = false;
    	Set<Integer> keys = users.keySet();
    	for (Integer i : keys){
    		if (added){
    			mnames += ", ";
    		}
    		mnames = mnames + users.get(i).getUserFirstName() + users.get(i).getUserLastName();
    		added = true;    		
    	}
    	
    	// Set the view
    	title.setText(mtitle);
    	desc.setText(mdesc);
    	date.setText(mdate);
    	time.setText(mtime);
    	tracktime.setText(mtracktime);
    	location.setText(maddr);
    	attendees.setText(mnames);
    }

	public void back(View Button){
		onBackPressed();
	}
	
	
    @Override
    public void onBackPressed(){
    	finish();
    	
    }
    

    
    public void accept(View Button){
        Communicator.acceptMeeting(uid, meeting.getMeetingID());
        db.open();
        db.updateMeetingUser(meeting.getMeetingID(), uid, MeetingPlannerDatabaseHelper.ATTENDINGSTATUS_ATTENDING, "0");
        db.close();
		Intent intent = new Intent(DisplayPending.this, DisplayAccepted.class);
		intent.putExtra("mid", meeting.getMeetingID());
		startActivity(intent);
		finish();
  
    }

    
    
    public void decline(View Button){
        Communicator.declineMeeting(uid, meeting.getMeetingID());
        db.open();
        db.updateMeetingUser(meeting.getMeetingID(), uid, MeetingPlannerDatabaseHelper.ATTENDINGSTATUS_DECLINING, "0");
        db.close();
		Intent intent = new Intent(DisplayPending.this, DisplayDeclined.class);
		intent.putExtra("mid", meeting.getMeetingID());
		startActivity(intent);
		finish();
  
    }

	 // menu 
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.logoutonly, menu);
	        return true;
	    }
	    
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	            case R.id.logout:{
	            	logout();
	            	break;
	            }
	        }
	        return true;
	    }
	    
	    private void logout(){
          this.setResult(R.string.logout);
          this.finish();
	    }
}
