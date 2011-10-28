package com.uiproject.meetingplanner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.util.Log;

public class Search extends Activity implements OnItemClickListener {
	private final String LOG_TAG = "Search";
	
    /** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.search);

	    AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_names);
	    String[] search_contacts = getResources().getStringArray(R.array.search_contacts);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, search_contacts);
	    textView.setAdapter(adapter);
	    textView.setOnItemClickListener(this);
	    /*textView.setOnItemClickListener(new OnItemClickListenter() {
	    	public void onItemClick 
	    	
	    })*/
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(LOG_TAG, "onItemSelected() " + parent.getItemAtPosition(position));
    }
}