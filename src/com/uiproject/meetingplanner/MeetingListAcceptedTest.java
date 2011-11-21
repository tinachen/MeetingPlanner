package com.uiproject.meetingplanner;

import java.util.ArrayList;
import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseHelper;
import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MeetingListAcceptedTest extends Activity{

	public static final String TAG = "MeetingListAcceptedTest";
	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	public static View confirmBar;
	private Button declineBtn;
	private MeetingPlannerDatabaseManager db;
	private ArrayList<MeetingInstance> meetings;
	private ListView list;
	private ArrayAdapter<MeetingInstance> adapter;
	private int uid;

	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accepted_list);

        db = new MeetingPlannerDatabaseManager(this, MeetingPlannerDatabaseHelper.DATABASE_VERSION);
        declineBtn = (Button) findViewById(R.id.acceptedDeclineBtn);
		confirmBar = (View) findViewById(R.id.confirmBarAccepted);
		list = (ListView) findViewById(R.id.list);
		list.setClickable(true);
		
		repopulate();
        
        declineBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
             // Perform action on click
            
            ArrayList<MeetingInstance> meetingsRemove = new ArrayList<MeetingInstance>(); 
            	
           	 for(int i = 0; i < meetings.size(); i++){
           		 MeetingInstance m = meetings.get(i);
           		 
           		 if(m.isSelected()){
           			 // Update with server
           			 Communicator.declineMeeting(uid, m.getMeetingID());
           			 
           			 // Update with internal db
           			 db.open();
           			 db.updateMeetingUserAttendingStatus(m.getMeetingID(), uid, MeetingPlannerDatabaseHelper.ATTENDINGSTATUS_DECLINING);
           			 db.close();
           			 
           			 meetingsRemove.add(m);
           			 
           			 Log.d(TAG, "Decline Selected - MeetingID = " + m.getMeetingID() + ", MeetingTitle = " + m.getMeetingTitle());
           			 
           		 }
           	 }
           	 
           	for(int i = 0; i < meetingsRemove.size(); i++){
          		MeetingInstance m = meetingsRemove.get(i);
         		adapter.remove(m);
         		Log.d(TAG, "remove " + m.getMeetingID());
           	}
           	adapter.notifyDataSetChanged();

           	hideBar();
           	
            }});
        
		
	}
	
	public static void showBar(){
		confirmBar.setVisibility(View.VISIBLE);
	}
	public static void hideBar(){
		confirmBar.setVisibility(View.GONE);
	}
	
	//TODO
	public void repopulate(){
		
		SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
    	uid = settings.getInt("uid", -1);
		
		db.open();
	    meetings = db.getAcceptedMeetings(uid);
		db.close();
		
		adapter = new MeetingListArrayAdapter(this, R.layout.all_list_item, meetings, MeetingListArrayAdapter.LISTTYPE_ACCEPTED, uid);
		
		list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				MeetingInstance m = meetings.get(position);
				int mid = m.getMeetingID();
				
				//ELIZABETH'S CODE //TODO
				
				Log.d(TAG, "onCreate: ListItem Click " + position + " " + arg3);
				Log.d(TAG, "onCreate: Meeting ID " + m.getMeetingID() + " selected ");
				hideBar();
			}
		});
	}
	
	
	public void onResume(){
		super.onResume();
		repopulate();
		
	}
	
	
}
