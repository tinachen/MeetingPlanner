package com.uiproject.meetingplanner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.uiproject.meetingplanner.R;
import android.app.Activity;
import android.os.Bundle;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainPage extends Activity {
    /** Called when the activity is first created. */
 
    //public Button button1;
    public TextView textview1;
    
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
   
        // For testing purpose
        textview1 = (TextView) findViewById(R.id.textview1);
    /*    
        // Link buttons to activities
        // Server Test Btn
	    Button serverTestBtn = (Button) findViewById(R.id.test1);      
	    serverTestBtn.setOnClickListener(new Button.OnClickListener(){
	        public void onClick(View v)
	        {
	        	try {
					displayResult();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        }
	    });
	*/    
	    // Create Meeting Btn
	    Button createMeetingBtn = (Button) findViewById(R.id.createMeeting);
	    createMeetingBtn.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View view) {

	    		startActivity(new Intent(MainPage.this, CreateMeetingWhat.class));
	    		Intent intent = new Intent(MainPage.this, CommunicateService.class);
	    		startService(intent);

	    }});
    
	    // View Meeting List Btn
	    Button viewMeetingBtn = (Button) findViewById(R.id.meetingList);
	    viewMeetingBtn.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View view) {
	    		startActivity(new Intent(MainPage.this, MeetingList.class));
	    }});
    
    }

    
    

}

