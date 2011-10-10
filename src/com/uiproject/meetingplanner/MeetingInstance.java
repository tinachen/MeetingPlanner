package com.uiproject.meetingplanner;

import java.util.Date;

public class MeetingInstance {
	private int meetingID;
	private int meetingLat;
	private int meetingLong;
	private String meetingSubject;
	private Date meetingDate;
	private Object[] attendees;
	
	public MeetingInstance(int meetingID, int meetingLat, int meetingLong, String meetingSubject, Date meetingDate, Object[] attendees){
		this.meetingID = meetingID;
		this.meetingLat = meetingLat;
		this.meetingLong = meetingLong;
		this.meetingSubject = meetingSubject;
		this.meetingDate = meetingDate;
		this.attendees = attendees;
		
	}
	
	//adding getters and setters
	public void setMeetingID(int meetingID)
	{
		this.meetingID = meetingID;
	}
	
	public void setMeetingLat(int meetingLat)
	{
		this.meetingLat = meetingLat;
	}
	
	public void setMeetingLong(int meetingLong)
	{
		this.meetingLong = meetingLong;
	}
	
	public void setMeetingSubject(String meetingSubject)
	{
		this.meetingSubject = meetingSubject;
	}
	
	public void setMeetingDate(Date meetingDate)
	{
		this.meetingDate = meetingDate;
	}
	
	public void setAttendees(Object[] attendees)
	{
		this.attendees = attendees;
	}
	
	public int getMeetingLat()
	{
		return this.meetingLat;
	}
	
	public int getMeetingLong()
	{
		return this.meetingLong;
	}
	
	public int getMeetingID()
	{
		return this.meetingID;
	}
	
	public String getMeetingSubject()
	{
		return this.meetingSubject;
	}
	
	public Date getMeetingDate()
	{
		return this.meetingDate;
	}
	
	public Object[] getAttendees()
	{
		return this.attendees;
	}
}
