package com.uiproject.meetingplanner;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainPage extends Activity {
    /** Called when the activity is first created. */

	TextView name;
	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
        
        name = (TextView) findViewById(R.id.name);

    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
    	String username = settings.getString("name", "my name!");
    	name.setText(username);
        
    }

    public void gotoMyMeetings(View button){
        Intent intent = new Intent(MainPage.this, MeetingList.class);
        MainPage.this.startActivity(intent);
    	
    }
    
    public void createMeeting(View button){

        Intent intent = new Intent(MainPage.this, CreateMeetingWhat.class);
        MainPage.this.startActivity(intent);
    }
    
    // menu 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editprofile:{
                Intent intent = new Intent(MainPage.this, EditProfile.class);
                MainPage.this.startActivity(intent);
                break;
            }       
            case R.id.editmeeting:{
                Intent intent = new Intent(MainPage.this, EditMeeting.class);
                MainPage.this.startActivity(intent);
            	break;
            }
        }
        return true;
    }

}

