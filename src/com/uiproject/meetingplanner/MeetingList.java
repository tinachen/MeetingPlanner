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

        ArrayAdapter<MeetingInstance> adapter = new MeetingListArrayAdapter(this, getMeeting());
		setListAdapter(adapter);
        
    }
    
    private List<MeetingInstance> getMeeting(){
    	List<MeetingInstance> list = new ArrayList<MeetingInstance>();
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