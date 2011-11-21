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
import android.widget.ImageView;
import android.widget.TextView;

import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseHelper;
import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseManager;

public class DisplayMeeting extends Activity {

	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	TextView title, desc, date, time, tracktime, attendees, location, status;
	ImageView trackbutton, callbutton, editbutton, alarmbutton, camerabutton, acceptbutton, declinebutton;
	MeetingInstance meeting;
	private MeetingPlannerDatabaseManager db;
	private int uid, statusid;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displaymeeting);
        title = (TextView) findViewById(R.id.title);
        desc = (TextView) findViewById(R.id.desc);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        tracktime = (TextView) findViewById(R.id.tracktime);
        attendees = (TextView) findViewById(R.id.attendees);
        location = (TextView) findViewById(R.id.location);
        status = (TextView) findViewById(R.id.status);
        trackbutton = (ImageView) findViewById(R.id.displaytrackbutton);
        callbutton = (ImageView) findViewById(R.id.displaycallbutton);
        editbutton = (ImageView) findViewById(R.id.displayeditbutton);
        alarmbutton = (ImageView) findViewById(R.id.displayalarmbutton);
        camerabutton = (ImageView) findViewById(R.id.displaycamerabutton);
        acceptbutton = (ImageView) findViewById(R.id.displayacceptbutton);
        declinebutton = (ImageView) findViewById(R.id.displaydeclinebutton);

    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
    	uid = settings.getInt("uid", -1);

        int mid = getIntent().getIntExtra("mid", -1);
        statusid = getIntent().getIntExtra("status", -1);
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
    	
    	
    	if (statusid == MeetingPlannerDatabaseHelper.ATTENDINGSTATUS_ATTENDING){
    		status.setText("Attending");
    		attendingButtons();
    	}else if(statusid == MeetingPlannerDatabaseHelper.ATTENDINGSTATUS_DECLINING){
    		status.setText("Declined");
    		declineButtons();
    	}else{
    		status.setText("Pending");
    		pendingButtons();
    	}
    }
	
	public void attendingButtons(){

		status.setText("Attending");
    	if (meeting.getMeetingInitiatorID() != uid){
    		editbutton.setVisibility(View.GONE);
    		camerabutton.setVisibility(View.GONE);
    		callbutton.setVisibility(View.VISIBLE);
    		declinebutton.setVisibility(View.VISIBLE);
    	}else{
    		callbutton.setVisibility(View.GONE);
    		declinebutton.setVisibility(View.GONE);
    		editbutton.setVisibility(View.VISIBLE);
    		camerabutton.setVisibility(View.VISIBLE);
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
    	}else{
	    	trackbutton.setVisibility(View.VISIBLE);    		
    	}
    	acceptbutton.setVisibility(View.GONE);
	}
	
	public void declineButtons(){
		editbutton.setVisibility(View.GONE);
		camerabutton.setVisibility(View.GONE);
		callbutton.setVisibility(View.GONE);
		declinebutton.setVisibility(View.GONE);
    	trackbutton.setVisibility(View.GONE);  
    	acceptbutton.setVisibility(View.VISIBLE);  		
		
	}
	
	public void pendingButtons(){
		editbutton.setVisibility(View.GONE);
		camerabutton.setVisibility(View.GONE);
		callbutton.setVisibility(View.GONE);
    	trackbutton.setVisibility(View.GONE);   
    	acceptbutton.setVisibility(View.VISIBLE);  	
    	declinebutton.setVisibility(View.VISIBLE);  	 	
		
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
		Intent intent = new Intent(DisplayMeeting.this, EditMeeting.class);
		intent.putExtra("mid", meeting.getMeetingID());
		startActivity(intent);
  
    }
    public void track(View Button){
		Intent intent = new Intent(DisplayMeeting.this, TrackerMap.class);
		intent.putExtra("mid", meeting.getMeetingID());
		startActivity(intent);
  
    }
    
    public void accept(View Button){
    	attendingButtons();
    	status.setText("Attending");
        Communicator.acceptMeeting(uid, meeting.getMeetingID());
        db.open();
        db.updateMeetingUser(meeting.getMeetingID(), uid, MeetingPlannerDatabaseHelper.ATTENDINGSTATUS_ATTENDING, "0");
        db.close();
    }    
    
    public void decline(View Button){
    	declineButtons();
    	status.setText("Declined");
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
