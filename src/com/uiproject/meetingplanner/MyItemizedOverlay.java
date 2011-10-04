package com.uiproject.meetingplanner;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MyItemizedOverlay extends ItemizedOverlay {
	private ArrayList<MyOverlayItem> mOverlays = new ArrayList<MyOverlayItem>();
	private GeoPoint center;
	private Context mContext;
	private MapView mapview;
    
	public MyItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}
	
	public MyItemizedOverlay(Drawable defaultMarker, Context context, MapView v) {
	  super(boundCenterBottom(defaultMarker));
	  mContext = context;
	  center = new GeoPoint(0, 0);
	  mapview = v;
	}

	@Override
	protected OverlayItem createItem(int i) {
	  return mOverlays.get(i).getOverlayItem();
	}

	@Override
	public int size() {
		return mOverlays.size();
	}
	
	@Override
	protected boolean onTap(int index) {
	  MyOverlayItem item = mOverlays.get(index);
	  /*
	  GeoPoint geo = item.getOverlayItem().getPoint();
	  
	  Projection projection = mapview.getProjection();
	  Point p = projection.toPixels(geo, null);
	  
	  PopupWindow popup = new PopupWindow(mContext);
	  
	  
	  popup.showAtLocation(mapview, Gravity.BOTTOM, p.x, p.y);
	  */
	  
	  //create new dialog and set the styles
	  Dialog dialog = new Dialog(mContext, R.style.AttendeeDialogTheme);
	  dialog.setContentView(R.layout.attendeedialog);
	  
	  
	  //set info accordingly
	  ImageView pic = (ImageView) dialog.findViewById(R.id.pic);
	  pic.setImageResource(R.drawable.androidmarker);
	  TextView name = (TextView) dialog.findViewById(R.id.name);
	  name.setText(item.getName());
	  TextView eta = (TextView) dialog.findViewById(R.id.eta);
	  eta.setText((item.getEta() / 60) + "min, " + (item.getEta() % 60) + " sec");
	  
	  //show the dialog with person's info
	  dialog.show();
	  
	  
	  return true;
	}
	
	public void addOverlay(MyOverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	    
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
	
	public GeoPoint getCenter(){
		return center;
	}
	
}
