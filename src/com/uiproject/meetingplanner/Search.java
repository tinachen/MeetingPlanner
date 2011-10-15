package com.uiproject.meetingplanner;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class Search extends Activity {
    /** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.search);

	    AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_names);
	    String[] search_contacts = getResources().getStringArray(R.array.search_contacts);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, search_contacts);
	    textView.setAdapter(adapter);
	}
}