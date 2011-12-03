package com.uiproject.meetingplanner;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseHelper;
import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseManager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainPage extends Activity {
    /** Called when the activity is first created. */

	TextView name, mtitle, mdate, mtime, mcreator, nomeeting;
	ImageView trackbutton;
	View nextmeeting;
	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	public static final String mainPageTag = "MainPage";
	private MeetingPlannerDatabaseManager db;
	private int uid;
	public static GeoUpdateHandler guh;
	private LocationManager locationManager;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
        
        name = (TextView) findViewById(R.id.name);

    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
    	String username = settings.getString("userFirstName", "") + " " + settings.getString("userLastName", "");
    	
    	if (username.length() == 1){
    		name.setText("Add your name");
    		name.setClickable(true);
    		name.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(MainPage.this, EditProfile.class);
                    MainPage.this.startActivity(intent);
                }
            });
    		
    	}else{
    		name.setText(username);
    	}


	    mtitle = (TextView) findViewById(R.id.mnexttitle);
	    mdate = (TextView) findViewById(R.id.mdate);
	    mtime = (TextView) findViewById(R.id.mtime);
	    mcreator = (TextView) findViewById(R.id.mcreator);
	    trackbutton = (ImageView) findViewById(R.id.displaytrackbutton);
    	nextmeeting = findViewById(R.id.nextmeetingview);
    	nomeeting = (TextView) findViewById(R.id.nomeeting);
    	// Hook up with database
	    db = new MeetingPlannerDatabaseManager(this, MeetingPlannerDatabaseHelper.DATABASE_VERSION);
	    uid = settings.getInt("uid", -1);
	    
	    guh = new GeoUpdateHandler(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, guh);
		
		Log.d(mainPageTag, "Create GeoUpdateHandler");
		
    }

    public void gotoMyMeetings(View button){
        Intent intent = new Intent(MainPage.this, AllMeetings.class);
        MainPage.this.startActivity(intent);
    	
    }
    
    public void gotomeeting(View view){
		int mid = (Integer) view.getTag();
		Intent intent = new Intent(MainPage.this, DisplayMeeting.class);
		intent.putExtra("mid", mid);
		intent.putExtra("status", MeetingPlannerDatabaseHelper.ATTENDINGSTATUS_ATTENDING);
		startActivity(intent);
    	
    }

    public void createMeeting(View button){


    	startActivity(new Intent(MainPage.this, CreateMeetingWhat.class));

    }
    
    public void track(View button){
    	String tag = button.getTag().toString();
    	int mid = Integer.parseInt(tag);
		Intent intent = new Intent(MainPage.this, TrackerMap.class);
		intent.putExtra("mid", mid);
		startActivity(intent);
    	
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
                MainPage.this.startActivityForResult(intent, 0);
                break;
            }   
	        case R.id.help:{
				Intent intent = new Intent(MainPage.this, HelpPage.class);
				startActivity(intent);
	        	break;
	        }
            case R.id.logout:{
            	Logout.logout(this);
            	break;
            }
        }
        return true;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == R.string.edited_profile) {
        	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
        	String username = settings.getString("userFirstName", "") + " " + settings.getString("userLastName", "");
    		name.setText(username);
        }
    }
    
    public void onStart(){
    	super.onStart();
	    Log.d(mainPageTag, "in onStart");
    	
    	//recheck the next upcoming meeting

    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
    	SharedPreferences.Editor editor = settings.edit();
    	
    	db.open();
	    MeetingInstance m = db.getNextUpcomingMeeting(uid);
	    Log.d(mainPageTag, "getNextUpcomingMeeting: " + "meetingID = " + m.getMeetingID());
	    db.close();
	    
	    int mid = m.getMeetingID();
	    
	    //Log.d(mainPageTag, "track mid = " + mid);
	    
	    if(mid < 0){
	    	nomeeting.setVisibility(View.VISIBLE);
	    	mtitle.setVisibility(View.GONE);
	    	mtime.setVisibility(View.GONE);
	    	mdate.setVisibility(View.GONE);
	    	mcreator.setVisibility(View.GONE);
	    	trackbutton.setVisibility(View.GONE);	
	    	
	    	// Set sharedpreferences
	    	editor.putInt("currentTrackingMid", -1);
	    }else{
	    	mdate.setText(m.getMeetingDate());
	    	mtime.setText( m.getMeetingStartTime() + "-" + m.getMeetingEndTime());
	    	int creatorid = m.getMeetingInitiatorID();
	    	db.open();
	    	UserInstance u = db.getUser(creatorid);
	    	db.close();
	    	mcreator.setText(u.getUserFirstName() + " " + u.getUserLastName());
	    	//String shortTitle = "";
	    	//if(m.getMeetingTitle().length() < 25){
	    	//	shortTitle = m.getMeetingTitle().substring(0, m.getMeetingTitle().length());
	    	//}else{
	    	//	shortTitle = m.getMeetingTitle().substring(0, 25) + "...";
	    	//}
	    	
	    	mtitle.setText(m.getMeetingTitle());
	    	trackbutton.setTag(mid);
	    	Calendar cal = new GregorianCalendar();
	        int currenth = cal.get(Calendar.HOUR_OF_DAY);
	        int currentm = cal.get(Calendar.MINUTE);

	    	String start = m.getMeetingStartTime();
			int starth = Integer.parseInt(start.substring(0, start.indexOf(':')));
			int startm = Integer.parseInt(start.substring(start.indexOf(':') + 1));
	    	int tracktime = m.getMeetingTrackTime();
	    	int minutes_before = ((currenth - starth) * 60) + (currentm - startm);
	    	Log.d(mainPageTag, "minutes before = " + minutes_before + ", tracktime = " + tracktime 
	    			+ ", currenth = " + currenth + ", starth = " + starth + ", currentm = " + currentm + ", startm = " + startm);
	    	if (minutes_before > tracktime){
	    		trackbutton.setVisibility(View.GONE);
	    	}else{
	    		trackbutton.setVisibility(View.VISIBLE);

	    	}

	    	nextmeeting.setTag(mid);

	    	nomeeting.setVisibility(View.GONE);
	    	mtitle.setVisibility(View.VISIBLE);
	    	mtime.setVisibility(View.VISIBLE);
	    	mdate.setVisibility(View.VISIBLE);
	    	mcreator.setVisibility(View.VISIBLE);
	    	
	    	// Set sharedpreferences
	    	editor.putInt("currentTrackingMid", mid);
	    }
    	
    }
    
    
}

