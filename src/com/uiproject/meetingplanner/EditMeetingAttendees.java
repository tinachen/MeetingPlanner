package com.uiproject.meetingplanner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class EditMeetingAttendees extends Search {

	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editmeetingattendees);
        init();
    }

    public void confirm(View button){
    	onBackPressed();
    }
    

    @Override
    public void onBackPressed(){
    	saveData();
    	EditMeetingAttendees.this.finish();
    	
    }
    
    private void saveData(){

    	String names = "";
    	String uids = "";
    	boolean added = false;
    	for (UserInstance u : checkedUsers){
    		if (added){
    			names += ", ";
    			uids += ",";
    		}
    		names = names + u.getUserFirstName() + " " + u.getUserLastName();
    		uids += u.getUserID();
    		added = true;
    	}
    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
    	SharedPreferences.Editor editor = settings.edit();
    	editor.putString("mnames", names);
    	editor.putString("mattendeeids", uids);
    	editor.commit();
    	
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
            	Logout.logout(this);
            	break;
            }
        }
        return true;
    }
}
