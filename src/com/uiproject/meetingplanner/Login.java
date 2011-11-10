package com.uiproject.meetingplanner;

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
	@Override
	protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
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
        phone_field = (EditText) findViewById(R.id.phone);
        pw_field = (EditText) findViewById(R.id.pw);
        remember_me = (CheckBox) findViewById(R.id.rememberme);
        
	}

	public void checkLogin(View button){
		String user = phone_field.getText().toString();
		String pass = pw_field.getText().toString();
		

        if(user.length() == 0 || pass.length() == 0){
            Toast.makeText(getBaseContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
        	return;        	
        }
        

        //query db and check if login is correct
        int uid = 1; //TODO CHANGE THIS TO A REAL ONE!!
        
        boolean error = false; // TODO CHANGE THIS WHEN CHECKING LOGIN
        
        if (error){ // if login does not work
            Toast.makeText(getBaseContext(), "Invalid login", Toast.LENGTH_SHORT).show();
            phone_field.setText("");
            pw_field.setText("");
            return;
        }

    	SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); 
    	SharedPreferences.Editor editor = settings.edit();
    	editor.putInt("uid", uid);
    	
        if(remember_me.isChecked()){ // if they want their login to be remembered
        	editor.putBoolean("remember", true);
            Toast.makeText(getBaseContext(), "remembering you", Toast.LENGTH_SHORT).show();
        }

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
