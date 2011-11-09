package com.uiproject.meetingplanner;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateMeetingWhat extends Activity {
	
	EditText title, desc;
	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createmeetingwhat);
        
        title = (EditText) findViewById(R.id.title);
        desc = (EditText) findViewById(R.id.desc);
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
    	
    	CreateMeetingWhat.this.finish();
    	
    }
    
    public void next(View button){
    	
    	String mtitle = title.getText().toString();
    	String mdesc = desc.getText().toString();
    	
    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
    	SharedPreferences.Editor editor = settings.edit();
    	editor.putString("mtitle", mtitle);
    	editor.putString("mdesc", mdesc);
    	editor.commit();
    	
		Intent intent = new Intent(CreateMeetingWhat.this, CreateMeetingWhen.class);
		CreateMeetingWhat.this.startActivityForResult(intent, 0);
    	
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == R.string.cancel_create) {
            this.finish();
        }
    }
}