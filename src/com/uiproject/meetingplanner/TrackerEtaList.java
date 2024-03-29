package com.uiproject.meetingplanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TrackerEtaList extends Activity {

	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	private ListView attendeesList;
	private ArrayList<UserInstance> attendees;
	private TrackerAdapter adapter;
    private MeetingPlannerDatabaseManager db;
    private ArrayList<UserInstance> meetingUsers;
    private MeetingInstance meetingInfo;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackeretalist);
        
        db = new MeetingPlannerDatabaseManager(this, MeetingPlannerDatabaseHelper.DATABASE_VERSION);
        int mid = getIntent().getIntExtra("mid", -1);
        
        db.open();
        meetingInfo = db.getMeeting(mid);
        meetingUsers = db.getMeetingUsersArray(mid);
        db.close();
        
        TextView tvMeetingName = (TextView) findViewById(R.id.meetingName);
        tvMeetingName.setText(meetingInfo.getMeetingTitle());
        
        TextView tvMeetingLocation = (TextView) findViewById(R.id.location);
        tvMeetingLocation.setText(meetingInfo.getMeetingAddress());
        //tvMeetingLocation.setText("TCC 330");
        
        TextView tvMeetingDate = (TextView) findViewById(R.id.date);
        tvMeetingDate.setText(meetingInfo.getMeetingDate());
        
        TextView tvMeetingTime = (TextView) findViewById(R.id.time);
        tvMeetingTime.setText(meetingInfo.getMeetingStartTime());
        
        attendeesList =  (ListView) findViewById(R.id.attendeesList);
        attendeesList.setClickable(false);
        
        attendees = new ArrayList<UserInstance>();
        
//      attendees.add(new UserInstance(2, "Elizabeth", "Deng", "elizabethdeng@gmail.com", "847-322-1831", 200, 200, "10 mins", "attending"));
//      attendees.add(new UserInstance(4, "Tina", "Chen", "tinac@usc.edu", "626-376-1106", 200, 200, "1 hr 30 mins", "attending"));
//		attendees.add(new UserInstance(1,"Cauchy","Choi","cauchych@usc.edu","408-499-1666",100,100,"2 hrs 30 mins","attending"));
		
        
		adapter = new TrackerAdapter(this, attendees, meetingUsers, meetingInfo);
		attendeesList.setAdapter(adapter);
		
		// Dynamically register receiver here
		TestReceiver receiver = new TestReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.uiproject.meetingplanner");
		registerReceiver(receiver, filter);
		
//		for (int i = 0; i < attendees.size(); i++) {
//			eta.setText(attendees.get(i).getUserEta());
//			trackerName.setText((attendees.get(i).getUserFirstName() + " " + attendees.get(i).getUserLastName()));
//		}
    }
    
    public void updateList(Map<Integer, UserInstance> map) {
    	attendees.clear();
    	Set<Integer> keys = map.keySet();
    	for (Integer i : keys){
    		attendees.add(map.get(i));
    		Log.d("map size", ""+map.size());
    		//attendeeNames.add(map2.get(i).getUserFirstName() + " " + map2.get(i).getUserLastName());
    	}
    	adapter = new TrackerAdapter(this, attendees, meetingUsers, meetingInfo);
        attendeesList.setAdapter(adapter);
    }
    
    public void toMap(View button){  	
    	TrackerEtaList.this.finish();
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
    
  //Receive broadcast msg from communicator
    public class TestReceiver extends BroadcastReceiver {  
    	public TestReceiver (){
    	}

        public void onReceive(Context context, Intent intent) { 
            // TODO Auto-generated method stub       
            Log.d ("Receiver","Success2");
            Bundle message = intent.getBundleExtra("message");
            int tag = message.getInt("tag");
            Bundle locations = message.getBundle("locations");
            Log.d("BUNDLE",locations.toString());
            Map<Integer,UserInstance> userLocations = new HashMap<Integer, UserInstance>();
            for (String i : locations.keySet()){
            	Bundle location = locations.getBundle(i);
            	userLocations.put(Integer.valueOf(i), new UserInstance(Integer.valueOf(i),location.getInt("lat"),location.getInt("lon"),location.getString("eta")));
            }
            updateList(userLocations);
            Log.d("tag","tag: "+tag);
            Log.d("AAA","userId: "+6);

            Log.d("AAA","lat: "+userLocations.get(1).getUserLocationLat());
            Log.d("AAA","lon: "+userLocations.get(1).getUserLocationLon());
            Log.d("AAA","eta: "+userLocations.get(1).getUserEta());

            
        } 
        
    } 
    
}
