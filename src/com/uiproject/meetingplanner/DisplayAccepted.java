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

public class DisplayAccepted extends Activity {

	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	TextView title, desc, date, time, tracktime, attendees, location;
	Button trackbutton, callbutton, editbutton, alarmbutton, camerabutton, declinebutton;
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
        trackbutton = (Button) findViewById(R.id.trackbutton);
        callbutton = (Button) findViewById(R.id.callbutton);
        editbutton = (Button) findViewById(R.id.editbutton);
        alarmbutton = (Button) findViewById(R.id.alarmbutton);
        camerabutton = (Button) findViewById(R.id.camerabutton);
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
    	
    	if (meeting.getMeetingInitiatorID() != uid){
    		editbutton.setVisibility(View.GONE);
    		camerabutton.setVisibility(View.GONE);
    	}else{
    		callbutton.setVisibility(View.GONE);
    		declinebutton.setVisibility(View.GONE);
    	}
    	
        int currenth = Calendar.HOUR_OF_DAY;
        int currentm = Calendar.MINUTE;
    	String start = meeting.getMeetingStartTime();
		int starth = Integer.parseInt(start.substring(0, start.indexOf(':')));
		int startm = Integer.parseInt(start.substring(start.indexOf(':') + 1));
    	int tracktime = meeting.getMeetingTrackTime();
    	int minutes_before = ((currenth - starth) * 60) + (currentm - startm);
    	if (minutes_before > tracktime){
	    	trackbutton.setVisibility(View.GONE);
    	}
    }

	public void back(View Button){
		onBackPressed();
	}
	
	
    @Override
    public void onBackPressed(){
    	finish();
    	
    }
    
    
    public void setalarm(View button){
    	//TODO set alarm code here
    	
    
    }
    
    public void takepic(View button){
    	//TODO taking picture code here
    }

    public void call(View button){
    	//TODO
    }

    public void edit(View Button){
		Intent intent = new Intent(DisplayAccepted.this, EditMeeting.class);
		intent.putExtra("mid", meeting.getMeetingID());
		startActivity(intent);
  
    }
    public void track(View Button){
		Intent intent = new Intent(DisplayAccepted.this, TrackerMap.class);
		intent.putExtra("mid", meeting.getMeetingID());
		startActivity(intent);
  
    }
    public void decline(View Button){
        Communicator.declineMeeting(uid, meeting.getMeetingID());
        db.open();
        db.updateMeetingUser(meeting.getMeetingID(), uid, MeetingPlannerDatabaseHelper.ATTENDINGSTATUS_DECLINING, "0");
        db.close();
  
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
