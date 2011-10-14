package com.uiproject.meetingplanner;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class MeetingList extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
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
        });*/

        ArrayAdapter<Meeting> adapter = new MeetingListArrayAdapter(this, getMeeting());
		setListAdapter(adapter);
        
    }
    
    private List<Meeting> getMeeting(){
    	List<Meeting> list = new ArrayList<Meeting>();
		list.add(new Meeting("RTH"));
		list.add(new Meeting("Campus Center"));
		list.add(new Meeting("Campus Center2"));
		list.add(new Meeting("Campus Center3"));
		list.add(new Meeting("Campus Center4"));
		list.add(new Meeting("Campus Center5"));
		list.add(new Meeting("RTH6"));
		list.add(new Meeting("Campus Center7"));
		list.add(new Meeting("Campus Center8"));
		list.add(new Meeting("RTH9"));
		list.add(new Meeting("Campus Center10"));
		list.add(new Meeting("Campus Center11"));
		list.add(new Meeting("Campus Center12"));
		list.add(new Meeting("Campus Center13"));
		list.add(new Meeting("Campus Center14"));
		list.add(new Meeting("Campus Center15"));
		list.add(new Meeting("Campus Center16"));
		list.add(new Meeting("Campus Center17"));
		
		return list;
    }
}