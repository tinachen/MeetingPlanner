package com.uiproject.meetingplanner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseHelper;
import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseManager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ExpandableListActivity;
import android.app.ListActivity;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;

public class MeetingListAccepted extends ExpandableListActivity {
    
    /** Called when the activity is first created. */
    ExpandableListAdapter mAdapter;
    private MeetingPlannerDatabaseManager db;
    public ArrayList<MeetingInstance> allMeet;
    public static final String PREFERENCE_FILENAME = "MeetAppPrefs";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //final LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Hook up with database
	    db = new MeetingPlannerDatabaseManager(this, MeetingPlannerDatabaseHelper.DATABASE_VERSION);
	    db.open();

	    db.createUser(1, "Laura", "Rodriguez", "lau.rodriguez@gmail", "3128573352", 37, -34);
	    db.createUser(2, "Dummy", "Joe", "tt@gmail.com", "1234567778", 32, 34);
	    db.createMeeting(2,"Drinking party", 32, -35, "Happy Hour Drinks", "RTCC 202", "10/31/2011", "6:30pm", "9:00pm", 5, 5);//TODO
	    db.createMeeting(5,"Drinking party", 32, -35, "Happy Hour Drinks", "RTCC 202", "10/31/2011", "6:30pm", "9:00pm", 5, 5);//TODO
	    db.createMeeting(6,"Drinking party", 32, -35, "Happy Hour Drinks", "RTCC 202", "10/31/2011", "6:30pm", "9:00pm", 5, 5);//TODO
	    db.createMeeting(7,"Drinking party", 32, -35, "Happy Hour Drinks", "RTCC 202", "10/31/2011", "6:30pm", "9:00pm", 5, 5);//TODO
	    db.createMeetingUser(2, 1, 2, "Hello");
	    db.createMeetingUser(2, 2, 1, "Hello2");
	    
	    SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
    	int uid = settings.getInt("uid", -1);
	    allMeet = db.getAcceptedMeetings(uid);
        // Set up our adapter

	    String s = "meeting size:" + allMeet.size();
    	Toast.makeText(MeetingListAccepted.this, s, Toast.LENGTH_SHORT).show();
	    
        mAdapter = new MyExpandableListAdapter(allMeet);
        setListAdapter(mAdapter);
        registerForContextMenu(getExpandableListView());
       
	    
    }

    public ArrayList<MeetingInstance> getMeet()
    {
    	String TAG = "MeetingTracker";
    	Log.v(TAG, "Size is " + allMeet.size());
    	return allMeet;
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Sample menu");
        //.add(0, 0, 0, R.layout.main);
    }

    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();

        String title = ((TextView) info.targetView).getText().toString();

        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition); 
            int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition); 
            Toast.makeText(this, title + ": Child " + childPos + " clicked in group " + groupPos,
                    Toast.LENGTH_SHORT).show();
            return true;
        } else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition); 
            Toast.makeText(this, title + ": Group " + groupPos + " clicked", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }
    
    public void onClick(View v) {
    	Toast.makeText(getApplicationContext(), "You clicked!", Toast.LENGTH_SHORT).show();
    }
    /*
    
    // add a click listener to the button
    mSelectMeeting.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
        	//showDialog(MEETING_DIALOG_ID);
        }});
    }
    */
    
    public class MyExpandableListAdapter extends BaseExpandableListAdapter {
        // Sample data set.  children[i] contains the children (String[]) for groups[i].
        private String[] groups;
    	private String[] children;
    	private ArrayList<MeetingInstance> allMeetings;
    	
        public MyExpandableListAdapter(ArrayList<MeetingInstance> allMeet) {
			// TODO Auto-generated constructor stub
        	allMeetings = allMeet;
        	//String TAG = "MeetingTracker";
        	//Log.v(TAG, "Size is " + allMeetings.size());
        	groups = new String[allMeetings.size()];
        	children = new String[allMeetings.size()];
        	
        	//Log.v(TAG, "Groups is " + groups.length);
        	//Log.v(TAG, "Children is " + children.length);
        	
        	for (int i = 0; i < allMeetings.size(); i++)
        	{
        		//Log.v(TAG, "Element number " + i + " is " + allMeetings.get(i).getMeetingSubject());
        		groups[i] = allMeetings.get(i).getMeetingTitle() + "\n\n" + "Fixme" + "\t\t" + allMeetings.get(i).getMeetingDate() + "\t" + allMeetings.get(i).getMeetingStartTime();
        		children[i] = allMeetings.get(i).getMeetingDescription() + "\n" +
        									allMeetings.get(i).getMeetingAddress() + "\n" +
        									allMeetings.get(i).getMeetingDate() + "  " + allMeetings.get(i).getMeetingStartTime() + " to " + allMeetings.get(i).getMeetingEndTime() + "\n" +
        									allMeetings.get(i).getMeetingAttendees().toString() + "\n";
         	}
        	
        	//Log.v(TAG, "I am at the end! ");
		}
        
        public Object getChild(int groupPosition, int childPosition) {
            return children[groupPosition];
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        public TextView getGenericView() {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, 100);

            TextView textView = new TextView(MeetingListAccepted.this);
            textView.setLayoutParams(lp);
            // Center the text vertically
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            // Set the text starting position
            textView.setPadding(80, 0, 0, 0);
            textView.setTextSize(14);
            return textView;
        }

        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                View convertView, ViewGroup parent) {
            
        	TextView textView = getGenericView();
            textView.setText(getChild(groupPosition, childPosition).toString());
            Button btn = (Button)textView.findViewById(R.id.editBtn);
            btn.setFocusable(false);
            btn.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "You clicked!", Toast.LENGTH_SHORT).show();
				}
			});
			
            return textView;
            /*
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem, null);
            
        	View v = inflater.inflate(R.layout.expander_child, null);

            Button button = (Button)v.findViewById(R.id.expand_child_button);
            button.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(ExpandableList1.this, "button pushed", Toast.LENGTH_SHORT).show();
                }
            });
                     
            return v;
            */
        }

        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }

        public int getGroupCount() {
            return allMeetings.size();
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                ViewGroup parent) {
            TextView textView = getGenericView();
            textView.setText(getGroup(groupPosition).toString());
            return textView;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) { 
            Button cb = (Button)v.findViewById( R.id.editBtn );
            if( cb != null )
                cb.bringToFront();
            return false;
        }
        public boolean hasStableIds() {
            return true;
        }

    }
    
}