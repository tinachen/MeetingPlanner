package com.uiproject.meetingplanner;

import java.util.ArrayList;
import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseHelper;
import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MeetingListPendingTest extends Activity{

	public static final String TAG = "MeetingListPendingTest";
	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	public static View confirmBar;
	private Button acceptBtn, declineBtn;
	private MeetingPlannerDatabaseManager db;
	private ArrayList<MeetingInstance> meetings;

	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pending_list);
		
		SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
    	final int uid = settings.getInt("uid", -1);
		
		//Hook up the database
        db = new MeetingPlannerDatabaseManager(this, MeetingPlannerDatabaseHelper.DATABASE_VERSION);
	    db.open();
	    meetings = db.getPendingMeetings(uid);
		db.close();
		// Create an array of Strings, that will be put to our ListActivity
		final ArrayAdapter<MeetingInstance> adapter = new MeetingListArrayAdapter(this, R.layout.all_list_item, meetings, MeetingListArrayAdapter.LISTTYPE_PENDING, uid);
		
		
		acceptBtn = (Button) findViewById(R.id.pendingAcceptBtn);
		declineBtn = (Button) findViewById(R.id.pendingDeclineBtn);
		confirmBar = (View) findViewById(R.id.confirmBarPending);
		
		
		ListView list = (ListView) findViewById(R.id.list);
        list.setClickable(true);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				MeetingInstance m = meetings.get(position);
				
				int mid = m.getMeetingID();

				Intent intent = new Intent(MeetingListPendingTest.this, DisplayMeeting.class);
				intent.putExtra("mid", mid);
				intent.putExtra("status", MeetingPlannerDatabaseHelper.ATTENDINGSTATUS_PENDING);
				startActivity(intent);
				
				//TODO meeting description
				
				Log.d(TAG, "onCreate: ListItem Click " + position + " " + arg3);
				Log.d(TAG, "onCreate: Meeting ID " + m.getMeetingID() + " selected ");
				hideBar();
			}
		});
        
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
             // Perform action on click
            	
            ArrayList<MeetingInstance> meetingsRemove = new ArrayList<MeetingInstance>(); 	
           	 
           	 for(int i = 0; i < meetings.size(); i++){
           		 MeetingInstance m = meetings.get(i);
           		 
           		 if(m.isSelected()){
           			 // Update with server
           			 Communicator.acceptMeeting(uid, m.getMeetingID());
           			 
           			 // Update with internal db
           			 db.open();
           			 db.updateMeetingUserAttendingStatus(m.getMeetingID(), uid, MeetingPlannerDatabaseHelper.ATTENDINGSTATUS_ATTENDING);
           			 db.close();
           			 
           			 meetingsRemove.add(m);
           			 
           			 Log.d(TAG, "Accept Selected - MeetingID = " + m.getMeetingID() + ", MeetingTitle = " + m.getMeetingTitle());
           		 }
           	 }
           	 
           	for(int i = 0; i < meetingsRemove.size(); i++){
          		MeetingInstance m = meetingsRemove.get(i);
         		adapter.remove(m);
           	}
           	adapter.notifyDataSetChanged();
           	 
           	hideBar();
           	 
            }});
        
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
           	}
           	adapter.notifyDataSetChanged();
           	
           	hideBar();
           	
            }});
        
        Log.d(TAG, "in onCreate");
		
	}
	
	public static void showBar(){
		confirmBar.setVisibility(View.VISIBLE);
	}
	public static void hideBar(){
		confirmBar.setVisibility(View.GONE);
	}
}
