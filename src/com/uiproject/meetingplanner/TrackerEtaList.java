package com.uiproject.meetingplanner;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TrackerEtaList extends Activity {

	private ListView attendeesList;
	private ArrayList<UserInstance> attendees;
	private ArrayAdapter<UserInstance> adapter; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackeretalist);
        
        attendeesList =  (ListView) findViewById(R.id.attendeesList);
        attendees = new ArrayList<UserInstance>();
        adapter = new ArrayAdapter<UserInstance>(this, R.layout.tracker_list_item, attendees);
        attendeesList.setAdapter(adapter);
    }
    
    public void updateList(Map<Integer, UserInstance> map) {
    	attendees.clear();

    	Set<Integer> keys = map.keySet();
    	for (Integer i : keys){
    		attendees.add(map.get(i));
    	}
    	adapter = new ArrayAdapter<UserInstance>(this, R.layout.tracker_list_item, attendees);
        attendeesList.setAdapter(adapter);
    }
    
    public void toMap(View button){  	
    	TrackerEtaList.this.finish();
    }
    
}
