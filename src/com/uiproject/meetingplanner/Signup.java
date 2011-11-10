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

public class Signup extends Activity {
	EditText fname_field, lname_field, phone_field, email_field, pw_field, pw2_field;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        
        fname_field = (EditText) findViewById(R.id.fname);
        lname_field = (EditText) findViewById(R.id.lname);
        phone_field = (EditText) findViewById(R.id.phone);
        email_field = (EditText) findViewById(R.id.email);
        pw_field = (EditText) findViewById(R.id.pw);
        pw2_field = (EditText) findViewById(R.id.pw2);
        
        //get info from phone and input it into the fields here
        
        AccountManager accountManager = AccountManager.get(this); 
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0) {
          account = accounts[0];   
          String email = account.name;
          email_field.setText(email);
          
        }
        
        TelephonyManager tMgr =(TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String phone = tMgr.getLine1Number();
        phone_field.setText(phone);
	}

	public void submit(View button){

        Toast.makeText(getBaseContext(), "You pressed the submit button!", Toast.LENGTH_SHORT).show();

        //error checking here
        
        String fname = fname_field.getText().toString();
        String lname = lname_field.getText().toString();
        String phone = phone_field.getText().toString();
        String email = email_field.getText().toString();
        String pw = pw_field.getText().toString();
        String pw2 = pw2_field.getText().toString();
        
        if (fname.length() == 0 || lname.length() == 0 || phone.length() == 0 || 
        		email.length() == 0 || pw.length() == 0 || pw2.length() == 0){
        	

            Toast.makeText(getBaseContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if(!pw.equals(pw2)){
            Toast.makeText(getBaseContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        	
        }
    	Intent intent = new Intent(Signup.this, MainPage.class);
    	Signup.this.startActivity(intent);
	}

}
