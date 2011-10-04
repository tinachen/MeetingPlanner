package com.uiproject.meetingplanner;

import com.google.android.maps.OverlayItem;

public class MyOverlayItem{
	
	private OverlayItem oi;
	private String name;
	private String picture;
	private int eta; // in sec
	
	public MyOverlayItem(OverlayItem o, String n, String p, int e){
		oi = o;
		name = n;
		picture = p;
		eta = e;
	}
	
	public String getName(){
		return name;
	}
	
	public String getPicture(){
		return picture;
	}
	
	public int getEta(){
		return eta;
	}
	
	public OverlayItem getOverlayItem(){
		return oi;
	}
	
}
