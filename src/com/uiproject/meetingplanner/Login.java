package com.uiproject.meetingplanner;

import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseManager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	
	EditText phone_field, pw_field;
	CheckBox remember_me;
	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	private MeetingPlannerDatabaseManager db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        phone_field = (EditText) findViewById(R.id.phone);
        pw_field = (EditText) findViewById(R.id.pw);
        remember_me = (CheckBox) findViewById(R.id.rememberme);
        
        //check to see if user is already logged in or not
        /*
    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
    	int uid = settings.getInt("uid", -1);
    	boolean remember = settings.getBoolean("remember", false);
    	if (uid != -1 && remember){ // if logged in, then go directly to main page
            Intent intent = new Intent(Login.this, MainPage.class);
        	Login.this.startActivity(intent);    		
    	}else{
    		Toast.makeText(getBaseContext(), "your login is not currently remembered", Toast.LENGTH_SHORT).show();		
    	}
*/        
        // Hook up with database
	    db = new MeetingPlannerDatabaseManager(this, 2);
	    db.open();
	}

	public void checkLogin(View button){
		
		SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		
    	boolean remember = settings.getBoolean("remember", false);
    	String userPhoneNumber = settings.getString("userPhoneNumber", "invalid phone number");
    	String userPassword = settings.getString("userPassword", "invalid password");
    	
    	// if user didn't check remember password before, set user phone number & password from user inputs
    	if(!remember){
    		userPhoneNumber = phone_field.getText().toString();
        	userPassword = pw_field.getText().toString();
        	
        	if(userPhoneNumber.length() == 0 || userPassword.length() == 0){
                Toast.makeText(getBaseContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            	return;        	
            }
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
    	
        // Grab user info from internal db
        UserInstance user = db.getUser(userID);
        String tests = "phone=" + user.getUserPhone() + "; fn=" + user.getUserFirstName() + "; ln=" + user.getUserLastName();
        Toast.makeText(getBaseContext(), tests, Toast.LENGTH_SHORT).show();
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
}
