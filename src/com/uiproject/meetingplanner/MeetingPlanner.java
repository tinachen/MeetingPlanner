package com.uiproject.meetingplanner;    

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MeetingPlanner extends MapActivity {
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
        
        //find the area to auto zoom to
        MapController mc = mapView.getController();
        mc.zoomToSpan(itemizedoverlay.getLatSpanE6(), itemizedoverlay.getLonSpanE6());
        
        //set the center
        mc.setCenter(itemizedoverlay.getCenter());
    }
    
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}