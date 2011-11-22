package com.uiproject.meetingplanner;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TrackerAdapter extends BaseAdapter {
	private Context context;
    private List<UserInstance> trackerList;
    private List<UserInstance> meetingUsers;
    
	public TrackerAdapter(Context context, List<UserInstance> trackerList, List<UserInstance> meetingUsers) {
		super();
		this.context = context;
		this.trackerList = trackerList;
		this.meetingUsers = meetingUsers;
	}

	
	public int getCount() {
		return trackerList.size();
	}

	public Object getItem(int position) {
		return trackerList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		UserInstance entry = trackerList.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.tracker_list_item, null);
        }
        
        TextView tvTrackerName = (TextView) convertView.findViewById(R.id.trackername);
        for (int i = 0; i < meetingUsers.size(); i++) {
        	if (entry.getUserID() == meetingUsers.get(i).getUserID()) {
        		tvTrackerName.setText(meetingUsers.get(i).getUserFirstName() + " " + meetingUsers.get(i).getUserLastName());
        	}
        }
        	
        TextView tvEta = (TextView) convertView.findViewById(R.id.eta);
        tvEta.setText(entry.getUserEta());
        tvEta.setTextColor(Color.RED);
                
        return convertView;
	}
}
