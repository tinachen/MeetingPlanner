/********************
 * 
 * Last Modified by Tina Chen
 * 
 *******************/

package com.uiproject.meetingplanner;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MeetingInstance {
	private int meetingID;
	private int meetingLat;
	private int meetingLon;
	private String meetingTitle;
	private String meetingDescription; 
	private String meetingAddress;
	private String meetingDate;
	private String meetingStartTime;
	private String meetingEndTime;
	private int meetingTrackTime;
	private Set<UserInstance> attendees = new HashSet<UserInstance>();
	private int meetingInitiatorID;
	
	
	// to be deleted (new constructor has been created but since i don't want to get errors in display meeting, 
	// i ll just leave it here for now - tina
	public MeetingInstance(int meetingID, int meetingLat, int meetingLong, String meetingSubject, Date meetingDate, Object[] attendees){
		this.meetingID = meetingID;
		this.meetingLat = meetingLat;
		this.meetingLon = meetingLong;
		this.meetingTitle = meetingSubject;
		//this.meetingDate = meetingDate;
		//this.attendees = attendees;

	}
	
	// to be deleted, for testing db only
	public MeetingInstance(int meetingID, String meetingTitle){
		this.meetingID = meetingID;
		this.meetingTitle = meetingTitle;
	}
	
	// to be deleted, for testing
	public MeetingInstance(String meetingTitle){
		this.meetingTitle = meetingTitle;
	}
	
	public MeetingInstance(int meetingID, int meetingLat, int meetingLon, String meetingTitle, String meetingDescription,
			String meetingAddress, String meetingDate, String meetingStartTime, String meetingEndTime, int meetingTrackTime, int meetingInitiatorID){
		this.meetingID = meetingID;
		this.meetingLat = meetingLat;
		this.meetingLon = meetingLon;
		this.meetingTitle = meetingTitle;
		this.meetingDescription = meetingDescription;
		this.meetingAddress = meetingAddress;
		this.meetingDate = meetingDate;
		this.meetingStartTime = meetingStartTime;
		this.meetingEndTime = meetingEndTime;
		this.meetingTrackTime = meetingTrackTime;
		this.meetingInitiatorID = meetingInitiatorID;
	}
	
	public MeetingInstance(int meetingID, int meetingLat, int meetingLon, String meetingTitle, String meetingDescription,
				String meetingAddress, String meetingDate, String meetingStartTime, String meetingEndTime, int meetingTrackTime,
				Set<UserInstance> attendees, int meetingInitiatorID){
		this.meetingID = meetingID;
		this.meetingLat = meetingLat;
		this.meetingLon = meetingLon;
		this.meetingTitle = meetingTitle;
		this.meetingDescription = meetingDescription;
		this.meetingAddress = meetingAddress;
		this.meetingDate = meetingDate;
		this.meetingStartTime = meetingStartTime;
		this.meetingEndTime = meetingEndTime;
		this.meetingTrackTime = meetingTrackTime;
		this.attendees = attendees;
		this.meetingInitiatorID = meetingInitiatorID;
	}
	

	
	/*************************************************
	 * 
	 * Setters
	 *
	 *************************************************/
	
	public void setMeetingID(int meetingID)
	{
		this.meetingID = meetingID;
	}
	
	public void setMeetingLat(int meetingLat)
	{
		this.meetingLat = meetingLat;
	}
	
	public void setMeetingLong(int meetingLon)
	{
		this.meetingLon = meetingLon;
	}
	
	public void setMeetingAddress(String newAddress)
	{
		this.meetingAddress = newAddress;
	}
	
	public void setMeetingSubject(String meetingTitle)
	{
		this.meetingTitle = meetingTitle;
	}
	
	public void setMeetingDate(String meetingDate)
	{
		this.meetingDate = meetingDate;
	}
	
	public void setMeetingTrackTime(int meetingTrackTime){
		this.meetingTrackTime = meetingTrackTime;
	}
	
	public void setAttendees(Set<UserInstance> attendees)
	{
		this.attendees = attendees;
	}
	
	
	/*************************************************
	 * 
	 * Getters
	 *
	 *************************************************/
	
	public int getMeetingLat()
	{
		return this.meetingLat;
	}
	
	public int getMeetingLong()
	{
		return this.meetingLon;
	}
	
	public String getMeetingAddress()
	{
		return this.meetingAddress;
	}
	
	public int getMeetingID()
	{
		return this.meetingID;
	}
	
	public String getMeetingSubject()
	{
		return this.meetingTitle;
	}
	
	public String getMeetingDate()
	{
		return this.meetingDate;
	}
	
	public int getMeetingTrackTime()
	{
		return this.meetingTrackTime;
	}
	
	public Set<UserInstance> getMeetingAttendees()
	{
		return this.attendees;
	}
	
	public int getMeetingInitiatorID(){
		return this.meetingInitiatorID;
	}
}
