package com.uiproject.meetingplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class CreateMeetingWhere extends SelectLocation implements OnEditorActionListener {

	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	public static final String TAG = "CreateMeetingWhere";
	
	private LocationManager locationManager;
	private String provider;
	private EditText searchBar;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createmeetingwhere);

        searchBar = (EditText) findViewById(R.id.address_field);
        searchBar.setOnEditorActionListener(this);
        
        // Get Map View
        MapView mv = (MapView)findViewById(R.id.selectlocview);
        MapController mc = mv.getController();
        
        /*GeoUpdateHandler guh = new GeoUpdateHandler(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, guh);
		// Define the criteria how to select the locatioin provider -> use
		// default
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);
		
		
		if(location != null){
			Log.d(TAG, "onCreate: Provider " + provider + " has been selected.");
			Log.d(TAG, "onCreate: Lat = " + location.getLatitude() + ", Lon = " + location.getLongitude());
		}*/
		
        int lat = MainPage.guh.getCurrentLat();
        int lon = MainPage.guh.getCurrentLng();
        String addr = MainPage.guh.getCurrentAddr();
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

    	clearData();
    	CreateMeetingWhere.this.setResult(R.string.cancel_create);
    	CreateMeetingWhere.this.finish();
    }
    
    private void clearData(){

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
    	editor.remove("mattendeeids");
    	editor.commit();
    	
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
	            	clearData();
	            	Logout.logout(this);
	            	break;
	            }
	        }
	        return true;
	    }

	    public boolean onEditorAction(TextView v, int actionId,
	            KeyEvent event) {
	        if (event != null&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
	            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	            in.hideSoftInputFromWindow(v
	                    .getApplicationWindowToken(),
	                    InputMethodManager.HIDE_NOT_ALWAYS);
	        }
	        return false;
	    }
    
}
