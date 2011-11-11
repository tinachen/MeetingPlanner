package com.uiproject.meetingplanner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class TrackerEtaList extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackeretalist);
        
    }
    public void toMap(View button){  	
    	TrackerEtaList.this.finish();
    }
    
}
