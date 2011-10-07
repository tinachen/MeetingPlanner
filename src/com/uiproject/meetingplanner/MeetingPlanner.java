package com.uiproject.meetingplanner;    

import java.io.DataInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MeetingPlanner extends MapActivity {
	
	private LocationManager locationManager;	// for detecting current location
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.androidmarker);
        MyItemizedOverlay itemizedoverlay = new MyItemizedOverlay(drawable, this, mapView);

        //hard-coding in some sample points
        GeoPoint point = new GeoPoint(34019443,-118289440);
        OverlayItem overlayitem = new OverlayItem(point, "USC", "SAL"); 
        MyOverlayItem myOi = new MyOverlayItem(overlayitem, "Person 1", "picture1", 500);
        itemizedoverlay.addOverlay(myOi);

        GeoPoint point2 = new GeoPoint(34020105, -118289821);
        OverlayItem overlayitem2 = new OverlayItem(point2, "USC", "RTH");   
        MyOverlayItem myOi2 = new MyOverlayItem(overlayitem2, "Person 2", "picture2", 1537);
        itemizedoverlay.addOverlay(myOi2);

        GeoPoint point3 = new GeoPoint(34042863, -118267006);
        OverlayItem overlayitem3 = new OverlayItem(point3, "Downtown LA", "Staple Center");   
        MyOverlayItem myOi3 = new MyOverlayItem(overlayitem3, "Person 3", "picture3", 7448);
        itemizedoverlay.addOverlay(myOi3);

        GeoPoint point4 = new GeoPoint(34150089, -118269152);
        OverlayItem overlayitem4 = new OverlayItem(point4, "Glendale", "edeng");   
        MyOverlayItem myOi4 = new MyOverlayItem(overlayitem4, "Person 4", "picture4", 10937);
        itemizedoverlay.addOverlay(myOi4);
        
        // add the points to the map
        mapOverlays.add(itemizedoverlay);
        
        // find the area to auto zoom to
        MapController mc = mapView.getController();
        mc.zoomToSpan(itemizedoverlay.getLatSpanE6(), itemizedoverlay.getLonSpanE6());
        
        // set the center
        mc.setCenter(itemizedoverlay.getCenter());
        
        // used for detecting current position
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new GeoUpdateHandler());
    }
    
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
   

    private static final String TAG = "MeetingTracker";
    
    Object origin;
	Object destination;
	String mode;
	String language;
	boolean sensor;{
    
    this.origin = "Chicago+IL";
    this.destination = "Madison+WI";
    this.mode="driving";
    this.language="en=US";
    this.sensor=false;}
	
    protected String getEta(Object origin, Object destination, String mode)
    {
    	this.origin = origin;
    	this.destination = destination;
    	this.mode = mode;
    	String myURL = "http://maps.googleapis.com/maps/api/distancematrix/xml?origins="+origin+"&destinations="+destination+"&mode="+mode+"&language="+language+"&sensor="+sensor+"";
    	String readableValue = "";
    	try
        {
    		URL                url;
    		URLConnection      urlConn;
    		DataInputStream    dis;

    		//My Directions
    		url = new URL(myURL);

    		urlConn = url.openConnection();
    		urlConn.setDoInput(true);
    		urlConn.setUseCaches(false);

    		dis = new DataInputStream(urlConn.getInputStream());
    		String line;
    		String value;
    		boolean done;
    		done = false;
    		
    		while (((line = dis.readLine()) != null) && (done == false))
    		{
    			line = line.toString();
    			line = line.trim();
    			if (line.equals("<duration>"))
    			{
    				Log.v(TAG, "Here I am!");
    				value = dis.readLine();
    				value = value.trim();
    				readableValue = dis.readLine();
    				readableValue = readableValue.trim();
    				if (value.startsWith("<value>"))
    				{
    					value = value.replaceAll("<value>", "");
    					value = value.replaceAll("</value>", "");
    				}
    				if (readableValue.startsWith("<text>"))
    				{
    					readableValue = readableValue.replaceAll("<text>", "");
    					readableValue = readableValue.replaceAll("</text>", "");
    					//Log.v(TAG, "The final value is: " + readableValue);
    					done = true;
    				}
    			}
    		}
            dis.close();
        }

    	catch (MalformedURLException mue) 
    	{
    		Log.e(TAG, "Sorry! Please double check the values passed: " + mue);
    	}
        catch (IOException ioe) 
        {
        	Log.e(TAG, "Sorry! Could not read values properly: " + ioe);
        }
        
    	if (readableValue != "")
    	{
    		return readableValue;
    	}
    	else
    	{
    		return "Oh no! We could not find the ETA. Please try again."; 
    	}
    }
}

