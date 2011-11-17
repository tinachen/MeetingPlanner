package com.uiproject.meetingplanner;

public class Tracker {
	String name;
	String eta;
	
	public Tracker(String n, String e) {
		name = n;
		eta = e;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public String getEta() {
		return eta;
	}
	
	public void setEta(String e) {
		eta = e;
	}
}
