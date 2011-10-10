package com.uiproject.meetingplanner;

public class ServerMsg {
	public int myLat;
	public int myLong;
	public int meetingID;
	public int userID;
	public String eta;
	public String picture;
	
	public ServerMsg(int lat, int lon, int m, int u, String e){
		myLat = lat;
		myLong = lon;
		meetingID = m;
		userID = u;
		eta = e;
		picture = null;
		
	}
	
}
