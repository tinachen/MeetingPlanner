package com.uiproject.meetingplanner.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MeetingPlannerDatabaseHelper extends SQLiteOpenHelper{

	private final static String DATABASE_NAME = "MeetingPlanner";
	
	// Meetings Table
	public final String MEETING_TABLE = "Meetings";
	public final String MEETING_ID = "MeetingID";
	public final String MEETING_TITLE = "MeetingTitle";
	public final String MEETING_LAT = "MeetingLat";
	public final String MEETING_LON = "MeetingLon";
	public final String MEETING_DESCRIPTION = "MeetingDescription";
	public final String MEETING_ADDRESS = "MeetingAddress";
	public final String MEETING_DATE = "MeetingDate";
	public final String MEETING_STARTTIME = "MeetingStartTime";
	public final String MEETING_ENDTIME = "MeetingEndTime";
	public final String MEETING_TRACKTIME = "MeetingTrackTime";
	public final String MEETING_INITIATOR_ID = "MeetingInitiatorID";
	
	// Users Table
	public final String USER_TABLE = "Users";
	public final String USER_ID = "UserID";
	public final String USER_FIRSTNAME = "UserFirstName";
	public final String USER_LASTNAME = "UserLastName";
	public final String USER_EMAIL = "UserEmail";
	public final String USER_PASSWORD = "UserPassword";
	public final String USER_PHONE = "UserPhone";
	public final String USER_LOCATIONLON = "UserLocationLon";
	public final String USER_LOCATIONLAT = "UserLocationLat";
	
	// MeetingUsers Table
	public final String MEETINGUSER_TABLE = "MeetingUsers";
	public final String MEETINGUSER_ETA = "MeetingUserEta";
	
	// AttendingStatus Table
	public final String ATTENDINGSTATUS_TABLE = "AttendingStatus";
	public final String ATTENDINGSTATUS_ID = "AttendingStatusID";
	public final String ATTENDINGSTATUS_NAME = "AttendingStatusName";
	public final int ATTENDINGSTATUS_ATTENDING = 0;
	public final int ATTENDINGSTATUS_DECLINING = 1;
	public final int ATTENDINGSTATUS_PENDING = 2;
	
	
	public MeetingPlannerDatabaseHelper(Context context, int version){
		
		super(context, DATABASE_NAME, null, version);
	}
	
	private void createTables(SQLiteDatabase db){
		// Create Tables statements
		String sql_create_meetings = "CREATE TABLE " + MEETING_TABLE + " (" +
										MEETING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
										MEETING_TITLE + " TEXT NOT NULL," +
										MEETING_LAT + " INTEGER NOT NULL," +
										MEETING_LON + " INTEGER NOT NULL," +
										MEETING_DESCRIPTION + " TEXT DEFAULT NULL," +
										MEETING_ADDRESS + " TEXT NOT NULL," +
										MEETING_DATE + " TEXT NOT NULL," +
										MEETING_STARTTIME + " TEXT NOT NULL," +
										MEETING_ENDTIME + " TEXT NOT NULL," +
										MEETING_TRACKTIME + " INTEGER NOT NULL," +	// minutes
										MEETING_INITIATOR_ID + " INTEGER NOT NULL" + //TODO commas redo
										//"FOREIGN KEY (" + MEETING_INITIATOR_ID + ") REFERENCES " + USER_TABLE + "(" + USER_ID + ")" +
									");";
		
		String sql_create_users = "CREATE TABLE " + USER_TABLE + " (" +
									USER_ID + " INTEGER PRIMARY KEY NOT NULL," +
									USER_FIRSTNAME + " TEXT NOT NULL," +
									USER_LASTNAME + " TEXT NOT NULL," +
									USER_EMAIL + " TEXT DEFAULT NULL," +
									USER_PHONE + " TEXT NOT NULL," +
									USER_LOCATIONLON + " INTEGER NOT NULL DEFAULT 0," +
									USER_LOCATIONLAT + " INTEGER NOT NULL DEFAULT 0" +
								");";
		
		String sql_create_meetingusers = "CREATE TABLE " + MEETINGUSER_TABLE + " (" +
											MEETING_ID + " INTEGER NOT NULL," +
											USER_ID + " INTEGER NOT NULL," +
											ATTENDINGSTATUS_ID + " INTEGER NOT NULL," +
											MEETINGUSER_ETA + "TEXT DEFAULT 0 NOT NULL," +
											"PRIMARY KEY (" + MEETING_ID + ", " + USER_ID +")," +
											"FOREIGN KEY (" + MEETING_ID + ") REFERENCES " + MEETING_TABLE + "(" + MEETING_ID + ")," +
											"FOREIGN KEY (" + USER_ID + ") REFERENCES " + USER_TABLE + "(" + USER_ID + ")," +
											"FOREIGN KEY (" + ATTENDINGSTATUS_ID + ") REFERENCES " + ATTENDINGSTATUS_TABLE + "(" + ATTENDINGSTATUS_ID + ")" +
										");";
		
		String sql_create_attendingstatus = "CREATE TABLE " + ATTENDINGSTATUS_TABLE + " (" +
												ATTENDINGSTATUS_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT," +
												ATTENDINGSTATUS_NAME + " TEXT NOT NULL" +
											");";
		
		db.execSQL(sql_create_meetings);
		db.execSQL(sql_create_users);
		db.execSQL(sql_create_meetingusers);
		db.execSQL(sql_create_attendingstatus);
	}
	
	private void insertAttendingStatuses(SQLiteDatabase db){
		
		
		try{
			ContentValues values = new ContentValues();
			values.put(ATTENDINGSTATUS_NAME, "attending");
			db.insert(ATTENDINGSTATUS_TABLE, null, values);
		
			values = new ContentValues();
			values.put(ATTENDINGSTATUS_NAME, "declining");
			db.insert(ATTENDINGSTATUS_TABLE, null, values);
			
			values = new ContentValues();
			values.put(ATTENDINGSTATUS_NAME, "pending");
			db.insert(ATTENDINGSTATUS_TABLE, null, values);
			
		}
		catch(Exception e)
		{
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
		
	}
	
	public void dropTables(SQLiteDatabase db){
		String sql_drop_meeting = "DROP TABLE IF EXISTS " + MEETING_TABLE +";";
		String sql_drop_meetinguser = "DROP TABLE IF EXISTS " + MEETINGUSER_TABLE +";";
		String sql_drop_user = "DROP TABLE IF EXISTS " + USER_TABLE +";";
		String sql_drop_attendingstatus = "DROP TABLE IF EXISTS " + ATTENDINGSTATUS_TABLE +";";
		
		db.execSQL(sql_drop_meeting);
		db.execSQL(sql_drop_meetinguser);
		db.execSQL(sql_drop_user);
		db.execSQL(sql_drop_attendingstatus);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		createTables(db);
		insertAttendingStatuses(db);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// NOTHING TO DO HERE. THIS IS THE ORIGINAL DATABASE VERSION.
		// OTHERWISE, YOU WOULD SPECIFIY HOW TO UPGRADE THE DATABASE.
		
		dropTables(db);
		onCreate(db);
	}
}
