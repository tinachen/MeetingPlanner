package com.uiproject.meetingplanner;

import java.util.ArrayList;
import java.util.List;

import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseHelper;
import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseManager;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class CustomListActivity extends Activity {
    /** Called when the activity is first created. */
	private MeetingPlannerDatabaseManager db;
	public ArrayList<MeetingInstance> allMeet;
	private static final String LOG_TAG = "MeetingListDeclined";
	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.declined_meeting_custom);
        
        db = new MeetingPlannerDatabaseManager(this, MeetingPlannerDatabaseHelper.DATABASE_VERSION);
	    db.open();
	    
        ListView list = (ListView) findViewById(R.id.listView1);
        list.setClickable(true);
        
        SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
    	int uid = settings.getInt("uid", -1);
    	Log.v(LOG_TAG, "uid = " + uid);
        // Set up our adapter
    	//allMeet = db.getDeclinedMeetings(uid);
    	allMeet = db.getAllMeetings();
    	db.close();

        MeetingListArrayAdapter adapter = new MeetingListArrayAdapter(this, allMeet);

        list.setAdapter(adapter);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}