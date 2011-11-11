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
import android.os.Bundle;
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

public class MeetingList extends ExpandableListActivity {
    
    /** Called when the activity is first created. */
    ExpandableListAdapter mAdapter;
    private MeetingPlannerDatabaseManager db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up our adapter
        mAdapter = new MyExpandableListAdapter();
        setListAdapter(mAdapter);
        registerForContextMenu(getExpandableListView());
        //final ExpandableListView listView = getExpandableListView();
		//listView.setItemsCanFocus(false);
		//listView.setChoiceMode(ExpandableListView.CHOICE_MODE_MULTIPLE);
        //Button acceptBtn = (Button) findViewById(R.id.acceptBtn);
        //Button declineBtn = (Button) findViewById(R.id.declineBtn);
        /*acceptBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	Toast.makeText(getApplicationContext(), "Meeting accepted!", Toast.LENGTH_SHORT).show();
            }
        });
        declineBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	Toast.makeText(getApplicationContext(), "Meeting declined", Toast.LENGTH_SHORT).show();
            }
        });
        */
        //ArrayAdapter<MeetingInstance> adapter = new MeetingListArrayAdapter(this, getMeeting());
		//setListAdapter(adapter);
     // Hook up with database
	    db = new MeetingPlannerDatabaseManager(this, 2);
	    db.open();
	    
	    ArrayList<MeetingInstance>meetings =  db.getAllMeetings();
	    String s = "meeting size:" + meetings.size();
    	Toast.makeText(MeetingList.this, s, Toast.LENGTH_SHORT).show();
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
        private String[] groups = { "Status Meeting", "MeetApp management ", "Project Proposal", "Class discussion" };
        private String[][] children = {
                { "RTH 202\n 06/12/2012 \n 1:30pm \n John and Jane\n" },
                { "SAL 300\n 05/07/2013 \n 4:00 pm \n Jennifer and Jack\n"},
                { "RTCC 101\n 10/03/2012 \n 5:30 pm \n Melissa Arthur and Blake\n"},
                { "THH 441\n 11/15/2011 \n 9:00 am \n Paul Mike and Paula" }
        };
        
        public Object getChild(int groupPosition, int childPosition) {
            return children[groupPosition][childPosition];
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        public int getChildrenCount(int groupPosition) {
            return children[groupPosition].length;
        }

        public TextView getGenericView() {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 100);

            TextView textView = new TextView(MeetingList.this);
            textView.setLayoutParams(lp);
            // Center the text vertically
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            // Set the text starting position
            textView.setPadding(50, 0, 0, 0);
            
            return textView;
        }

        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                View convertView, ViewGroup parent) {
            TextView textView = getGenericView();
            textView.setText(getChild(groupPosition, childPosition).toString());
            return textView;
        }

        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }

        public int getGroupCount() {
            return groups.length;
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

        public boolean hasStableIds() {
            return true;
        }

    }
    
    private List<MeetingInstance> getMeeting(){
    	List<MeetingInstance> list = new ArrayList<MeetingInstance>();
    	
    	//Adding a few meetings to test
    	
    	Date myDate = new Date(06/12/2014);
    	String [] myPeople = {"John", "Jane"};
    	list.add(new MeetingInstance(1,42,37,"Status Meeting",myDate, myPeople));
    	
    	Date myDate2 = new Date(05/07/2013);
    	String [] myPeople2 = {"Jennifer", "Jack"};
    	list.add(new MeetingInstance(2,40,30,"Status Meeting 2 ",myDate2, myPeople2));
    	
    	Date myDate3 = new Date(10/03/2012);
    	String [] myPeople3 = {"Michael", "Pete"};
    	list.add(new MeetingInstance(3,35,-37,"Status Meeting 3",myDate3, myPeople3));
    	
    	Date myDate4 = new Date(10/10/2012);
    	String [] myPeople4 = {"Melissa", "Jakob"};
    	list.add(new MeetingInstance(4,-35,-37,"Status Meeting 4",myDate4, myPeople4));
    	
		list.add(new MeetingInstance("RTH"));
		list.add(new MeetingInstance("Campus Center"));
		list.add(new MeetingInstance("Campus Center2"));
		list.add(new MeetingInstance("Campus Center3"));
		list.add(new MeetingInstance("Campus Center4"));
		list.add(new MeetingInstance("Campus Center5"));
		list.add(new MeetingInstance("RTH6"));
		list.add(new MeetingInstance("Campus Center7"));
		list.add(new MeetingInstance("Campus Center8"));
		list.add(new MeetingInstance("RTH9"));
		list.add(new MeetingInstance("Campus Center10"));
		list.add(new MeetingInstance("Campus Center11"));
		list.add(new MeetingInstance("Campus Center12"));
		list.add(new MeetingInstance("Campus Center13"));
		list.add(new MeetingInstance("Campus Center14"));
		list.add(new MeetingInstance("Campus Center15"));
		list.add(new MeetingInstance("Campus Center16"));
		list.add(new MeetingInstance("Campus Center17"));
		
		
		return list;
    }
    
}