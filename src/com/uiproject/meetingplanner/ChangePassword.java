package com.uiproject.meetingplanner;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends Activity {
	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";

	EditText oldpw_field, pw_field, pw2_field;
	String fname, lname, phone, email;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);
        
        oldpw_field = (EditText) findViewById(R.id.oldpw);
        pw_field = (EditText) findViewById(R.id.pw);
        pw2_field = (EditText) findViewById(R.id.pw2);
    	
        
	}

	public void submit(View button){
        
        String oldpw = pw_field.getText().toString();
        String pw = pw_field.getText().toString();
        String pw2 = pw2_field.getText().toString();
        
        if (fname.length() == 0 || lname.length() == 0 || phone.length() == 0 || email.length() == 0){
            Toast.makeText(getBaseContext(), "Please fill in all fields (name, phone, email)", Toast.LENGTH_SHORT).show();
        }
        
        boolean pwchanged = false;
        if (oldpw.length() != 0 && pw.length() != 0 && pw2.length() != 0){ // if they want to change password
	        // TODO query db and get current pw and check
        	if(!pw.equals(pw2)){
	            Toast.makeText(getBaseContext(), "New passwords do not match", Toast.LENGTH_SHORT).show();
	            return;
	        	
	        }else{
	            Toast.makeText(getBaseContext(), "Password has changed", Toast.LENGTH_SHORT).show();
	            pwchanged = true;
	        }
        }
        
        if(!pwchanged){
        	pw = oldpw;
        }

		SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
		int uid = settings.getInt("uid", 0);
        //TODO Communicator.updateUser(uid, Long.parseLong(phone), fname, lname, email, pw);

        ChangePassword.this.finish();
	}
    // menu 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logoutonly, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:{
            	logout();
            	break;
            }
        }
        return true;
    }
    
    private void logout(){
    	Logout.logout(this);
    }

}
