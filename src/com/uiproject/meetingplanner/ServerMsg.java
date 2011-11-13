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
	
	public ServerMsg(int u,int lat, int lon){
		this.myLat = lat;
		this.myLong = lon;
		this.userID = u;
	}
	
	public ServerMsg(int lat, int lon) {
		this.myLat = lat;
		this.myLong = lon;
	}
	
	
	//adding getters and setters
	public void setLat(int myLat)
	{
		this.myLat = myLat;
	}
	
	public void setLong(int myLong)
	{
		this.myLong = myLong;
	}
	
	public void setMeetingID(int meetingID)
	{
		this.meetingID = meetingID;
	}
	
	public void setuserID(int userID)
	{
		this.userID = userID;
	}
	
	public void setEta(String eta)
	{
		this.eta = eta;
	}
	
	public void setPicture(String picture)
	{
		this.picture = picture;
	}
	
	public int getLat()
	{
		return this.myLat;
	}
	
	public int getLong()
	{
		return this.myLong;
	}
	
	public int getMeetingID()
	{
		return this.meetingID;
	}
	
	public int getUserID()
	{
		return this.userID;
	}
	
	public String getEta()
	{
		return this.eta;
	}
	
	public String getPicture()
	{
		return this.picture;
	}
	
}
