package com.uiproject.meetingplanner;

public class Meeting {

	private String meetingName;
	
	public Meeting(String name){
		this.meetingName = name;
	}
	
	public String getMeetingName(){
		return meetingName;
	}
}
