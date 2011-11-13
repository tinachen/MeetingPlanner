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
	
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        //setContentView(R.layout.meetingitems);
	        // Hook up with database
		    db = new MeetingPlannerDatabaseManager(this, 2);
		    db.open();
		    db.createMeeting(4,"CS588 Progress", 32, -35, "Happy Hour Drinks", "RTCC 202", "10/31/2011", "6:30pm", "9:00pm", 5, 5);
		    allMeet = db.getAllMeetings();
		    
	        /*setListAdapter(new ArrayAdapter<String>(this, R.layout.meetinglist, COUNTRIES));
	        ListView lv = getListView();
	        //lv.setTextFilterEnabled(true);

	        lv.setOnItemClickListener(new OnItemClickListener() {
	          public void onItemClick(AdapterView<?> parent, View view,
	              int position, long id) {
	            // When clicked, show a toast with the TextView text
	            Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
	                Toast.LENGTH_SHORT).show();
	          }
	        });
			
			*/
	        ArrayAdapter<MeetingInstance> adapter = new MeetingListArrayAdapter(this, allMeet);
	        setListAdapter(adapter);
	        

	        //ListView list = (ListView) findViewById(R.id.listView1);
	        //list.setClickable(true);

	        //final ArrayAdapter<MeetingInstance> listOfMeeting = new MeetingListArrayAdapter(this,allMeet);

	        /*list.setOnItemClickListener(new OnItemClickListener() {

	            @Override
	            public void onItemClick(AdapterView<?> arg0, View view, int position, long index) {
	                System.out.println("sadsfsf");
	                showToast(listOfMeeting.get(position).getName());
	            }
	        });*/

	        //list.setAdapter(listOfMeeting);

	        
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