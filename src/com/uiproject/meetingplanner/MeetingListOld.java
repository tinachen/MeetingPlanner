package com.uiproject.meetingplanner;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseHelper;
import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseManager;

public class MeetingListOld extends Activity{

	public static final String TAG = "MeetingListOld";
	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	private MeetingPlannerDatabaseManager db;
	private ArrayList<MeetingInstance> meetings;
	private ListView list;
	private ArrayAdapter<MeetingInstance> adapter;
	private int uid;

	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.old_list);

        db = new MeetingPlannerDatabaseManager(this, MeetingPlannerDatabaseHelper.DATABASE_VERSION);
		list = (ListView) findViewById(R.id.list);
		list.setClickable(true);
		
		repopulate();
        
       
	}
	
	//TODO
	public void repopulate(){
		
		SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
    	uid = settings.getInt("uid", -1);
		
		db.open();
	    meetings = db.getOldMeetings(uid);
		db.close();
		
		adapter = new MeetingListArrayAdapter(this, R.layout.all_list_item, meetings, MeetingListArrayAdapter.LISTTYPE_ACCEPTED, uid);
		
		list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				MeetingInstance m = meetings.get(position);
				int mid = m.getMeetingID();
				//TODO
				Intent intent = new Intent(MeetingListOld.this, DisplayMeeting.class);
				intent.putExtra("mid", mid);
				intent.putExtra("status", MeetingPlannerDatabaseHelper.ATTENDINGSTATUS_ATTENDING);
				startActivity(intent);
				//TODO
				
				Log.d(TAG, "onCreate: ListItem Click " + position + " " + arg3);
				Log.d(TAG, "onCreate: Meeting ID " + m.getMeetingID() + " selected ");
			}
		});
	}
	
	
	public void onResume(){
		super.onResume();
		repopulate();
		
	}
	
	
}