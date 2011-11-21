package com.uiproject.meetingplanner;

import java.util.ArrayList;
import java.util.List;

import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseHelper;
import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseManager;

import android.app.Activity;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

public class CustomListActivity extends Activity {
    /** Called when the activity is first created. */
	static  View bar;
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

    	//  MeetingListArrayAdapter adapter = new MeetingListArrayAdapter(this, allMeet);
        MeetingAdapter adapter = new MeetingAdapter(this,allMeet);

        list.setAdapter(adapter);
        
        bar = (View)findViewById(R.id.linearLayout1);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list.setItemsCanFocus(false);
        list.setClickable(true);
        list.setSelected(true);
        /*
        CheckBox cb = (CheckBox) findViewById(R.id.checkBox1); 
        cb.setOnCheckedChangeListener(new OnCheckedChangeListener() { 

        	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
        	// TODO Auto-generated method stub 
            if (buttonView.isChecked()) { 
            	Toast.makeText(getBaseContext(), "Checked", 
            	Toast.LENGTH_SHORT).show(); 
            } 
            else 
            { 
            	Toast.makeText(getBaseContext(), "UnChecked", 
            	Toast.LENGTH_SHORT).show(); 
            } 
          } 
        }); 
        */
        list.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                    int arg2, long arg3) {
                // TODO Auto-generated method stub
            	Toast.makeText(getBaseContext(), "Checked", 
                    	Toast.LENGTH_SHORT).show(); 
            	Log.d("checked",""+arg2);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }

        });
    }
	
	public static void showBar(){
		bar.setVisibility(View.VISIBLE);
	}
	public static void hideBar(){
		bar.setVisibility(View.GONE);
	}

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}