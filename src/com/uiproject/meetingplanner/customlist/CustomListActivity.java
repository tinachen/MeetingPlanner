package com.uiproject.meetingplanner.customlist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class CustomListActivity extends Activity {
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ListView list = (ListView) findViewById(R.id.listView1);
        list.setClickable(true);

        final List<Meeting> listOfMeeting = new ArrayList<Meeting>();
        listOfMeeting.add(new Meeting("Sales meeting", "description1", "Dec 1", "10:00am", "Boss"));
        listOfMeeting.add(new Meeting("Play Poker", "description2", "Dec 1", "10:00am", "Friend"));
        listOfMeeting.add(new Meeting("Big dinner", "description3", "Dec 1", "5:00pm", "Family"));


        MeetingAdapter adapter = new MeetingAdapter(this, listOfMeeting);

        /*list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long index) {
                System.out.println("sadsfsf");
                showToast(listOfMeeting.get(position).getName());
            }
        });*/

        list.setAdapter(adapter);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}