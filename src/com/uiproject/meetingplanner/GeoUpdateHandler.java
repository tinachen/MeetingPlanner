package com.uiproject.meetingplanner;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class GeoUpdateHandler implements LocationListener {

	private int currentLat;	// current latitude
	private int currentLng;	// current longitude
	
	public GeoUpdateHandler(){
		currentLat = 0;	
		currentLng = 0;
	}
	
	@Override
	public void onLocationChanged(Location location) {
		currentLat = (int) (location.getLatitude() * 1E6);
		currentLng = (int) (location.getLongitude() * 1E6);
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
	
	/**
	 * @author Tina Chen
	 * @return current latitude
	 */
	public int getCurrentLat(){
		return currentLat;
	}
	
	/**
	 * @author Tina Chen
	 * @return current longitude
	 */
	public int getCurrentLng(){
		return currentLng;
	}

}