package com.uiproject.meetingplanner.customlist;

public class Meeting {
	String title;
	String description;
	String Date;
	String Time;
	String creator;
	public Meeting(String title, String description, String date, String time, String creator) {
		super();
		this.title = title;
		this.description = description;
		Date = date;
		Time = time;
		this.creator = creator;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
}
