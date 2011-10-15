package com.uiproject.meetingplanner;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class Search extends Activity {
	static final String[] NAMES = new String[] {
		  "Mengfei Xu", "Cauchy Choi", "Elizabeth Deng", "Tina Chen", "Yuwen Bian", "Laura Rodriguez"
		};
    /** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.search);

	    AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_names);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, NAMES);
	    textView.setAdapter(adapter);
	}
}