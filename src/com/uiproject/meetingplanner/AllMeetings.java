package com.uiproject.meetingplanner;

import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseHelper;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView
;import android.widget.Toast;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;

public class AllMeetings extends TabActivity{

	TabHost tabHost;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.tabs);
	    
	    /*Resources res = getResources(); // Resource object to get Drawables
	    tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, MeetingListPending.class);

	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("pending").setIndicator("Pending", res.getDrawable(R.drawable.ic_menu_flash))
        			  .setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, MeetingListAccepted.class);
	    spec = tabHost.newTabSpec("accepted").setIndicator("Accepted", res.getDrawable(R.drawable.ic_menu_tick))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, CustomListActivity.class);
	    spec = tabHost.newTabSpec("declined").setIndicator("Declined", res.getDrawable(R.drawable.ic_menu_stop))
        			  .setContent(intent);
	    tabHost.addTab(spec);
	    tabHost.setCurrentTab(1);*/
	    
	    
	    tabHost = (TabHost) findViewById(android.R.id.tabhost);
	    tabHost.setup();
	    //tabHost.getTabWidget().setDividerDrawable(R.drawable.tab_divider);
	    setupTab(new TextView(this), "Pending");
		setupTab(new TextView(this), "Accepted");
		setupTab(new TextView(this), "Declined");
		tabHost.setCurrentTab(1);
	}
	
    // menu 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.meetinglistmenu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
	        case R.id.logout:{
	        	Logout.logout(this);
	        	break;
	        }
	        case R.id.help:{
				Intent intent = new Intent(AllMeetings.this, HelpPage.class);
				startActivity(intent);
	        	break;
	        }
	        case R.id.oldmeeting:{

				Intent intent = new Intent(AllMeetings.this, MeetingListOld.class);
				startActivity(intent);
	        	break;
	        }
        }
        return true;
    }
    
    private void setupTab(final View view, final String tag) {
    	Intent intent;
    	if (tag.compareTo("Accepted") == 0){
    		intent = new Intent().setClass(this, MeetingListAcceptedTest.class);
    	}else if(tag.compareTo("Declined") == 0){
    		intent = new Intent().setClass(this, MeetingListDeclinedTest.class);
    	}else{
    		intent = new Intent().setClass(this, MeetingListPendingTest.class);
    	}
    		
    	
    	View tabview = createTabView(tabHost.getContext(), tag);
    		TabSpec setContent = tabHost.newTabSpec(tag).setIndicator(tabview).setContent(intent);
        tabHost.addTab(setContent);
    }
    
    private static View createTabView(final Context context, final String text) {
    	View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
    	TextView tv = (TextView) view.findViewById(R.id.tabsText);
    	tv.setText(text);
    	return view;
    }
}
