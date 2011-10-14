package com.uiproject.meetingplanner;

import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class SelectLocation extends MapActivity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectlocation);
        
        MapView mapView = (MapView) findViewById(R.id.selectlocview);
        mapView.setBuiltInZoomControls(true);
        
        // find the area to auto zoom to
        MapController mc = mapView.getController();
       mc.zoomToSpan(100,100);
        
        // set the center
        mc.setCenter(new GeoPoint(34019443,-118289440));
        
    }
    
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    
}
