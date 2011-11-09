package com.uiproject.meetingplanner;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class CreateMeetingWho extends Activity {

	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createmeetingwho);
        
    }

    public void back(View button){
    	onBackPressed();
    }

    public void cancel(View button){

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
    	
    	//remove people
    	//remove location
    	editor.commit();
    	
		finish();
		//need to clear the previous activities too
    }
    
    public void next(View button){
    	saveData();
		Intent intent = new Intent(CreateMeetingWho.this, CreateMeetingWhere.class);
		CreateMeetingWho.this.startActivity(intent);
    	
    }

    @Override
    public void onBackPressed(){
    	saveData();
    	CreateMeetingWho.this.finish();
    	
    }
    
    private void saveData(){

    	//save data in shared preferences
    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
    	SharedPreferences.Editor editor = settings.edit();
    	//save stuff here
    	editor.commit();
    	
    }
    
}
