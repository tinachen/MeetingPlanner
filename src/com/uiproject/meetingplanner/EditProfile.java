package com.uiproject.meetingplanner;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfile extends Activity {

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
        
        //get info from db
        fname = "Elizabeth";
        lname = "Deng";
        phone = "8473221831";
        email = "elizabethdeng@gmail.com";
        
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
        
        if (!fname.equals(this.fname)){
        	//change first name
        }
        
        if (!lname.equals(this.lname)){
        	//change last name
        }

        if (!phone.equals(this.phone)){
        	//change phone
        }

        if (!email.equals(this.email)){
        	//change email
        }
        
        if (oldpw.length() != 0 && pw.length() != 0 && pw2.length() != 0){ // if they want to change password
	        // query db and get current pw and check
        	if(!pw.equals(pw2)){
	            Toast.makeText(getBaseContext(), "New passwords do not match", Toast.LENGTH_SHORT).show();
	            return;
	        	
	        }else{
	            Toast.makeText(getBaseContext(), "Password has changed", Toast.LENGTH_SHORT).show();
	        	// change pw in db
	        	
	        }
        }
        
    	Intent intent = new Intent(EditProfile.this, MainPage.class);
    	EditProfile.this.startActivity(intent);
	}

}
