package com.uiproject.meetingplanner;

import java.util.ArrayList;
import java.util.Vector;

import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseHelper;
import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseManager;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.util.Log;

public class Search extends Activity implements OnItemClickListener {
    public static final String LOG_TAG = "Search";

    protected GridView mContactList;
    protected AutoCompleteTextView textView;
    protected ArrayAdapter<String> adapter;
    protected Vector<String> currentSearchList;
    protected Vector<String> contactList;
    protected ArrayList<UserInstance> usersArray;
    private MeetingPlannerDatabaseManager db;
    protected ArrayList<UserInstance> fullSearchList;
    protected ArrayList<UserInstance> currSearchList;
    protected ArrayList<UserInstance> checkedUsers;

    /** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    //setContentView(R.layout.search);
	    
	    db = new MeetingPlannerDatabaseManager(this, MeetingPlannerDatabaseHelper.DATABASE_VERSION);
	    currentSearchList = new Vector<String>();
	    contactList = new Vector<String>();
	    usersArray = new ArrayList<UserInstance>();
	    fullSearchList = new ArrayList<UserInstance>();
	    currSearchList = new ArrayList<UserInstance>();
	    checkedUsers = new ArrayList<UserInstance>();
	}
	
	public void init() {
	    db.open();
	    usersArray = db.getAllUsers();
	    db.close();
		
		//usersArray.add(new UserInstance(1, "Cauchy", "Choi", "cauchych@usc.edu",
		//		"4084991666", 2, 3));
		//usersArray.add(new UserInstance(2, "Tina", "Chen", "tinac@usc.edu", "6263761106", 2, 3));
		
	    // Gets the contact list and saves it into contactList
	    ContentResolver cr = getContentResolver();
	    Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
	    if (cursor.getCount() > 0) {
	    	while (cursor.moveToNext()) {
	    		// Get name from contact list
	    	    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		    	//currentSearchList.add(name);
		    	contactList.add(name);
		    	//Log.d("Contact List", name);
	    	    
	    	    // Get phone number from contact list
	    	    String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
	    	    String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

	    	    if ( hasPhone.equalsIgnoreCase("1"))
	    	    	hasPhone = "true";
	    	    else
	    	    	hasPhone = "false" ;

	    	    if (Boolean.parseBoolean(hasPhone)) {
	    	    	Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,null, null);
	    	        while (phones.moveToNext()) {
	    	        	String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("\\D", "");
	    	        	//Log.d("TEST", phoneNumber);
	    	        	for (int i = 0; i < usersArray.size(); i++) {
	    	        		//Log.d("TEST1", phones.getString(phones.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
		    	        	if (usersArray.get(i).getUserPhone().equals(phoneNumber)) {
		    	        		currentSearchList.add(phones.getString(phones.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
		    	        		fullSearchList.add(usersArray.get(i));
		    	        	}
	    	        	}
	    	        }
	    	        phones.close();
	    	    }
	    	}
	    }
	    
	    // Sets up the adapter for AutoCompleteTextView
	    textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_names);
	    adapter = new ArrayAdapter<String>(this, R.layout.search, currentSearchList);
	    textView.setAdapter(adapter);
	    textView.setThreshold(1);
	    textView.setDropDownHeight(0);
	    
	    // Sets the current contact list 
        mContactList = (GridView) findViewById(R.id.gridview);
        UserAdapter uAdapter = new UserAdapter(this, fullSearchList, checkedUsers);
        mContactList.setAdapter(uAdapter);
        mContactList.setFocusable(true);
        mContactList.setClickable(true);
        mContactList.setOnItemClickListener(this);
        

        /*mContactList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	Log.d("itemClick", "item clicked");
            	if (!checkedUsers.contains(parent.getItemAtPosition(position))) {
        			checkedUsers.add((UserInstance)parent.getItemAtPosition(position));
        		}
        		else {
        			checkedUsers.remove((UserInstance)parent.getItemAtPosition(position));
        		}
        		for (int i = 0; i < checkedUsers.size(); i++) {
        			Log.d("checkedUsers", checkedUsers.get(i).getUserFirstName() + " " + checkedUsers.get(i).getUserLastName());
        		}
                Toast.makeText(Search.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });*/
        
        adapter.registerDataSetObserver(new DataSetObserver() {
        	@Override
        	public void onChanged() {
        		currSearchList.clear();
        		currentSearchList.clear();
        		for (int i = 0; i < fullSearchList.size(); i++) {
        			if (textView.getText().toString().toLowerCase().equals(fullSearchList.get(i).getUserFirstName().substring(0,textView.getText().toString().length()).toLowerCase()) || textView.getText().toString().toLowerCase().equals(fullSearchList.get(i).getUserLastName().substring(0,textView.getText().toString().length()).toLowerCase())) {
        				currSearchList.add(fullSearchList.get(i));
        				Log.d("TEST", fullSearchList.get(i).getUserFirstName() + " " + fullSearchList.get(i).getUserLastName());
        			}
        		}
        		for (int i = 0; i < adapter.getCount(); i++) {
		            Object item = adapter.getItem(i);
		            currentSearchList.add(item.toString());
        		}
                UserAdapter uAdapter = new UserAdapter(Search.this, currSearchList, checkedUsers);
                mContactList.setAdapter(uAdapter);
        	}
            
        	@Override
	        public void onInvalidated() {
	        	Log.d("OnInvalidated", "invalid data");
	        	currentSearchList.clear();
	        	currSearchList.clear();
	        	
                UserAdapter uAdapter = new UserAdapter(Search.this, currSearchList, checkedUsers);
                mContactList.setAdapter(uAdapter);
	        }
        });
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// Displays the clicked name in LogCat for now
		Log.d("TEST", parent.getItemAtPosition(position).toString());
		if (!checkedUsers.contains(parent.getItemAtPosition(position))) {
			checkedUsers.add((UserInstance)parent.getItemAtPosition(position));
		}
		else {
			checkedUsers.remove((UserInstance)parent.getItemAtPosition(position));
		}
		for (int i = 0; i < checkedUsers.size(); i++) {
			Log.d("checkedUsers", checkedUsers.get(i).getUserFirstName() + " " + checkedUsers.get(i).getUserLastName());
		}
	}
	
	/*public void recheck() {
		for (int i = 0; i < currentSearchList.size(); i++) {
			if (checkedNames.contains(currentSearchList.elementAt(i))) {
				Log.d("TEST", currentSearchList.elementAt(i));
				mContactList.setItemChecked(i, true);
			}
		}
	}*/
	
    /**
     * Populate the contact list based on account currently selected in the account spinner.
     */
	/*
    private void populateContactList() {
        // Build adapter with contact entries
        Cursor cursor = getContacts();
        String[] fields = new String[] {
                ContactsContract.Data.DISPLAY_NAME
        };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.contact_entry, cursor,
                fields, new int[] {R.id.contactEntryText});
    	Log.d(LOG_TAG, "populateContactList");
        mContactList.setAdapter(adapter);
    }
*/
    /**
     * Obtains the contact list for the currently selected account.
     *
     * @return A cursor for for accessing the contact list.
     */
	/*
    private Cursor getContacts()
    {
        // Run query
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        
        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '" +
                (mShowInvisible ? "0" : "1") + "'";
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
        return managedQuery(uri, projection, selection, selectionArgs, sortOrder);
    }
    */
}
