package com.uiproject.meetingplanner; 

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MeetingListArrayAdapter extends ArrayAdapter<MeetingInstance> {

	private final List<MeetingInstance> list;
	private final Activity context;
	
	public MeetingListArrayAdapter(Activity context, List<MeetingInstance> list) {
		super(context, R.layout.meetingitems, list);
		this.context = context;
		this.list = list;
	}

	static class ViewHolder {
		protected TextView meetingName;
		protected TextView meetingDesc;
		protected Button acceptBtn;
		protected Button declineBtn;
		
	}

	/*
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.meetinglistpending, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.meetingName = (TextView) view.findViewById(R.id.meetingName);
			viewHolder.meetingDesc = (TextView) view.findViewById(R.id.meetingDesc);
			viewHolder.acceptBtn = (Button) view.findViewById(R.id.acceptBtn);
			viewHolder.declineBtn = (Button) view.findViewById(R.id.declineBtn);
			
			viewHolder.acceptBtn.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	                 // Perform action on click
	            	 MeetingInstance meeting = (MeetingInstance) viewHolder.acceptBtn.getTag();
	            	 
	            	 // For testing
	            	 new AlertDialog.Builder(context)
	         	      .setMessage("accept " + meeting.getMeetingTitle())
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
	         	      .setMessage("decline " + meeting.getMeetingTitle())
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
		holder.meetingName.setText(list.get(position).getMeetingTitle());
		holder.meetingDesc.setText("\n\n\n" + list.get(position).getMeetingAddress() + "\t" +
				list.get(position).getMeetingDate() + "\t" +  list.get(position).getMeetingStartTime() + " to " + list.get(position).getMeetingEndTime());
		return view;
	}
	*/
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public MeetingInstance getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MeetingInstance entry = list.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem, null);
        }
        TextView tvMeetingTitle = (TextView) convertView.findViewById(R.id.meetingTitle);
        tvMeetingTitle.setText(entry.getMeetingTitle());

        TextView tvTime = (TextView) convertView.findViewById(R.id.time);
        tvTime.setText(entry.getMeetingDate()+" "+entry.getMeetingStartTime());

        TextView tvCreator = (TextView) convertView.findViewById(R.id.creator);
        tvCreator.setText("FixMe");        
        
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1); 
        cb.setOnCheckedChangeListener(new OnCheckedChangeListener() { 
 
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
        // TODO Auto-generated method stub 
        if (buttonView.isChecked()) { 
        	//Toast.makeText(getBaseContext(), "Checked", Toast.LENGTH_SHORT).show(); 
        } 
        else 
        { 
        	//Toast.makeText(getBaseContext(), "UnChecked", Toast.LENGTH_SHORT).show(); 
        } 

        } 
        }); 
        
        return convertView;
	}
}