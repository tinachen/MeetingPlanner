package com.uiproject.meetingplanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.uiproject.meetingplanner.TrackerMap.TestReceiver;
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

	//public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	private ListView attendeesList;
	private TextView eta;
	private TextView trackerName;
	private ArrayList<UserInstance> attendees;
	private TrackerAdapter adapter;
	private ArrayList<Tracker> trackerList;
    //private MeetingPlannerDatabaseManager db;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackeretalist);
        
        /*db = new MeetingPlannerDatabaseManager(this, MeetingPlannerDatabaseHelper.DATABASE_VERSION);
        SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
        int mid = settings.getInt("currentTrackingMid", -1);
        
        db.open();
        db.getMeetingUsersArray(mid);
        db.close();*/
        
        attendeesList =  (ListView) findViewById(R.id.attendeesList);
        attendeesList.setClickable(false);
        
        attendees = new ArrayList<UserInstance>();
        trackerList = new ArrayList<Tracker>();
        
		trackerList.add(new Tracker("Cauchy Choi", "3:00pm"));
		trackerList.add(new Tracker("Tina Chen", "4:00pm"));
        
		adapter = new TrackerAdapter(this, trackerList);
		attendeesList.setAdapter(adapter);
		
		TestReceiver receiver =new TestReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.uiproject.meetingplanner");
		registerReceiver(receiver, filter);
		
//		for (int i = 0; i < attendees.size(); i++) {
//			eta.setText(attendees.get(i).getUserEta());
//			trackerName.setText((attendees.get(i).getUserFirstName() + " " + attendees.get(i).getUserLastName()));
//		}
    }
    
    public void updateList(Map<String, Object> map) {
    	attendees.clear();
    	Map<Integer, UserInstance> map2 = (Map<Integer, UserInstance>) map.get("locations");
    	Set<Integer> keys = map2.keySet();
    	for (Integer i : keys){
    		attendees.add(map2.get(i));
    		//attendeeNames.add(map2.get(i).getUserFirstName() + " " + map2.get(i).getUserLastName());
    	}
    	adapter = new TrackerAdapter(this, trackerList);
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
    
}
