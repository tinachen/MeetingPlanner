package com.uiproject.meetingplanner;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseHelper;
import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseManager;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    
}
