package com.uiproject.meetingplanner;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AutoCompleteAdapter extends ArrayAdapter<UserInstance> {
	private Context context;
    private List<UserInstance> userList;
    private ArrayAdapter<String> mAdapter;
    private View searchView;
    
	public AutoCompleteAdapter(Context context, List<UserInstance> userList) {
		super(context, R.layout.search, userList);
		this.context = context;
		this.userList = userList;
	}

	
	public int getCount() {
		return userList.size();
	}

	public UserInstance getItem(int position) {
		return userList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		UserInstance entry = userList.get(position);
		
		if(position == 0) {
			LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Log.d("AutoCompleteAdapter", "Making AutoCompleteTextView");
            searchView = inflater.inflate(R.layout.search, null);
            searchView = (AutoCompleteTextView)convertView.findViewById(R.id.autocomplete_names);
            mAdapter = new ArrayAdapter<String>(this.context, android.R.layout.simple_dropdown_item_1line, this.getUserInstanceNames(this.userList));
            ((AutoCompleteTextView) searchView).setAdapter(mAdapter);
            //((AutoCompleteTextView)searchView.findViewById(R.id.autocomplete_names)).setAdapter(mAdapter);
            searchView.setFocusable(true);
            return searchView;
        }
		
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.search, null);
        }
     
        //AutoCompleteTextView tvSearchName = (AutoCompleteTextView) convertView.findViewById(R.id.autocomplete_search);
        //tvSearchName.setText(entry.getUserFirstName() + " " + entry.getUserLastName());
        
        return convertView;
	}
	
	private ArrayList<String> getUserInstanceNames(List<UserInstance> userList2) {
		ArrayList<String> names = new ArrayList<String>();
		for (int i = 0; i < userList2.size(); i++) {
			names.add(userList2.get(i).getUserFirstName() + " " + userList2.get(i).getUserLastName());
		}
		return names;
	}
}
