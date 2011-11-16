package com.uiproject.meetingplanner;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseHelper;
import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseManager;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TrackerEtaList extends Activity {

	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	private ListView attendeesList;
	private ArrayList<UserInstance> attendees;
	private ArrayAdapter<UserInstance> adapter; 
    private MeetingPlannerDatabaseManager db;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackeretalist);
        
        db = new MeetingPlannerDatabaseManager(this, MeetingPlannerDatabaseHelper.DATABASE_VERSION);
        SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
        int mid = settings.getInt("currentTrackingMid", -1);
        
        db.open();
        db.getMeetingUsersArray(mid);
        db.close();
        
        attendeesList =  (ListView) findViewById(R.id.attendeesList);
        attendees = new ArrayList<UserInstance>();
        adapter = new ArrayAdapter<UserInstance>(this, R.layout.tracker_list_item, attendees);
        attendeesList.setAdapter(adapter);
    }
    
    public void updateList(Map<String, Object> map) {
    	attendees.clear();
    	Map<Integer, UserInstance> map2 = (Map<Integer, UserInstance>) map.get("locations");
    	Set<Integer> keys = map2.keySet();
    	for (Integer i : keys){
    		attendees.add(map2.get(i));
    	}
    	adapter = new ArrayAdapter<UserInstance>(this, R.layout.tracker_list_item, attendees);
        attendeesList.setAdapter(adapter);
    }
    
    public void toMap(View button){  	
    	TrackerEtaList.this.finish();
    }
    
}
