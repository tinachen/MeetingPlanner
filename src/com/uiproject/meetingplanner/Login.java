package com.uiproject.meetingplanner;

import java.text.ParseException;
import java.util.HashMap;

import org.json.JSONException;

import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseHelper;
import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseManager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	
	EditText phone_field, pw_field;
	CheckBox remember_me;
	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	public static final String LoginTag = "Login";
	private MeetingPlannerDatabaseManager db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        phone_field = (EditText) findViewById(R.id.phone);
        pw_field = (EditText) findViewById(R.id.pw);
        remember_me = (CheckBox) findViewById(R.id.rememberme);
        
        //check to see if user is already logged in or not
        
    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
    	int uid = settings.getInt("uid", -1);
    	boolean remember = settings.getBoolean("remember", false);
    	if (uid != -1 && remember){ // if logged in, then go directly to main page
            Intent intent = new Intent(Login.this, MainPage.class);
        	Login.this.startActivity(intent);    		
    	}
    	
        // Hook up with database
	    db = new MeetingPlannerDatabaseManager(this, MeetingPlannerDatabaseHelper.DATABASE_VERSION);
	}

	public void checkLogin(View button) throws JSONException, ParseException{
		
		SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		
    	boolean remember = settings.getBoolean("remember", false);
    	String userPhoneNumber = settings.getString("userPhoneNumber", "invalid");
    	String userPassword = settings.getString("userPassword", "invalid");

    	// if user didn't check remember password before, set user phone number & password from user inputs
    	if(!remember || (userPhoneNumber.compareTo("invalid")==0) || (userPassword.compareTo("invalid")==0)){
    		//Toast.makeText(getBaseContext(), "userphonecomapre " + userPhoneNumber + " " +userPhoneNumber.compareTo("invalid phone number"), Toast.LENGTH_SHORT).show();
    		userPhoneNumber = phone_field.getText().toString();
        	userPassword = pw_field.getText().toString();
        	
        	if(userPhoneNumber.length() == 0 || userPassword.length() == 0){
                Toast.makeText(getBaseContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            	return;        	
            }
        	
    	}else{
    		phone_field.setText(userPhoneNumber);
    		pw_field.setText(userPassword);
    	}
		
    	long userPhoneNumberLong = Long.parseLong(userPhoneNumber);
    	int userID = Communicator.logIn(userPhoneNumberLong, userPassword);
    	boolean loginSuccess = (userID<0)?false:true;
    	
        if (!loginSuccess){ // if login does not work
            Toast.makeText(getBaseContext(), "Invalid login", Toast.LENGTH_SHORT).show();
            phone_field.setText("");
            pw_field.setText("");
            return;   
        }

    	// User successfully logged in       	
  
    	// Get meetings info & user infos from server
    	HashMap<Integer, UserInstance> usersMap = (HashMap<Integer, UserInstance>) Communicator.getAllUsers();
    	HashMap<Integer, MeetingInstance> meetingsMap = (HashMap<Integer, MeetingInstance>) Communicator.getAllMeetings();
    	
    	/****** Update internal db ******/
    	
    	// Open db connection
    	db.open();
    	
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
    	
    	 // Grab user info from internal db
        UserInstance user = db.getUser(userID);
     
        // Close db
    	db.close();
    	
        Log.v(LoginTag, "phone=" + user.getUserPhone() + "; fn=" + user.getUserFirstName() + "; ln=" + user.getUserLastName());
        
        // Saves user info into sharedpreferences
    	editor.putInt("uid", userID);
    	editor.putString("userPhoneNumber", user.getUserPhone());
    	editor.putString("userFirstName", user.getUserFirstName());
    	editor.putString("userLastName", user.getUserLastName());
    	editor.putString("userEmail", user.getUserEmail());
    	
    	// Saves user password if he/she checked remember password
        if(remember_me.isChecked()){ 
        	editor.putBoolean("remember", true);
        	editor.putString("userPassword", userPassword);
            Toast.makeText(getBaseContext(), "remembering you", Toast.LENGTH_SHORT).show();
        }else{
        	editor.putBoolean("remember", false);
        }

        // Saves the changes in sharedpreferences
    	editor.commit(); 
    	
        // no problems, go to main page
        Intent intent = new Intent(Login.this, MainPage.class);
    	Login.this.startActivity(intent);
	}

	public void forgotPass(View button){
        Toast.makeText(getBaseContext(), "You forgot your password", Toast.LENGTH_SHORT).show();
	}
	
	public void signup(View button){ // go to signup page
    	Intent intent = new Intent(Login.this, Signup.class);
    	Login.this.startActivity(intent);		
	}
	
	@Override
	public void onBackPressed(){
		moveTaskToBack(true);
		return;
	}
}
