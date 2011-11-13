package com.uiproject.meetingplanner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseManager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ExpandableListActivity;
import android.app.ListActivity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;

public class MeetingListPending extends ListActivity {

	    /** Called when the activity is first created. */
	
		private MeetingPlannerDatabaseManager db;
		public ArrayList<MeetingInstance> allMeet;
		public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        //setContentView(R.layout.meetingitems);
	        // Hook up with database
		    db = new MeetingPlannerDatabaseManager(this, 2);
		    db.open();
		    db.createMeeting(4,"CS588 Progress", 32, -35, "Happy Hour Drinks", "RTCC 202", "10/31/2011", "6:30pm", "9:00pm", 5, 5);

		    SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
	    	int uid = settings.getInt("uid", -1);
		    allMeet = db.getPendingMeetings(uid);
		    
	        ArrayAdapter<MeetingInstance> adapter = new MeetingListArrayAdapter(this, allMeet);
	        setListAdapter(adapter);

	        
	    }
    
}