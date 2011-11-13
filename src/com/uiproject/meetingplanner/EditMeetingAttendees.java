package com.uiproject.meetingplanner;

import android.content.SharedPreferences;
import android.os.Bundle;
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

    	//save data in shared preferences
    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
    	SharedPreferences.Editor editor = settings.edit();
    	//save stuff here
    	editor.commit();
    	
    }
}
