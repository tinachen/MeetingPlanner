package com.uiproject.meetingplanner;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfile extends Activity {
	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";

	EditText fname_field, lname_field, phone_field, email_field, oldpw_field, pw_field, pw2_field;
	String fname, lname, phone, email;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);
        
        fname_field = (EditText) findViewById(R.id.fname);
        lname_field = (EditText) findViewById(R.id.lname);
        phone_field = (EditText) findViewById(R.id.phone);
        email_field = (EditText) findViewById(R.id.email);
        oldpw_field = (EditText) findViewById(R.id.oldpw);
        pw_field = (EditText) findViewById(R.id.pw);
        pw2_field = (EditText) findViewById(R.id.pw2);
		SharedPreferences settings = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
    	
        //get info from db
        fname = settings.getString("userFirstName", "");
        lname = settings.getString("userLastName", "");
        phone = settings.getString("userPhoneNumber", "");
        email = settings.getString("userEmail", "");
        
        // set fields
        fname_field.setText(fname);
        lname_field.setText(lname);
        phone_field.setText(phone);
        email_field.setText(email);
        
	}

	public void submit(View button){
        
        String fname = fname_field.getText().toString();
        String lname = lname_field.getText().toString();
        String phone = phone_field.getText().toString();
        String email = email_field.getText().toString();
        String oldpw = pw_field.getText().toString();
        String pw = pw_field.getText().toString();
        String pw2 = pw2_field.getText().toString();
        
        if (fname.length() == 0 || lname.length() == 0 || phone.length() == 0 || email.length() == 0){
            Toast.makeText(getBaseContext(), "Please fill in all fields (name, phone, email)", Toast.LENGTH_SHORT).show();
        }
        
        boolean pwchanged = false;
        if (oldpw.length() != 0 && pw.length() != 0 && pw2.length() != 0){ // if they want to change password
	        // query db and get current pw and check
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
		
		SharedPreferences.Editor editor = settings.edit();
    	editor.putString("userPhoneNumber", phone);
    	editor.putString("userFirstName", fname);
    	editor.putString("userLastName", lname);
    	editor.putString("userEmail", email);
    	editor.commit();
    	
        Communicator.updateUser(uid, Long.parseLong(phone), fname, lname, email, pw);

        EditProfile.this.setResult(R.string.edited_profile);
        EditProfile.this.finish();
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
