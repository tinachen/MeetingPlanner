package com.uiproject.meetingplanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class SelectLocation extends MapActivity {
	protected MapController mc;
	protected MapView mapView;
	protected EditText address_field;
	protected MyOverlay overlay;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
	public void init(int lat, int lon){

    	address_field = (EditText) findViewById(R.id.address_field);
        mapView = (MapView) findViewById(R.id.selectlocview);
        mapView.setBuiltInZoomControls(true);

        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.androidmarker);
        overlay = new MyOverlay(drawable);
        mapOverlays.add(overlay);
        
        // find the area to auto zoom to
        mc = mapView.getController();
   
        // set the center
        mc.setCenter(new GeoPoint(lat,lon));
        
	}
	
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    
    public void findAddress(View button){
    	
    	String addy = address_field.getText().toString();
    	try{
	    	Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
			List<Address> addresses = geoCoder.getFromLocationName(addy, 1);
	
		    if(addresses.size() > 0)
		    {
				GeoPoint p = new GeoPoint( (int) (addresses.get(0).getLatitude() * 1E6), 
				                  (int) (addresses.get(0).getLongitude() * 1E6));
				
				mc.animateTo(p);
				mc.setZoom(12);
				
				OverlayItem oi = new OverlayItem(p, addresses.get(0).toString(), "");
				overlay.addOverlay(oi);
				
				mapView.invalidate();
				address_field.setText("");
		    }else{
	        	Toast.makeText(getBaseContext(), "Cannot find " + addy, Toast.LENGTH_SHORT).show();		    	
		    }
	    }catch(Exception e){
	    	e.printStackTrace();
        	Toast.makeText(getBaseContext(), "Cannot find " + addy, Toast.LENGTH_SHORT).show();
	    }
	    
	}
    
    protected class MyOverlay extends ItemizedOverlay<OverlayItem>{	
    	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
        
        
    	public MyOverlay(Drawable defaultMarker) {
    		super(boundCenterBottom(defaultMarker));
    	}
    	
    	@Override
    	protected OverlayItem createItem(int i) {
    	  return mOverlays.get(i);
    	}
    	
    	@Override
    	public int size() {
    		return mOverlays.size();
    	}
    	

    	public void addOverlay(OverlayItem overlay) {
    		mOverlays.clear();
    	    mOverlays.add(overlay);
    	    populate();
    	}
    	
    	public OverlayItem getOverlayItem(){
    		if (mOverlays.size() == 0){
    			return null;
    		}
    		return mOverlays.get(0);
    	}
    	
    	@Override
    	public boolean onTap(GeoPoint p, MapView mapView){
    		OverlayItem oi = new OverlayItem(p, "title", "something");
            addOverlay(oi);
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
                
                 oi = new OverlayItem(p, add, "");
                addOverlay(oi);
                
                Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT).show();
                
            }
            catch (IOException e) {  
            	Toast.makeText(getBaseContext(), "no addy found", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }   
            return true;
    	}
    }
    //overlay class ends here
    
}
