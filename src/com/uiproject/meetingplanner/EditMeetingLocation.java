package com.uiproject.meetingplanner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class EditMeetingLocation extends SelectLocation {

	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editmeetinglocation);

    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
        int lat = settings.getInt("mlat", 34019443); // TODO get current location and make that the default
        int lon = settings.getInt("mlon", -118289440);
        String addr = settings.getString("maddr", "current adress");
        init(lat, lon, addr);
        address_field.setText(addr);       
    }
    
    public void init(int lat, int lon, String addr){
    	super.init(lat, lon);

    	OverlayItem oi = new OverlayItem(new GeoPoint(lat, lon), addr, "");  
        overlay.addOverlay(oi);   
    	
    }

    public void confirm(View button){
    	onBackPressed();
    }
    
    @Override
    public void onBackPressed(){
    	saveData();
    	EditMeetingLocation.this.finish();
    	
    }
    
    private void saveData(){
    	OverlayItem oi = overlay.getOverlayItem();
    	if (oi == null){
        	return;
    	}

    	String addr = oi.getTitle();
    	int lat = oi.getPoint().getLatitudeE6();
    	int lon = oi.getPoint().getLongitudeE6();
    	
    	//save data in shared preferences
    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
    	SharedPreferences.Editor editor = settings.edit();
    	
    	editor.putString("maddr", addr);
    	editor.putInt("mlat", lat);
    	editor.putInt("mlon", lon);
    	editor.commit();    	
    }
}
