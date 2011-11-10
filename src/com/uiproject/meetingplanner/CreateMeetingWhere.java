package com.uiproject.meetingplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class CreateMeetingWhere extends SelectLocation {

	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createmeetingwhere);

    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
        int lat = settings.getInt("mlat", 34019443); // TODO get current location and make that the default
        int lon = settings.getInt("mlon", -118289440);
        String addr = settings.getString("maddr", "current adress");
        init(lat, lon, addr);
        
    }
    
    public void init(int lat, int lon, String addr){
    	super.init(lat, lon);

    	OverlayItem oi = new OverlayItem(new GeoPoint(lat, lon), addr, "");  
        overlay.addOverlay(oi);   
    	
    }

    public void back(View button){
    	onBackPressed();
    }

    public void cancel(View button){

    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
    	SharedPreferences.Editor editor = settings.edit();
    	editor.remove("mtitle");
    	editor.remove("mdesc");
    	editor.remove("mdatem");
    	editor.remove("mdated");
    	editor.remove("mdatey");
    	editor.remove("mstarth");
    	editor.remove("mstartm");
    	editor.remove("mendh");
    	editor.remove("mendm");
    	editor.remove("mtracktime");
    	editor.remove("maddr");
    	editor.remove("mlat");
    	editor.remove("mlon");
    	
    	//remove people
    	editor.commit();

    	
    	CreateMeetingWhere.this.setResult(R.string.cancel_create);
    	CreateMeetingWhere.this.finish();
    }
    
    public void next(View button){
    	OverlayItem oi = overlay.getOverlayItem();
    	if (oi == null){
        	Toast.makeText(getBaseContext(), "Please select a location", Toast.LENGTH_SHORT).show();
        	return;
    	}
    	saveData();
    	
		Intent intent = new Intent(CreateMeetingWhere.this, CreateMeetingConfirm.class);
		CreateMeetingWhere.this.startActivityForResult(intent, 0);
    	
    }

    @Override
    public void onBackPressed(){
    	Toast.makeText(getBaseContext(), "hereX", Toast.LENGTH_SHORT).show();
    	saveData();
    	CreateMeetingWhere.this.finish();
    	
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
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == R.string.cancel_create) {
            this.setResult(R.string.cancel_create);
            this.finish();
        }
    }
    
}
