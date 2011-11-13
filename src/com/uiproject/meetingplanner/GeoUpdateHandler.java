package com.uiproject.meetingplanner;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

public class GeoUpdateHandler implements LocationListener {

	private int currentLat = 0;	// current latitude
	private int currentLng = 0;	// current longitude
	private String currentAddr = "";
	private Context context;
	
	public GeoUpdateHandler(){
		currentLat = 0;	
		currentLng = 0;
	}

	public GeoUpdateHandler(Context c){
		currentLat = 0;	
		currentLng = 0;
		context = c;
	}
	
	public void onLocationChanged(Location location) {
		currentLat = (int) (location.getLatitude() * 1E6);
		currentLng = (int) (location.getLongitude() * 1E6);
		if (context != null){
			try {
	
	        	Geocoder geoCoder = new Geocoder(context, Locale.ENGLISH);
	            List<Address> addresses = geoCoder.getFromLocation(currentLat, currentLng, 1);
	
	            String add = "";
	            if (addresses.size() > 0) 
	            {
	                for (int i=0; i<addresses.get(0).getMaxAddressLineIndex(); 
	                     i++)
	                   add += addresses.get(0).getAddressLine(i) + "\n";
	            }
	            currentAddr = add;
	        }
	        catch (IOException e) {  
	        	Toast.makeText(context, "Cannot find current location", Toast.LENGTH_SHORT).show();
	            e.printStackTrace();
	        }   
		}
	}

	public void onProviderDisabled(String provider) {
	}

	public void onProviderEnabled(String provider) {
	}

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
	
	public String getCurrentAddr(){
		return currentAddr;
	}

	
}