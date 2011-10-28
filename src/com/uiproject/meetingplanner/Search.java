package com.uiproject.meetingplanner;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.util.Log;

public class Search extends Activity implements OnItemClickListener {
	private final String LOG_TAG = "Search";
    public static final String TAG = "ContactManager";

    private ListView mContactList;
    private boolean mShowInvisible;
	
    /** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.search);

	    AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_names);
	    String[] search_contacts = getResources().getStringArray(R.array.search_contacts);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, search_contacts);
	    textView.setAdapter(adapter);
	    textView.setOnItemClickListener(this);
	    /*textView.setOnItemClickListener(new OnItemClickListenter() {
	    	public void onItemClick 
	    	
	    })*/
	    mContactList = (ListView) findViewById(R.id.contactList);
	    populateContactList();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(LOG_TAG, "onItemSelected() " + parent.getItemAtPosition(position));
    }
	
    /**
     * Populate the contact list based on account currently selected in the account spinner.
     */
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

    /**
     * Obtains the contact list for the currently selected account.
     *
     * @return A cursor for for accessing the contact list.
     */
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
}