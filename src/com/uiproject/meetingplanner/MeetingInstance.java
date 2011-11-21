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

import android.util.Log;

public class MeetingInstance {
	
	public static final String TAG = "MeetingInstance";
	
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
	private boolean selected;
	private int imageResourceID;

	
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
		this.selected = false;
		this.imageResourceID = 0;
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
		this.selected = false;
		this.imageResourceID = 0;
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
		this.selected = false;
		this.imageResourceID = 0;
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
	
	public void setSelected(boolean selected){
		this.selected = selected;
	}
	
	public void setMeetingImageResourceID(int imageResourceID){
		this.imageResourceID = imageResourceID;
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
	
	public boolean isSelected(){
		return this.selected;
	}
	
	public int getMeetingImageResourceID(){
		return this.imageResourceID;
	}
}
