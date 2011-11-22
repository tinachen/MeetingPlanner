package com.uiproject.meetingplanner;

import java.util.ArrayList;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
        
        String uids = getIntent().getStringExtra("uids");
        
        ArrayList<Integer> checkedUids = new ArrayList<Integer>();
        int commaIndex;
        String u = "";
    	while (uids.length() > 0){
    		commaIndex = uids.indexOf(',');
    		if (commaIndex == -1){
    			checkedUids.add(Integer.parseInt(uids));
    			break;
    		}else{
	    		u = uids.substring(0, commaIndex);
	    		checkedUids.add(Integer.parseInt(u));
	    		uids = uids.substring(commaIndex + 1);
    		}
		}
    	for (int i = 0; i < checkedUids.size(); i++)
    		Log.d("CMW uids", checkedUids.get(i).toString());
    	recheck(checkedUids);	
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
