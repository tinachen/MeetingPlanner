package com.uiproject.meetingplanner;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class SelectLocation extends MapActivity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectlocation);
        
        MapView mapView = (MapView) findViewById(R.id.selectlocview);
        mapView.setBuiltInZoomControls(true);


        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.androidmarker);
        MyOverlay overlay = new MyOverlay();
        mapOverlays.add(overlay);
        
        // find the area to auto zoom to
        MapController mc = mapView.getController();
       mc.zoomToSpan(100,100);
        
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
    
    private class MyOverlay extends Overlay{	
    	private OverlayItem o;
    	    	
        public boolean onTouchEvent(MotionEvent event, MapView mapView) 
        {   
        	//---when user lifts his finger---
            if (event.getAction() == 1) {                
                GeoPoint p = mapView.getProjection().fromPixels(
                    (int) event.getX(),
                    (int) event.getY());
                    Toast.makeText(getBaseContext(), 
                        p.getLatitudeE6() / 1E6 + "," + 
                        p.getLongitudeE6() /1E6 , 
                        Toast.LENGTH_SHORT).show();
            }                            
            return false;
        }
    }
    
}
