package com.uiproject.meetingplanner;

import java.util.List;

import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseHelper;
import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseManager;



import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class MeetingAdapter extends BaseAdapter{
	private Context context;
    private List<MeetingInstance> listMeeting;
    private MeetingPlannerDatabaseManager db;
    
	public MeetingAdapter(Context context, List<MeetingInstance> listMeeting) {
		super();
		this.context = context;
		this.listMeeting = listMeeting;
		this.db = new MeetingPlannerDatabaseManager(context, MeetingPlannerDatabaseHelper.DATABASE_VERSION);
	}

	@Override
	public int getCount() {
		return listMeeting.size();
	}

	@Override
	public Object getItem(int position) {
		return listMeeting.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		db.open();
		MeetingInstance entry = listMeeting.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem, null);
        }
        TextView tvMeetingTitle = (TextView) convertView.findViewById(R.id.meetingTitle);
        tvMeetingTitle.setText(entry.getMeetingTitle());

        TextView tvTime = (TextView) convertView.findViewById(R.id.time);
        tvTime.setText(entry.getMeetingDate()+" "+entry.getMeetingStartTime());

        TextView tvCreator = (TextView) convertView.findViewById(R.id.creator);
        UserInstance user = db.getUser(entry.getMeetingID());
        tvCreator.setText(user.getUserFirstName()+" "+user.getUserLastName());     
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
        db.close();
        checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				// TODO Auto-generated method stub
				CustomListActivity.showBar();
				Log.d("check","CHECK STATUS CHANGED");
			}
		});
        return convertView;
	}



    private void showDialog(MeetingInstance entry) {
        // Create and show your dialog
        // Depending on the Dialogs button clicks delete it or do nothing
    }


}
