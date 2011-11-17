package com.uiproject.meetingplanner;

import java.text.ParseException;
import java.util.HashMap;

import org.json.JSONException;

import com.facebook.android.*;
import com.facebook.android.Facebook.*;
import com.uiproject.meetingplanner.database.*;

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
	//Facebook facebook = new Facebook("294864470534799");  // FOR FACEBOOK INTEGRATION
	
	@Override
	protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        // FOR FACEBOOK INTEGRATION
        /*facebook.authorize(this, new DialogListener() {
            @Override
            public void onComplete(Bundle values) {}

            @Override
            public void onFacebookError(FacebookError error) {}

            @Override
            public void onError(DialogError e) {}

            @Override
            public void onCancel() {}
        });*/
        
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

	// FOR FACEBOOK INTEGRATION
	/*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebook.authorizeCallback(requestCode, resultCode, data);
    }*/
	

	public void checkLogin(View button) throws JSONException, ParseException, NumberFormatException{
		
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
  
        /****** Update internal db ******/
        
        // Open db connection
    	db.open();
    	
    	// Get meetings info & user infos from server and update internal db
    	MeetingPlannerDatabaseUtility.updateDatabase(db);
    	
    	 // Grab user info from internal db
        UserInstance user = db.getUser(userID);
     
        // Close db 
    	db.close();
    	
        Log.d(LoginTag, "phone=" + user.getUserPhone() + "; fn=" + user.getUserFirstName() + "; ln=" + user.getUserLastName());
        
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
            //Toast.makeText(getBaseContext(), "remembering you", Toast.LENGTH_SHORT).show();
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
