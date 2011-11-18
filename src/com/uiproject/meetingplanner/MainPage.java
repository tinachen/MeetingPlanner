package com.uiproject.meetingplanner;

import java.util.HashMap;
import java.util.Map;

import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseHelper;
import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseManager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
	public static final String mainPageTag = "MainPage";
	private MeetingPlannerDatabaseManager db;
	
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

    	// Hook up with database
	    db = new MeetingPlannerDatabaseManager(this, MeetingPlannerDatabaseHelper.DATABASE_VERSION);
	    
	    //test get next meeting works
	    int uid = settings.getInt("uid", -1);
	    db.open();
	    MeetingInstance m = db.getNextUpcomingMeeting(uid);
	    Log.v(mainPageTag, "getNextUpcomingMeeting: " + "meetingID = " + m.getMeetingID());
	    db.close();
    }

    public void gotoMyMeetings(View button){
        Intent intent = new Intent(MainPage.this, AllMeetings.class);
        MainPage.this.startActivity(intent);
    	
    }
 //**************************************************************   
    public class TestReceiver extends BroadcastReceiver { 
    	public TestReceiver (){
    	}

        public void onReceive(Context context, Intent intent) { 
            // TODO Auto-generated method stub       
            Log.d ("Receiver","Success");
            Bundle message = intent.getBundleExtra("message");
            int tag = message.getInt("tag");
            Bundle locations = message.getBundle("locations");
            Map<Integer,UserInstance> userLocations = new HashMap<Integer, UserInstance>();
            for (String i : locations.keySet()){
            	Bundle location = locations.getBundle(i);
            	userLocations.put(Integer.valueOf(i), new UserInstance(Integer.valueOf(i),location.getInt("lat"),location.getInt("lon"),location.getString("eta")));
            }
            Log.d("tag","tag: "+tag);
            Log.d("AAA","userId: "+6);
            Log.d("AAA","lat: "+userLocations.get(6).getUserLocationLat());
            Log.d("AAA","lon: "+userLocations.get(6).getUserLocationLon());
            Log.d("AAA","eta: "+userLocations.get(6).getUserEta());
        } 
    } 
   
 //*******************************************************************
    public void createMeeting(View button){


    	startActivity(new Intent(MainPage.this, CreateMeetingWhat.class));
		Intent intent = new Intent(MainPage.this, CommunicateService.class);
		startService(intent);
		
		TestReceiver receiver =new TestReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.uiproject.meetingplanner");
		registerReceiver(receiver, filter);
		
		

//		Intent intent = new Intent(MainPage.this, CreateMeetingWhat.class);
//		startActivity(intent);


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
            case R.id.editmeeting:{
                Intent intent = new Intent(MainPage.this, EditMeeting.class);
                MainPage.this.startActivity(intent);
            	break;
            }
            case R.id.trackermap:{
                Intent intent = new Intent(MainPage.this, TrackerMap.class);
                MainPage.this.startActivity(intent);
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
    
    
}

