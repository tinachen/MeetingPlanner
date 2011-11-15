/********************
 * 
 * Last Modified by Tina Chen
 * 
 *******************/

package com.uiproject.meetingplanner;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MeetingInstance {
	private int meetingID;
	private int meetingLat;
	private int meetingLon;
	private String meetingTitle;
	private String meetingAddress;
	private String meetingDescription;
	private String meetingDate;
	private String meetingStartTime;
	private String meetingEndTime;
	private int meetingTrackTime;
	private HashMap<Integer, UserInstance> attendees = new HashMap<Integer,UserInstance>();
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
	
	public MeetingInstance(){
		this.meetingID = -1;
		this.meetingLat = -1;
		this.meetingLon = -1;
		this.meetingTitle = "invalid";
		this.meetingDescription = "invalid";
		this.meetingAddress = "invalid";
		this.meetingDate = "invalid";
		this.meetingStartTime = "invalid";
		this.meetingEndTime = "invalid";
		this.meetingTrackTime = -1;
		this.meetingInitiatorID = -1;
		this.attendees = new HashMap<Integer, UserInstance>();
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
		this.attendees = new HashMap<Integer, UserInstance>();
	}
	
	public MeetingInstance(int meetingID, int meetingLat, int meetingLon, String meetingTitle, String meetingDescription,
				String meetingAddress, String meetingDate, String meetingStartTime, String meetingEndTime, int meetingTrackTime,
				HashMap<Integer, UserInstance> attendees, int meetingInitiatorID){
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
	
	public void setMeetingLon(int meetingLon)
	{
		this.meetingLon = meetingLon;
	}
	
	public void setMeetingAddress(String newAddress)
	{
		this.meetingAddress = newAddress;
	}
	
	/*public void setMeetingSubject(String meetingTitle)
	{
		this.meetingTitle = meetingTitle;
	}*/
	
	public void setMeetingTitle(String meetingTitle) {
		this.meetingTitle = meetingTitle;
	}
	
	public void setMeetingDate(String meetingDate)
	{
		this.meetingDate = meetingDate;
	}
	
	public void setMeetingTrackTime(int meetingTrackTime){
		this.meetingTrackTime = meetingTrackTime;
	}
	
	public void setMeetingAttendees(HashMap<Integer, UserInstance> attendees)
	{
		this.attendees = attendees;
	}
	
	public void setMeetingStartTime(String meetingStartTime){
		this.meetingStartTime = meetingStartTime;
	}
	
	public void setMeetingEndTime(String meetingEndTime){
		this.meetingEndTime = meetingEndTime;
	}
	
	public void setMeetingDescription(String meetingDescription){
		this.meetingDescription = meetingDescription;
	}

	public void setMeetingInitiatorID(int meetingInitiatorID) {
		this.meetingInitiatorID = meetingInitiatorID;
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
	
	public int getMeetingLon() {
		return meetingLon;
	}
	
	public String getMeetingAddress()
	{
		return this.meetingAddress;
	}
	
	public int getMeetingID()
	{
		return this.meetingID;
	}
	
	/*public String getMeetingSubject()
	{
		return this.meetingTitle;
	}*/
	
	public String getMeetingTitle() {
		return meetingTitle;
	}
	
	public String getMeetingDate()
	{
		return this.meetingDate;
	}
	
	public int getMeetingTrackTime()
	{
		return this.meetingTrackTime;
	}
	
	public HashMap<Integer, UserInstance> getMeetingAttendees()
	{
		return this.attendees;
	}
	
	public int getMeetingInitiatorID(){
		return this.meetingInitiatorID;
	}
	
	public String getMeetingStartTime(){
		return this.meetingStartTime;
	}
	
	public String getMeetingEndTime(){
		return this.meetingEndTime;
	}
	
	public String getMeetingDescription(){
		return this.meetingDescription;
	}

}
