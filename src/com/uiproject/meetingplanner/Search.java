package com.uiproject.meetingplanner;

import java.util.ArrayList;
import java.util.Vector;

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
import android.widget.ListView;
import android.util.Log;
import android.util.SparseBooleanArray;

public class Search extends Activity implements OnItemClickListener {
    public static final String LOG_TAG = "Search";

    protected ListView mContactList;
    protected AutoCompleteTextView textView;
    protected ArrayAdapter<String> adapter;
    protected Vector<String> currentSearchList;
    protected Vector<String> contactList;
    protected ArrayList<String> checkedNames;
    protected ArrayList<String> checkedPhoneNumbers;

    /** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    //setContentView(R.layout.search);
	    
	    currentSearchList = new Vector<String>();
	    contactList = new Vector<String>();
	    checkedNames = new ArrayList<String>();
	    checkedPhoneNumbers = new ArrayList<String>();
	}
	
	public void init() {
	    // Gets the contact list and saves it into contactList
	    ContentResolver cr = getContentResolver();
	    Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
	    if (cursor.getCount() > 0) {
	    	while (cursor.moveToNext()) {
	    		// Get name from contact list
	    	    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
	    	    currentSearchList.add(name);
	    	    contactList.add(name);
	    	    Log.d("Contact List", name);
	    	    
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
	    	        	checkedPhoneNumbers.add(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
	    	        }
	    	        phones.close();
	    	    }
	    	}
	    }
	    
	    // Sets up the adapter for AutoCompleteTextView
	    textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_names);
	    //String[] search_contacts = getResources().getStringArray(R.array.search_contacts);
	    adapter = new ArrayAdapter<String>(this, R.layout.list_item, contactList);
	    textView.setAdapter(adapter);
	    textView.setThreshold(1);
	    textView.setDropDownHeight(0);
	    
	    // Sets the current contact list 
	    mContactList = (ListView) findViewById(R.id.contactList);
        ArrayAdapter<String> searchUpdateAdapter = new ArrayAdapter<String>(Search.this, R.layout.list_item, currentSearchList);
        mContactList.setAdapter(searchUpdateAdapter);
	    mContactList.setOnItemClickListener(this);
	    mContactList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	    //mContactList.setSelector(R.layout.rowselector);
	    
	    adapter.registerDataSetObserver(new DataSetObserver() {
	    	// Updates the current search list
	        @Override
	        public void onChanged() {
	            super.onChanged();
	            Log.d(LOG_TAG, "dataset changed");
	            currentSearchList.clear();
	            for (int i = 0; i < adapter.getCount(); i++) {
		            Object item = adapter.getItem(i);
		            currentSearchList.add(item.toString());
		            	if (checkedNames.contains(item)) {
		            		Log.d("Search checkedNames", item.toString());
		            		mContactList.setItemChecked(i, true);
		            	}
		            
		            Log.d(LOG_TAG, "item.toString "+ item.toString());
	            }
	            ArrayAdapter<String> searchUpdateAdapter = new ArrayAdapter<String>(Search.this, R.layout.list_item, currentSearchList);
	            mContactList.setAdapter(searchUpdateAdapter);
	            for (int i =  0; i < currentSearchList.size(); i++) {
	            	if (checkedNames.contains(currentSearchList.get(i))) {
	            		mContactList.setItemChecked(i, true);
	            	}
	            }
	        }
	        
	        // Clears the search list when input is not in the list
	        @Override
	        public void onInvalidated() {
	        	Log.d("OnInvalidated", "invalid data");
	        	currentSearchList.clear();
	            ArrayAdapter<String> searchUpdateAdapter = new ArrayAdapter<String>(Search.this, R.layout.list_item, currentSearchList);
	            mContactList.setAdapter(searchUpdateAdapter);
	        }
	    });
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// Displays the clicked name in LogCat for now
		Log.d("TEST", parent.getItemAtPosition(position).toString());
		SparseBooleanArray checked = mContactList.getCheckedItemPositions();
		checkedNames.clear();
		for (int i = 0; i < currentSearchList.size(); i++) {
			if (checked.get(i)) {
				checkedNames.add(currentSearchList.elementAt(i));
			}
			else if (!checked.get(i)) {
				checkedNames.remove(currentSearchList.elementAt(i));
			}
		}
		Log.d("checkedNames", checkedNames.toString());
		//view.setSelected(true);
	}
	
	public void recheck() {
		for (int i = 0; i < currentSearchList.size(); i++) {
			if (checkedNames.contains(currentSearchList.elementAt(i))) {
				Log.d("TEST", currentSearchList.elementAt(i));
				mContactList.setItemChecked(i, true);
			}
		}
	}
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
