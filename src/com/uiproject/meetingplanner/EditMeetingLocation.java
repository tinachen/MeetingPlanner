package com.uiproject.meetingplanner;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        GeoUpdateHandler guh = new GeoUpdateHandler();
        int lat = guh.getCurrentLat();
        int lon = guh.getCurrentLng();
        String addr = guh.getCurrentAddr();
    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
        lat = settings.getInt("mlat", lat);
        lon = settings.getInt("mlon", lon);
        addr = settings.getString("maddr", addr);
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
    	OverlayItem oi = overlay.getOverlayItem();
    	if (oi == null){
        	return;
    	}

    	String addr = oi.getTitle();
    	int lat = oi.getPoint().getLatitudeE6();
    	int lon = oi.getPoint().getLongitudeE6();
    	
    	Intent resultIntent = new Intent();
    	resultIntent.putExtra("lat", lat);
    	resultIntent.putExtra("lon", lon);
    	resultIntent.putExtra("addr", addr);
    	setResult(Activity.RESULT_OK, resultIntent);
    	
    	EditMeetingLocation.this.finish();
    	
    }
    // menu 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logoutonly, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:{
            	logout();
            	break;
            }
        }
        return true;
    }
    
    private void logout(){
    	Logout.logout(this);
    }
}
