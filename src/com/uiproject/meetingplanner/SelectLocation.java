package com.uiproject.meetingplanner;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class SelectLocation extends MapActivity {
	MapController mc;
	MapView mapView;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectlocation);
        
        mapView = (MapView) findViewById(R.id.selectlocview);
        mapView.setBuiltInZoomControls(true);


        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.androidmarker);
        MyOverlay overlay = new MyOverlay();
        mapOverlays.add(overlay);
        
        // find the area to auto zoom to
        mc = mapView.getController();
        
       Bundle bundle = this.getIntent().getExtras();
       int myLat = bundle.getInt("lat");
       int myLon = bundle.getInt("lon");
       
        // set the center
        mc.setCenter(new GeoPoint(myLat,myLon));
        
    }
    
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    
    public void findAddress(View button){    
    	EditText edittext = (EditText) findViewById(R.id.address_field);
    	String addy = edittext.getText().toString();
    	try{
	    	Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
			List<Address> addresses = geoCoder.getFromLocationName(addy, 5);
	
		    if(addresses.size() > 0)
		    {
				GeoPoint p = new GeoPoint( (int) (addresses.get(0).getLatitude() * 1E6), 
				                  (int) (addresses.get(0).getLongitude() * 1E6));
				
				mc.animateTo(p);
				mc.setZoom(12);
				
				MyOverlay mapOverlay = new MyOverlay();
				List<Overlay> listOfOverlays = mapView.getOverlays();
				listOfOverlays.clear();
				listOfOverlays.add(mapOverlay);
				
				mapView.invalidate();
				edittext.setText("");
		    }else{
	        	Toast.makeText(getBaseContext(), "0 addresses", Toast.LENGTH_SHORT).show();
		    	
		    }
	    }catch(Exception e){
	    	e.printStackTrace();
        	Toast.makeText(getBaseContext(), "cannot find " + addy, Toast.LENGTH_SHORT).show();
	    }
	}
    
    private class MyOverlay extends Overlay{	
    	public OverlayItem o;
    	  
    	/*
    	public boolean onTouchEvent(MotionEvent event, MapView mapView) 
        {   
            //---when user lifts his finger---
            if (event.getAction() == 1) {                
                GeoPoint p = mapView.getProjection().fromPixels(
                    (int) event.getX(),
                    (int) event.getY());
 
                
                try {

                	Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.ENGLISH);
                    List<Address> addresses = geoCoder.getFromLocation(
                        p.getLatitudeE6()  / 1E6, 
                        p.getLongitudeE6() / 1E6, 1);
 
                    String add = "";
                    if (addresses.size() > 0) 
                    {
                        for (int i=0; i<addresses.get(0).getMaxAddressLineIndex(); 
                             i++)
                           add += addresses.get(0).getAddressLine(i) + "\n";
                    }
 
                    Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT).show();
                    
                }
                catch (IOException e) {  
                	Toast.makeText(getBaseContext(), "no addy found", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }   
                return true;
            }
            else                
                return false;
        }
    	*/
    }
    //overlay class ends here
    
}
