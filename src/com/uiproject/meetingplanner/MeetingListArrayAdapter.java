package com.uiproject.meetingplanner; 

import java.util.List;

import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseHelper;
import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MeetingListArrayAdapter extends ArrayAdapter<MeetingInstance> {

	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	public final static String TAG = "MeetingListArrayAdapter";
	
	private final List<MeetingInstance> list;
	private final Activity context;
	private int listType;
	private int uid;
	private MeetingPlannerDatabaseManager db;
	
    public static int LISTTYPE_ACCEPTED = 0;
    public static int LISTTYPE_DECLINED = 1;
    public static int LISTTYPE_PENDING = 2;
    public static int LISTTYPE_OLD = 3;
   
	
	public MeetingListArrayAdapter(Activity context, int resourceID, List<MeetingInstance> list, int listType, int uid) {
		super(context, resourceID, list);
		this.context = context;
		this.list = list;
		this.listType = listType;
		this.uid = uid;
		
		db = new MeetingPlannerDatabaseManager(context, MeetingPlannerDatabaseHelper.DATABASE_VERSION);
	}

	static class ViewHolder {
		protected TextView meetingTitle;
		protected TextView meetingInitiatorName;
		protected TextView meetingTime;
		protected CheckBox checkbox;
		protected ImageView itemIcon;
	}

	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent){
		View view = null;
		MeetingInstance m = list.get(position);
		
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.all_list_item, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.meetingTitle = (TextView) view.findViewById(R.id.meetingTitle);
			viewHolder.meetingInitiatorName = (TextView) view.findViewById(R.id.meetingInitiatorName);
			viewHolder.meetingTime = (TextView) view.findViewById(R.id.meetingTime);
			viewHolder.checkbox = (CheckBox) view.findViewById(R.id.checkbox);
			viewHolder.itemIcon = (ImageView) view.findViewById(R.id.list_icon);
			
			// Make check buttons invisible if the meetings are created by the user
			// Change calling icon to edit button
			viewHolder.itemIcon.setClickable(true);
			
			
			viewHolder.itemIcon.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	MeetingInstance meeting = (MeetingInstance) viewHolder.itemIcon.getTag();
	            	if(meeting.getMeetingImageResourceID() == R.drawable.edit_meeting){
	            		// Display edit meeting page
	            		
	            		// ELIZABETH'S CODE GOES HERE - TODO
	            		int mid = meeting.getMeetingID();
	            		
	            	}else{
	            		// Call the initiator's phone number
	            		db.open();
	            		UserInstance u = db.getUser(meeting.getMeetingInitiatorID());
	            		db.close();
	            		
	            		String phonenumber = u.getUserPhone();
	            		
	            		// MENGFEI'S CODE GOES HERE - TODO
	            		Intent intent = new Intent(Intent.ACTION_CALL);
	            		intent.setData(Uri.parse("tel:" + phonenumber));
	            		context.startActivity(intent);
	            	}
	            	
	            }});

			viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	                 // Perform action on click
	            	 MeetingInstance meeting = (MeetingInstance) viewHolder.checkbox.getTag();
	            	 meeting.setSelected(buttonView.isChecked());
	            	 
	            	 if(buttonView.isChecked()){
	            		 if(listType == LISTTYPE_PENDING){
	            			 Log.d(TAG, "pending - check state change; meetingTitle = " +meeting.getMeetingTitle());
	            			 MeetingListPendingTest.showBar();
	            		 }else if(listType == LISTTYPE_ACCEPTED){
	            			 Log.d(TAG, "accepted - check state change; meetingTitle = " +meeting.getMeetingTitle());
	            			 MeetingListAcceptedTest.showBar();
	            		 }else{
	            			 Log.d(TAG, "declined - check state change; meetingTitle = " +meeting.getMeetingTitle());
	            			 MeetingListDeclinedTest.showBar();
	            		 }
	            		
	            	 }
	            	 
	             }});
			
			
			view.setTag(viewHolder);
			viewHolder.itemIcon.setTag(list.get(position));
			viewHolder.checkbox.setTag(list.get(position));
			
		} else {
			view = convertView;
			((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
		}
		
		if(uid == m.getMeetingInitiatorID() && listType == LISTTYPE_ACCEPTED){
			m.setMeetingImageResourceID(R.drawable.edit_meeting);
		}else{
			m.setMeetingImageResourceID(R.drawable.call);
		}
		
		db.open();
		UserInstance initiatorUser = db.getUser(list.get(position).getMeetingInitiatorID());
		db.close();
		
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.meetingTitle.setText(list.get(position).getMeetingTitle());
		holder.meetingInitiatorName.setText(initiatorUser.getUserFirstName() + " " + initiatorUser.getUserLastName());
		holder.meetingTime.setText(list.get(position).getMeetingDate() + " " + list.get(position).getMeetingStartTime());
		holder.checkbox.setChecked(list.get(position).isSelected());
		holder.itemIcon.setImageResource(list.get(position).getMeetingImageResourceID());
		if(uid == m.getMeetingInitiatorID() && listType == LISTTYPE_ACCEPTED){
			holder.checkbox.setVisibility(View.INVISIBLE);
		}else{
			holder.checkbox.setVisibility(View.VISIBLE);
		}
		
		return view;
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

	/*
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
	}*/
}