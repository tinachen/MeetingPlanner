package com.uiproject.meetingplanner;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class CreateMeetingWho extends Search {

	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createmeetingwho);
    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
    	String names = settings.getString("mnames", "");
    	String phones = settings.getString("mattendeeids", "");
        
        init();
        
        String n;
        String p;
        int commaIndex;
    	/*if (names.length() > 0){
	    	while (names.length() > 0){
	    		commaIndex = names.indexOf(',');
	    		if (commaIndex == -1){
	    			checkedNames.add(names);
	    			break;
	    		}else{
		    		n = names.substring(0, commaIndex);
		    		checkedNames.add(n);
		    		names = names.substring(commaIndex + 2);
	    		}
    		}

	    	while (phones.length() > 0){
	    		commaIndex = phones.indexOf(',');
	    		if (commaIndex == -1){
	    			checkedPhoneNumbers.add(phones);
	    			break;
	    		}else{
		    		p = phones.substring(0, commaIndex);
		    		checkedPhoneNumbers.add(p);
		    		phones = phones.substring(commaIndex + 2);
	    		}
    		}
	    	for (int i = 0; i < checkedNames.size(); i++)
	    		Log.d("CMW names", checkedNames.get(i));
	    	for (int i = 0; i < checkedPhoneNumbers.size(); i++)
	    		Log.d("CMW phone", checkedPhoneNumbers.get(i));
	    	//recheck();	
    	}*/
    }

    public void back(View button){
    	onBackPressed();
    }

    public void cancel(View button){
    	clearData();
    	CreateMeetingWho.this.setResult(R.string.cancel_create);
    	CreateMeetingWho.this.finish();
		//need to clear the previous activities too
    }
    
    private void clearData(){

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
    	editor.remove("maddr");
    	editor.remove("mlat");
    	editor.remove("mlon");
    	editor.remove("mnames");
    	editor.remove("mphones");
    	editor.commit();

    	
    }
    
    public void next(View button){
    	saveData();
		Intent intent = new Intent(CreateMeetingWho.this, CreateMeetingWhen.class);
		CreateMeetingWho.this.startActivityForResult(intent, 0);
    	
    }

    @Override
    public void onBackPressed(){
    	saveData();
    	CreateMeetingWho.this.finish();
    	
    }
    
    private void saveData(){
    	/*String names = "";
    	boolean added = false;
    	for (String n : checkedNames){
    		if (added){
    			names += ", ";
    		}
    		names += n;
    		added = true;
    	}
    	
    	String phones = "";
    	added = false;
    	for (String n : checkedPhoneNumbers){
    		if (added){
    			phones += ",";
    		}
    		phones += n;
    		added = true;    		
    	}
    	Log.d("CMW", names);
    	Log.d("CMW", phones);
    	//save data in shared preferences
    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
    	SharedPreferences.Editor editor = settings.edit();
    	editor.putString("mnames", names);
    	editor.putString("mphones", phones);
    	editor.commit();
    	*/
    }
    

    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == R.string.cancel_create) {
            this.setResult(R.string.cancel_create);
            this.finish();
        }else if (resultCode == R.string.meeting_created) {
            this.setResult(R.string.meeting_created);
            this.finish();
        }
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
	            	clearData();
	            	Logout.logout(this);
	            	break;
	            }
	        }
	        return true;
	    }
}
