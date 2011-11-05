package com.uiproject.meetingplanner;

import java.util.Vector;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.util.Log;

public class Search extends Activity {
	private final String LOG_TAG = "Search";
    public static final String TAG = "ContactManager";

    private ListView mContactList;
    private AutoCompleteTextView textView;
    private ArrayAdapter<String> adapter;
    private Vector<String> currentSearchList;
    private Vector<String> contactList;
	
    /** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.search);
	    
	    currentSearchList = new Vector<String>();
	    contactList = new Vector<String>();

	    // Gets the contact list and saves it into contactList
	    ContentResolver cr = getContentResolver();
	    Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
	    if (cursor.getCount() > 0) {
	    	while (cursor.moveToNext()) {
	    	    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
	    	    currentSearchList.add(name);
	    	    contactList.add(name);
	    	    Log.d("Contact List", name);
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
	    
	    adapter.registerDataSetObserver(new DataSetObserver() {
	        @Override
	        public void onChanged() {
	            super.onChanged();
	            Log.d(TAG, "dataset changed");
	            currentSearchList.clear();
	            for (int i = 0; i < adapter.getCount(); i++) {
		            Object item = adapter.getItem(i);
		            currentSearchList.add(item.toString());
		            Log.d(TAG, "item.toString "+ item.toString());
	            }
	            ArrayAdapter<String> searchUpdateAdapter = new ArrayAdapter<String>(Search.this, R.layout.list_item, currentSearchList);
	            mContactList.setAdapter(searchUpdateAdapter);
	        }
	    });
	    textView.dismissDropDown();
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