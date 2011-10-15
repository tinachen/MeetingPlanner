package com.uiproject.meetingplanner; 

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MeetingListArrayAdapter extends ArrayAdapter<MeetingInstance> {

	private final List<MeetingInstance> list;
	private final Activity context;

	public MeetingListArrayAdapter(Activity context, List<MeetingInstance> list) {
		super(context, R.layout.meetinglist, list);
		this.context = context;
		this.list = list;
	}

	static class ViewHolder {
		protected TextView meetingName;
		protected Button acceptBtn;
		protected Button declineBtn;
		
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.meetinglist, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.meetingName = (TextView) view.findViewById(R.id.meetingName);
			viewHolder.acceptBtn = (Button) view.findViewById(R.id.acceptBtn);
			viewHolder.declineBtn = (Button) view.findViewById(R.id.declineBtn);
			
			viewHolder.acceptBtn.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	                 // Perform action on click
	            	 MeetingInstance meeting = (MeetingInstance) viewHolder.acceptBtn.getTag();
	            	 
	            	 // For testing
	            	 new AlertDialog.Builder(context)
	         	      .setMessage("accept " + meeting.getMeetingSubject())
	         	      .setTitle("Meeting")
	         	      .setCancelable(true)
	         	      .show();
	             }});
			
			viewHolder.declineBtn.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	                 // Perform action on click
	            	 MeetingInstance meeting = (MeetingInstance) viewHolder.acceptBtn.getTag();
	            	 
	            	// For testing
	         		new AlertDialog.Builder(context)
	         	      .setMessage("decline " + meeting.getMeetingSubject())
	         	      .setTitle("Meeting")
	         	      .setCancelable(true)
	         	      .show();
	         		
	         		// Remove the declined one from meeting list
	         		remove(meeting);
	         		notifyDataSetChanged();
	         		
	             }});
			
			view.setTag(viewHolder);
			viewHolder.acceptBtn.setTag(list.get(position));
			viewHolder.declineBtn.setTag(list.get(position));
			
		} else {
			view = convertView;
			((ViewHolder) view.getTag()).acceptBtn.setTag(list.get(position));
			((ViewHolder) view.getTag()).declineBtn.setTag(list.get(position));
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.meetingName.setText(list.get(position).getMeetingSubject());
		return view;
	}
}