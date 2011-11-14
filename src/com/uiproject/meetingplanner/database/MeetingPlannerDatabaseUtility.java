package com.uiproject.meetingplanner.database;

import java.text.ParseException;
import java.util.HashMap;

import org.json.JSONException;

import com.uiproject.meetingplanner.Communicator;
import com.uiproject.meetingplanner.MeetingInstance;
import com.uiproject.meetingplanner.UserInstance;

public class MeetingPlannerDatabaseUtility {

	public MeetingPlannerDatabaseUtility(){
		
	}
	
	public static void updateDatabase(MeetingPlannerDatabaseManager db) throws JSONException, ParseException{
		// Get meetings info & user infos from server
    	HashMap<Integer, UserInstance> usersMap = (HashMap<Integer, UserInstance>) Communicator.getAllUsers();
    	HashMap<Integer, MeetingInstance> meetingsMap = (HashMap<Integer, MeetingInstance>) Communicator.getAllMeetings();
    	
    	// 1. Delete all data in db
    	db.deleteAllUsers();
    	db.deleteAllMeetings();
    	db.deleteAllMeetingUsers();
    	
    	// 2-1. Update Users
    	for (UserInstance userObj : usersMap.values()) {
    		db.createUser(userObj.getUserID(), userObj.getUserFirstName(), userObj.getUserLastName(),
    				userObj.getUserEmail(), userObj.getUserPhone(), userObj.getUserLocationLon(), userObj.getUserLocationLat());
    	}

    	// 2-2. Update Meetings
    	for (MeetingInstance meetingObj : meetingsMap.values()) {
    		db.createMeeting(meetingObj.getMeetingID(), meetingObj.getMeetingTitle(), meetingObj.getMeetingLat(), meetingObj.getMeetingLon(),
    						meetingObj.getMeetingDescription(), meetingObj.getMeetingAddress(), meetingObj.getMeetingDate(), 
    						meetingObj.getMeetingStartTime(), meetingObj.getMeetingEndTime(), meetingObj.getMeetingTrackTime(), meetingObj.getMeetingInitiatorID());
    	
    		// 2-3. Update Meeting Users
    		HashMap<Integer, UserInstance> meetingUsers = meetingObj.getMeetingAttendees();
    		
    		for(UserInstance meetingUserObj : meetingUsers.values()){
    			
    			int attendingStatusID = MeetingPlannerDatabaseHelper.ATTENDINGSTATUS_PENDING;
    			
    			if(meetingUserObj.getUserAttendingStatus().compareTo(MeetingPlannerDatabaseHelper.ATTENDINGSTATUS_ATTENDINGSTRING) == 0){
    				attendingStatusID = MeetingPlannerDatabaseHelper.ATTENDINGSTATUS_ATTENDING;
    			}else if(meetingUserObj.getUserAttendingStatus().compareTo(MeetingPlannerDatabaseHelper.ATTENDINGSTATUS_DECLININGSTRING) == 0){
    				attendingStatusID = MeetingPlannerDatabaseHelper.ATTENDINGSTATUS_DECLINING;
    			}
    			
    			db.createMeetingUser(meetingObj.getMeetingID(), meetingUserObj.getUserID(), attendingStatusID, "0");
    		}
    	}
	}
}
