package com.uiproject.meetingplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        GeoUpdateHandler guh = new GeoUpdateHandler();
        int lat = guh.getCurrentLat();
        int lon = guh.getCurrentLng();
        String addr = guh.getCurrentAddr();
    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
        lat = settings.getInt("mlat", lat);
        lon = settings.getInt("mlon", lon);
        addr = settings.getString("maddr", addr);
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
    	editor.remove("mnames");
    	editor.remove("mphones");
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
        }else if (resultCode == R.string.meeting_created) {
            this.setResult(R.string.meeting_created);
            this.finish();
        }else if (resultCode == R.string.logout) {
        	logout();
        }
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
           this.setResult(R.string.logout);
           this.finish();
	    }
	    
    
}
