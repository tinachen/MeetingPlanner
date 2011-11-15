package com.uiproject.meetingplanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class TrackerMap extends MapActivity {

	private MyItemizedOverlay itemizedoverlay;
	private int mid;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackermap);
        
        mid = getIntent().getIntExtra("mid", -1);
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.androidmarker);
        itemizedoverlay = new MyItemizedOverlay(drawable, this);

        mapOverlays.add(itemizedoverlay);
        
        // find the area to auto zoom to
        MapController mc = mapView.getController();
        mc.zoomToSpan(itemizedoverlay.getLatSpanE6(), itemizedoverlay.getLonSpanE6());
        
        // set the center
        mc.setCenter(itemizedoverlay.getCenter());
        
        Intent intent = new Intent(TrackerMap.this, CommunicateService.class);
        intent.putExtra("mid", mid);
        startService(intent);
        
        
    }
    
    @Override
    public boolean isRouteDisplayed() {
        return false;
    }

    public void updateMap(Map<String,Object> map){
    	itemizedoverlay.clear();
    	Map<Integer, UserInstance> attendees = (Map<Integer, UserInstance>) map.get("locations");
    	MyOverlayItem myoi;
    	OverlayItem oi;
    	Set<Integer> keys = attendees.keySet();
    	UserInstance a;
    	for (Integer i : keys){
    		a = attendees.get(i);
    		oi = new OverlayItem(new GeoPoint(a.getUserLocationLat(), a.getUserLocationLon()), "", "");
    		myoi = new MyOverlayItem(oi, a.getUserFirstName() + " " + a.getUserLastName(), a.getUserEta());
    		itemizedoverlay.addOverlay(myoi);
    	}
    	
    	itemizedoverlay.doPopulate();
    	
    }
    
    public void toList(View button){  	

		Intent intent = new Intent(TrackerMap.this, TrackerEtaList.class);
		TrackerMap.this.startActivityForResult(intent, 0);
    }
    
    private class MyItemizedOverlay extends ItemizedOverlay<OverlayItem> {
    	private ArrayList<MyOverlayItem> mOverlays = new ArrayList<MyOverlayItem>();
    	private GeoPoint center;
    	private Context mContext;
        
    	public MyItemizedOverlay(Drawable defaultMarker) {
    		super(boundCenterBottom(defaultMarker));
    	}
    	
    	public MyItemizedOverlay(Drawable defaultMarker, Context context) {
    	  super(boundCenterBottom(defaultMarker));
    	  mContext = context;
    	  center = new GeoPoint(0, 0);
    	}

    	@Override
    	protected OverlayItem createItem(int i) {
    	  return mOverlays.get(i).getOverlayItem();
    	}

    	@Override
    	public int size() {
    		return mOverlays.size();
    	}
    	
    	public void clear(){
    		mOverlays.clear();
    		center = new GeoPoint(0, 0);
    	}
    	
    	@Override
    	protected boolean onTap(int index) {
    	  //MyOverlayItem item = mOverlays.get(index);
    	  /*
    	  GeoPoint geo = item.getOverlayItem().getPoint();
    	  
    	  Projection projection = mapview.getProjection();
    	  Point p = projection.toPixels(geo, null);
    	  
    	  PopupWindow popup = new PopupWindow(mContext);
    	  
    	  
    	  popup.showAtLocation(mapview, Gravity.BOTTOM, p.x, p.y);
    	  
    	  
    	  //create new dialog and set the styles
    	  Dialog dialog = new Dialog(mContext, R.style.AttendeeDialogTheme);
    	  dialog.setContentView(R.layout.attendeedialog);
    	  
    	  
    	  //set info accordingly
    	  ImageView pic = (ImageView) dialog.findViewById(R.id.pic);
    	  pic.setImageResource(R.drawable.androidmarker);
    	  TextView name = (TextView) dialog.findViewById(R.id.name);
    	  //name.setText(item.getName());
    	  TextView eta = (TextView) dialog.findViewById(R.id.eta);
    	  //eta.setText((item.getEta() / 60) + "min, " + (item.getEta() % 60) + " sec");
    	  
    	  //show the dialog with person's info
    	  dialog.show();
    	  
    	  */
    	  return true;
    	}
    	
    	public void addOverlay(MyOverlayItem overlay) {
    	    mOverlays.add(overlay);
    	    //calculate the new center point
    	    GeoPoint p = overlay.getOverlayItem().getPoint();
    	    int size = mOverlays.size();
    	    int lat = p.getLatitudeE6();
    	    int lon = p.getLongitudeE6() ;
    	    int cLat = center.getLatitudeE6() * (size - 1);
    	    int cLon = center.getLongitudeE6() * (size - 1);
    	    int newLat =  (lat + cLat) / size;
    	    int newLon =  (lon + cLon) / size;
    	    GeoPoint c = new GeoPoint(newLat, newLon);
    	    
    	    //set the new center point
    	    center = c;
    	    
    	}
    	
    	public void doPopulate(){
    		populate();
    	}
    	
    	public GeoPoint getCenter(){
    		return center;
    	}
    	
    }
    
    private class MyOverlayItem{
    	
    	private OverlayItem oi;
    	private String name;
    	private String eta; // in sec
    	
    	public MyOverlayItem(OverlayItem o, String n, String e){
    		oi = o;
    		name = n;
    		eta = e;
    	}
    	
    	public String getName(){
    		return name;
    	}
    	    	
    	public String getEta(){
    		return eta;
    	}
    	
    	public OverlayItem getOverlayItem(){
    		return oi;
    	}
    }


}
