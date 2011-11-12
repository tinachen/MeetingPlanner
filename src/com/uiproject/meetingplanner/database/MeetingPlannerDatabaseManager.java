package com.uiproject.meetingplanner.database;

import java.util.ArrayList;
import java.util.HashMap;

import com.uiproject.meetingplanner.MeetingInstance;
import com.uiproject.meetingplanner.UserInstance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class MeetingPlannerDatabaseManager {

	private Context context;
	private int version;
	private SQLiteDatabase db;
	private MeetingPlannerDatabaseHelper dbHelper;
	public final static String dbManagerTag = "MeetingPlannerDbManager";
	
	public MeetingPlannerDatabaseManager(Context context, int version){
		this.context = context;
		this.version = version;
	}
	
	public MeetingPlannerDatabaseManager open() throws SQLException {
		dbHelper = new MeetingPlannerDatabaseHelper(context, version);
		db = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		dbHelper.close();
	}
	
	
	/*******************************************************************
	 * 
	 * All insert/update SQL statements start from here
	 * 
	 *******************************************************************/
	
	public void createMeeting(/*int meetingID, */String meetingTitle, int meetingLat, int meetingLon,
				String meetingDescription, String meetingAddress, String meetingDate, String meetingStartTime,
				String meetingEndTime, int meetingTrackTime, int meetingInitiatorID){
		
		ContentValues values = new ContentValues();
		//values.put(dbHelper.MEETING_ID, meetingID); //TODO
		values.put(dbHelper.MEETING_TITLE, meetingTitle);
		values.put(dbHelper.MEETING_LAT, meetingLat);
		values.put(dbHelper.MEETING_LON, meetingLon);
		values.put(dbHelper.MEETING_DESCRIPTION, meetingDescription);
		values.put(dbHelper.MEETING_ADDRESS, meetingAddress);
		values.put(dbHelper.MEETING_DATE, meetingDate.toString());
		values.put(dbHelper.MEETING_STARTTIME, meetingStartTime);
		values.put(dbHelper.MEETING_ENDTIME, meetingEndTime);
		values.put(dbHelper.MEETING_TRACKTIME, meetingTrackTime);
		values.put(dbHelper.MEETING_INITIATOR_ID, meetingInitiatorID);	
		
		try{
			long l= db.insert(dbHelper.MEETING_TABLE, null, values);
		
			String s1 = "debug " + l;
			Toast.makeText(context, s1, Toast.LENGTH_SHORT).show();
		}
		catch(Exception e)
		{
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
		
	}
	
	public void updateMeeting(int meetingID, String meetingTitle, int meetingLat, int meetingLon,
		String meetingDescription, String meetingAddress, String meetingDate, String meetingStartTime,
		String meetingEndTime, int meetingTrackTime){
		
		ContentValues values = new ContentValues();
		values.put(dbHelper.MEETING_TITLE, meetingTitle);
		values.put(dbHelper.MEETING_LAT, meetingLat);
		values.put(dbHelper.MEETING_LON, meetingLon);
		values.put(dbHelper.MEETING_DESCRIPTION, meetingDescription);
		values.put(dbHelper.MEETING_ADDRESS, meetingAddress);
		values.put(dbHelper.MEETING_DATE, meetingDate);
		values.put(dbHelper.MEETING_STARTTIME, meetingStartTime);
		values.put(dbHelper.MEETING_ENDTIME, meetingEndTime);
		values.put(dbHelper.MEETING_TRACKTIME, meetingTrackTime);
		
		try {
			db.update(dbHelper.MEETING_TABLE, values, dbHelper.MEETING_ID + "=" + meetingID, null);}
		catch (Exception e)	{
			Log.e("DB Error", e.toString());
			e.printStackTrace();
		}
	}
	
	public void deleteMeeting(int meetingID){
		try {
			db.delete(dbHelper.MEETING_TABLE, dbHelper.MEETING_ID + "=" + meetingID, null);
		}catch (Exception e){
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
	}
	
	public void createUser(int userID, String userFirstName, String userLastName, String userEmail, 
							String userPhone, int userLocationLon, int userLocationLat){
		
		ContentValues values = new ContentValues();
		values.put(dbHelper.USER_ID, userID);
		values.put(dbHelper.USER_FIRSTNAME, userFirstName);
		values.put(dbHelper.USER_LASTNAME, userLastName);
		values.put(dbHelper.USER_EMAIL, userEmail);
		values.put(dbHelper.USER_PHONE, userPhone);
		values.put(dbHelper.USER_LOCATIONLON, userLocationLon);
		values.put(dbHelper.USER_LOCATIONLAT, userLocationLat);
		
		try{
			db.insert(dbHelper.USER_TABLE, null, values);
		}
		catch(Exception e)
		{
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
	}
	
	public void updateUserProfile(int userID, String userFirstName, String userLastName, String userEmail, 
		String userPhone, int userLocationLon, int userLocationLat){
		
		ContentValues values = new ContentValues();
		values.put(dbHelper.USER_FIRSTNAME, userFirstName);
		values.put(dbHelper.USER_LASTNAME, userLastName);
		values.put(dbHelper.USER_EMAIL, userEmail);
		values.put(dbHelper.USER_PHONE, userPhone);
		
		try {
			db.update(dbHelper.USER_TABLE, values, dbHelper.USER_ID + "=" + userID, null);}
		catch (Exception e)	{
			Log.e("DB Error", e.toString());
			e.printStackTrace();
		}
	}
	
	public void updateUserLocation(int userID, int userLocationLon, int userLocationLat){
		ContentValues values = new ContentValues();
		values.put(dbHelper.USER_LOCATIONLON, userLocationLon);
		values.put(dbHelper.USER_LOCATIONLAT, userLocationLat);
		
		try {
			db.update(dbHelper.USER_TABLE, values, dbHelper.USER_ID + "=" + userID, null);}
		catch (Exception e)	{
			Log.e("DB Error", e.toString());
			e.printStackTrace();
		}
	}
	
	public void deleteUser(int userID){
		try {
			db.delete(dbHelper.USER_TABLE, dbHelper.USER_ID + "=" + userID, null);
		}catch (Exception e){
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
	}
	
	public void createMeetingUser(int meetingID, int userID, int attendingStatusID, String meetingUserEta){
		ContentValues values = new ContentValues();
		values.put(dbHelper.MEETING_ID, meetingID);
		values.put(dbHelper.USER_ID, userID);
		values.put(dbHelper.ATTENDINGSTATUS_ID, attendingStatusID);
		values.put(dbHelper.MEETINGUSER_ETA, meetingUserEta);
		
		try{
			db.insert(dbHelper.MEETINGUSER_TABLE, null, values);
		}
		catch(Exception e)
		{
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
	}
	
	public void updateMeetingUser(int meetingID, int userID, int attendingStatusID, String meetingUserEta){
		ContentValues values = new ContentValues();
		values.put(dbHelper.ATTENDINGSTATUS_ID, attendingStatusID);
		values.put(dbHelper.MEETINGUSER_ETA, meetingUserEta);
		
		try {
			db.update(dbHelper.MEETINGUSER_TABLE, values, dbHelper.USER_ID + "=" + userID + " AND " + dbHelper.MEETING_ID + "=" + meetingID, null);}
		catch (Exception e)	{
			Log.e("DB Error", e.toString());
			e.printStackTrace();
		}
	}
	
	public void updateMeetingUserAttendingStatus(int meetingID, int userID, int attendingStatusID){
		ContentValues values = new ContentValues();
		values.put(dbHelper.ATTENDINGSTATUS_ID, attendingStatusID);
		
		try {
			db.update(dbHelper.MEETINGUSER_TABLE, values, dbHelper.USER_ID + "=" + userID + " AND " + dbHelper.MEETING_ID + "=" + meetingID, null);}
		catch (Exception e)	{
			Log.e("DB Error", e.toString());
			e.printStackTrace();
		}
	}
	
	public void updateMeetingUserEta(int meetingID, int userID, String meetingUserEta){
		ContentValues values = new ContentValues();
		values.put(dbHelper.MEETINGUSER_ETA, meetingUserEta);
		
		try {
			db.update(dbHelper.MEETINGUSER_TABLE, values, dbHelper.USER_ID + "=" + userID + " AND " + dbHelper.MEETING_ID + "=" + meetingID, null);}
		catch (Exception e)	{
			Log.e("DB Error", e.toString());
			e.printStackTrace();
		}
	}
	
	public void deleteMeetingUser(int meetingID, int userID){
		try {
			db.delete(dbHelper.MEETINGUSER_TABLE, dbHelper.USER_ID + "=" + userID + " AND " + dbHelper.MEETING_ID + " = " + meetingID, null);
		}catch (Exception e){
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
	}
	/*******************************************************************
	 * 
	 * All select SQL statements start from here
	 * 
	 *******************************************************************/
	public ArrayList<MeetingInstance> getAllMeetings(){
		/*
		 * meetingsArray - used for storing meeting info
		 * cursor - used for storing query result 
		 */
		ArrayList<MeetingInstance> meetingsArray = new ArrayList<MeetingInstance>();
		Cursor cursor;
		
		try{
			// Do the query
			cursor = db.query(
					dbHelper.MEETING_TABLE,
					new String[]{dbHelper.MEETING_ID, dbHelper.MEETING_LAT, dbHelper.MEETING_LON, dbHelper.MEETING_TITLE,
							dbHelper.MEETING_DESCRIPTION, dbHelper.MEETING_ADDRESS, dbHelper.MEETING_DATE, dbHelper.MEETING_STARTTIME,
							dbHelper.MEETING_ENDTIME, dbHelper.MEETING_TRACKTIME, dbHelper.MEETING_INITIATOR_ID},
					null, null, null, null, null
			);
			
			// move the cursor's pointer to position zero.
			cursor.moveToFirst();
 
			// if there is data after the current cursor position, add it
			// to the ArrayList.
			if (!cursor.isAfterLast())
			{
				do
				{
					int meetingID = cursor.getInt(0);
					int meetingLat = cursor.getInt(1);
					int meetingLon = cursor.getInt(2);
					String meetingTitle = cursor.getString(3);
					String meetingDescription = cursor.getString(4);
					String meetingAddress = cursor.getString(5);
					String meetingDate = cursor.getString(6);
					String meetingStartTime = cursor.getString(7);
					String meetingEndTime = cursor.getString(8);
					int meetingTrackTime = cursor.getInt(9);
					int meetingInitiatorID = cursor.getInt(10);
					
					MeetingInstance m = new MeetingInstance(meetingID, meetingLat, meetingLon, 
							meetingTitle, meetingDescription, meetingAddress, 
							meetingDate, meetingStartTime, meetingEndTime, meetingTrackTime, meetingInitiatorID);
					
					meetingsArray.add(m);
					
					// Parse the date
					/*Date meetingDate; //TODO
					String meetingDateString = cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.MEETING_DATE));
					DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd");
					
					try {
						meetingDate = iso8601Format.parse(meetingDateString);
						
						MeetingInstance m = new MeetingInstance(meetingID, meetingLat, meetingLon, 
								meetingTitle, meetingDescription, meetingAddress, 
								meetingDate,
								meetingStartTime, meetingEndTime, meetingTrackTime);
						
						meetingsArray.add(m);
						
					} catch (ParseException e) {
						Log.e("Parsing ISO8601 datetime failed", e.toString());
					} */
					
				}
				// move the cursor's pointer up one position.
				while (cursor.moveToNext());
			}
		}catch(SQLException e) 
		{
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
		
		return meetingsArray;
	}

	//TODO
	public ArrayList<MeetingInstance> getAcceptedMeetings(int userID){
		ArrayList<MeetingInstance> meetingsArray = new ArrayList<MeetingInstance>();
		Cursor cursor;
		
		try{
			
			String query = "SELECT " + dbHelper.MEETING_ID + "," + dbHelper.MEETING_TITLE  + "," + dbHelper.MEETING_LAT+ "," + dbHelper.MEETING_LON + "," 
				+ dbHelper.MEETING_DESCRIPTION + "," + dbHelper.MEETING_ADDRESS + "," + dbHelper.MEETING_DATE + "," + dbHelper.MEETING_STARTTIME + "," + dbHelper.MEETING_ENDTIME + "," 
				+ dbHelper.MEETING_TRACKTIME + "," + dbHelper.MEETING_INITIATOR_ID
 				+ " FROM " + dbHelper.MEETINGUSER_TABLE 
				+ " LEFT JOIN " + dbHelper.MEETING_TABLE + " ON " + dbHelper.MEETINGUSER_TABLE + "." + dbHelper.MEETING_ID + "=" + dbHelper.MEETING_TABLE + "." + dbHelper.MEETING_ID
				+ " WHERE " + dbHelper.MEETINGUSER_TABLE + "." + dbHelper.USER_ID + "=?"
				+ " AND " + dbHelper.MEETINGUSER_TABLE + "." + dbHelper.ATTENDINGSTATUS_ID + "=?";
			
			Log.v(dbManagerTag, "getDeclinedMeetings query1: " + query);
			
			// Do the query
			cursor = db.rawQuery(query, new String[]{String.valueOf(userID), String.valueOf(dbHelper.ATTENDINGSTATUS_ATTENDING)});
			
			// move the cursor's pointer to position zero.
			cursor.moveToFirst();
 
			// if there is data after the current cursor position, add it
			// to the ArrayList.
			if (!cursor.isAfterLast())
			{
				do
				{
					int meetingID = cursor.getInt(0);
					String meetingTitle = cursor.getString(1);
					int meetingLat = cursor.getInt(2);
					int meetingLon = cursor.getInt(3);
					String meetingDescription = cursor.getString(4);
					String meetingAddress = cursor.getString(5);
					String meetingDate = cursor.getString(6);
					String meetingStartTime = cursor.getString(7);
					String meetingEndTime = cursor.getString(8);
					int meetingTrackTime = cursor.getInt(9);
					int meetingInitiatorID = cursor.getInt(10);

					MeetingInstance m = new MeetingInstance(meetingID, meetingLat, meetingLon, 
							meetingTitle, meetingDescription, meetingAddress, 
							meetingDate, meetingStartTime, meetingEndTime, meetingTrackTime, meetingInitiatorID);
					
					// Do another query to get every meeting's invited users
					HashMap<Integer, UserInstance> meetingUsersMap = getMeetingUsersMap(meetingID);
					
					// Add meeting users to the meeting
					m.setMeetingAttendees(meetingUsersMap);
					
					// Add current meeting into meeting array
					meetingsArray.add(m);
					
				}
				// move the cursor's pointer up one position.
				while (cursor.moveToNext());
			}
		}catch(SQLException e) 
		{
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
		
		return meetingsArray;
	}
	
	//TODO
	public ArrayList<MeetingInstance> getDeclinedMeetings(int userID){
		ArrayList<MeetingInstance> meetingsArray = new ArrayList<MeetingInstance>();
		Cursor cursor;
		
		try{
			
			String query = "SELECT " + dbHelper.MEETING_ID + "," + dbHelper.MEETING_TITLE  + "," + dbHelper.MEETING_LAT+ "," + dbHelper.MEETING_LON + "," 
				+ dbHelper.MEETING_DESCRIPTION + "," + dbHelper.MEETING_ADDRESS + "," + dbHelper.MEETING_DATE + "," + dbHelper.MEETING_STARTTIME + "," + dbHelper.MEETING_ENDTIME + "," 
				+ dbHelper.MEETING_TRACKTIME + "," + dbHelper.MEETING_INITIATOR_ID
 				+ " FROM " + dbHelper.MEETINGUSER_TABLE 
				+ " LEFT JOIN " + dbHelper.MEETING_TABLE + " ON " + dbHelper.MEETINGUSER_TABLE + "." + dbHelper.MEETING_ID + "=" + dbHelper.MEETING_TABLE + "." + dbHelper.MEETING_ID
				+ " WHERE " + dbHelper.MEETINGUSER_TABLE + "." + dbHelper.USER_ID + "=?"
				+ " AND " + dbHelper.MEETINGUSER_TABLE + "." + dbHelper.ATTENDINGSTATUS_ID + "=?";
			
			Log.v(dbManagerTag, "getDeclinedMeetings query1: " + query);
			
			// Do the query
			cursor = db.rawQuery(query, new String[]{String.valueOf(userID), String.valueOf(dbHelper.ATTENDINGSTATUS_DECLINING)});
			
			// move the cursor's pointer to position zero.
			cursor.moveToFirst();
 
			// if there is data after the current cursor position, add it
			// to the ArrayList.
			if (!cursor.isAfterLast())
			{
				do
				{
					int meetingID = cursor.getInt(0);
					String meetingTitle = cursor.getString(1);
					int meetingLat = cursor.getInt(2);
					int meetingLon = cursor.getInt(3);
					String meetingDescription = cursor.getString(4);
					String meetingAddress = cursor.getString(5);
					String meetingDate = cursor.getString(6);
					String meetingStartTime = cursor.getString(7);
					String meetingEndTime = cursor.getString(8);
					int meetingTrackTime = cursor.getInt(9);
					int meetingInitiatorID = cursor.getInt(10);

					MeetingInstance m = new MeetingInstance(meetingID, meetingLat, meetingLon, 
							meetingTitle, meetingDescription, meetingAddress, 
							meetingDate, meetingStartTime, meetingEndTime, meetingTrackTime, meetingInitiatorID);
					
					// Do another query to get every meeting's invited users
					HashMap<Integer, UserInstance> meetingUsersMap = getMeetingUsersMap(meetingID);
					
					// Add meeting users to the meeting
					m.setMeetingAttendees(meetingUsersMap);
					
					// Add current meeting into meeting array
					meetingsArray.add(m);
					
				}
				// move the cursor's pointer up one position.
				while (cursor.moveToNext());
			}
		}catch(SQLException e) 
		{
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
		
		return meetingsArray;
	}
	
	//Done - need testing
	public ArrayList<MeetingInstance> getPendingMeetings(int userID){
		ArrayList<MeetingInstance> meetingsArray = new ArrayList<MeetingInstance>();
		Cursor cursor;
		
		try{
			
			String query = "SELECT " + dbHelper.MEETING_ID + "," + dbHelper.MEETING_TITLE  + "," + dbHelper.MEETING_LAT+ "," + dbHelper.MEETING_LON + "," 
				+ dbHelper.MEETING_DESCRIPTION + "," + dbHelper.MEETING_ADDRESS + "," + dbHelper.MEETING_DATE + "," + dbHelper.MEETING_STARTTIME + "," + dbHelper.MEETING_ENDTIME + "," 
				+ dbHelper.MEETING_TRACKTIME + "," + dbHelper.MEETING_INITIATOR_ID
 				+ " FROM " + dbHelper.MEETINGUSER_TABLE 
				+ " LEFT JOIN " + dbHelper.MEETING_TABLE + " ON " + dbHelper.MEETINGUSER_TABLE + "." + dbHelper.MEETING_ID + "=" + dbHelper.MEETING_TABLE + "." + dbHelper.MEETING_ID
				+ " WHERE " + dbHelper.MEETINGUSER_TABLE + "." + dbHelper.USER_ID + "=?"
				+ " AND " + dbHelper.MEETINGUSER_TABLE + "." + dbHelper.ATTENDINGSTATUS_ID + "=?";
			
			Log.v(dbManagerTag, "getPendingMeetings query1: " + query);
			
			// Do the query
			cursor = db.rawQuery(query, new String[]{String.valueOf(userID), String.valueOf(dbHelper.ATTENDINGSTATUS_PENDING)});
			
			// move the cursor's pointer to position zero.
			cursor.moveToFirst();
 
			// if there is data after the current cursor position, add it
			// to the ArrayList.
			if (!cursor.isAfterLast())
			{
				do
				{
					int meetingID = cursor.getInt(0);
					String meetingTitle = cursor.getString(1);
					int meetingLat = cursor.getInt(2);
					int meetingLon = cursor.getInt(3);
					String meetingDescription = cursor.getString(4);
					String meetingAddress = cursor.getString(5);
					String meetingDate = cursor.getString(6);
					String meetingStartTime = cursor.getString(7);
					String meetingEndTime = cursor.getString(8);
					int meetingTrackTime = cursor.getInt(9);
					int meetingInitiatorID = cursor.getInt(10);

					MeetingInstance m = new MeetingInstance(meetingID, meetingLat, meetingLon, 
							meetingTitle, meetingDescription, meetingAddress, 
							meetingDate, meetingStartTime, meetingEndTime, meetingTrackTime, meetingInitiatorID);
					
					// Do another query to get every meeting's invited users
					HashMap<Integer, UserInstance> meetingUsersMap = getMeetingUsersMap(meetingID);
					
					// Add meeting users to the meeting
					m.setMeetingAttendees(meetingUsersMap);
					
					// Add current meeting into meeting array
					meetingsArray.add(m);
					
				}
				// move the cursor's pointer up one position.
				while (cursor.moveToNext());
			}
		}catch(SQLException e) 
		{
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
		
		return meetingsArray;
	}
	
	public HashMap<Integer, UserInstance> getMeetingUsersMap(int meetingID){
		
		/*
		 * meetingUsersArray - used for storing meeting's users info
		 * cursor - used for storing query result 
		 */
		HashMap<Integer, UserInstance> meetingUsersMap = new HashMap<Integer, UserInstance>();
		Cursor cursor;
		
		try{
			
			//String MY_QUERY = "SELECT * FROM table_a a INNER JOIN table_b b ON a.id=b.other_id WHERE b.property_id=?";
			String query = "SELECT " + dbHelper.USER_ID + "," + dbHelper.USER_FIRSTNAME  + "," + dbHelper.USER_LASTNAME+ "," + dbHelper.USER_EMAIL + "," 
				+ dbHelper.USER_PHONE + "," + dbHelper.USER_LOCATIONLON + "," + dbHelper.USER_LOCATIONLAT + "," + dbHelper.MEETINGUSER_ETA + "," + dbHelper.ATTENDINGSTATUS_NAME 
				+ " FROM " + dbHelper.MEETINGUSER_TABLE 
				+ " LEFT JOIN " + dbHelper.USER_TABLE + " ON " + dbHelper.MEETINGUSER_TABLE + "." + dbHelper.USER_ID + "=" + dbHelper.USER_TABLE + "." + dbHelper.USER_ID
				+ " LEFT JOIN " + dbHelper.ATTENDINGSTATUS_TABLE + " ON " + dbHelper.MEETINGUSER_TABLE + "." + dbHelper.ATTENDINGSTATUS_ID + "=" + dbHelper.ATTENDINGSTATUS_TABLE + "." + dbHelper.ATTENDINGSTATUS_ID
				+ " WHERE " + dbHelper.MEETINGUSER_TABLE + "." + dbHelper.MEETING_ID + "=?";
			
			// Do the query
			cursor = db.rawQuery(query, new String[]{String.valueOf(meetingID)});
			
			// move the cursor's pointer to position zero.
			cursor.moveToFirst();
 
			// if there is data after the current cursor position, add it
			// to the ArrayList.
			if (!cursor.isAfterLast())
			{
				do
				{
					int userID = cursor.getInt(0);
					String userFirstName = cursor.getString(1);
					String userLastName = cursor.getString(2);
					String userEmail = cursor.getString(3);
					String userPhone = cursor.getString(4);
					int userLocationLon = cursor.getInt(5);
					int userLocationLat = cursor.getInt(6);
					String userEta = cursor.getString(7);
					String userAttendingStatus = cursor.getString(8);
					
					UserInstance u = new UserInstance(userID, userFirstName, userLastName, 
														userEmail, userPhone, userLocationLon, 
														userLocationLat, userEta, userAttendingStatus);
					
					meetingUsersMap.put(userID, u);
					
				}
				// move the cursor's pointer up one position.
				while (cursor.moveToNext());
			}
		}catch(SQLException e) 
		{
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
		
		return meetingUsersMap;
	}
	
	public ArrayList<UserInstance> getMeetingUsersArray(int meetingID){
		
		/*
		 * meetingUsersArray - used for storing meeting's users info
		 * cursor - used for storing query result 
		 */
		ArrayList<UserInstance> meetingUsersArray = new ArrayList<UserInstance>();
		Cursor cursor;
		
		try{
			
			//String MY_QUERY = "SELECT * FROM table_a a INNER JOIN table_b b ON a.id=b.other_id WHERE b.property_id=?";
			String query = "SELECT " + dbHelper.USER_ID + "," + dbHelper.USER_FIRSTNAME  + "," + dbHelper.USER_LASTNAME+ "," + dbHelper.USER_EMAIL + "," 
				+ dbHelper.USER_PHONE + "," + dbHelper.USER_LOCATIONLON + "," + dbHelper.USER_LOCATIONLAT + "," + dbHelper.MEETINGUSER_ETA + "," + dbHelper.ATTENDINGSTATUS_NAME 
				+ " FROM " + dbHelper.MEETINGUSER_TABLE 
				+ " LEFT JOIN " + dbHelper.USER_TABLE + " ON " + dbHelper.MEETINGUSER_TABLE + "." + dbHelper.USER_ID + "=" + dbHelper.USER_TABLE + "." + dbHelper.USER_ID
				+ " LEFT JOIN " + dbHelper.ATTENDINGSTATUS_TABLE + " ON " + dbHelper.MEETINGUSER_TABLE + "." + dbHelper.ATTENDINGSTATUS_ID + "=" + dbHelper.ATTENDINGSTATUS_TABLE + "." + dbHelper.ATTENDINGSTATUS_ID
				+ " WHERE " + dbHelper.MEETINGUSER_TABLE + "." + dbHelper.MEETING_ID + "=?";
			
			// Do the query
			cursor = db.rawQuery(query, new String[]{String.valueOf(meetingID)});
			
			// move the cursor's pointer to position zero.
			cursor.moveToFirst();
 
			// if there is data after the current cursor position, add it
			// to the ArrayList.
			if (!cursor.isAfterLast())
			{
				do
				{
					int userID = cursor.getInt(0);
					String userFirstName = cursor.getString(1);
					String userLastName = cursor.getString(2);
					String userEmail = cursor.getString(3);
					String userPhone = cursor.getString(4);
					int userLocationLon = cursor.getInt(5);
					int userLocationLat = cursor.getInt(6);
					String userEta = cursor.getString(7);
					String userAttendingStatus = cursor.getString(8);
					
					UserInstance u = new UserInstance(userID, userFirstName, userLastName, 
														userEmail, userPhone, userLocationLon, 
														userLocationLat, userEta, userAttendingStatus);
					
					meetingUsersArray.add(u);
					
				}
				// move the cursor's pointer up one position.
				while (cursor.moveToNext());
			}
		}catch(SQLException e) 
		{
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
		
		return meetingUsersArray;
	}
	
	
	public UserInstance getUser(int userID){
		Cursor cursor;
		UserInstance user = new UserInstance(userID);

		try{
			// Do the query
			cursor = db.query(
					dbHelper.USER_TABLE,
					new String[]{dbHelper.USER_FIRSTNAME, dbHelper.USER_LASTNAME, dbHelper.USER_EMAIL, dbHelper.USER_PHONE,
							dbHelper.USER_LOCATIONLAT, dbHelper.USER_LOCATIONLON},
					dbHelper.USER_ID + "=?", new String[]{Integer.toString(userID)}, null, null, null
			);
			
			String tag = "MeetingPlannerDb";
			Log.v(tag, "getUser");
			
			// move the cursor's pointer to position zero.
			cursor.moveToFirst();
 
			// if there is data after the current cursor position, add it
			// to the ArrayList.
			if (!cursor.isAfterLast())
			{
				do
				{	
					String userFirstName = cursor.getString(0);
					String userLastName = cursor.getString(1);
					String userEmail = cursor.getString(2);
					String userPhone = cursor.getString(3);
					int userLocationLat = cursor.getInt(4);
					int userLocationLon = cursor.getInt(5);
					
					user.setUserFirstName(userFirstName);
					user.setUserLastName(userLastName);
					user.setUserEmail(userEmail);
					user.setUserPhone(userPhone);
					user.setUserLocationLat(userLocationLat);
					user.setUserLocationLon(userLocationLon);
					
				}
				
				// move the cursor's pointer up one position.
				while (cursor.moveToNext());
			}
		}catch(SQLException e) 
		{
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
		
		return user;
	}
}
