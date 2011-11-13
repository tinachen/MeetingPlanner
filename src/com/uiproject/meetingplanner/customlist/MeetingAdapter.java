package com.uiproject.meetingplanner.customlist;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MeetingAdapter extends BaseAdapter{
	private Context context;
    private List<Meeting> listMeeting;
    
	public MeetingAdapter(Context context, List<Meeting> listMeeting) {
		super();
		this.context = context;
		this.listMeeting = listMeeting;
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
		Meeting entry = listMeeting.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem, null);
        }
        TextView tvMeetingTitle = (TextView) convertView.findViewById(R.id.meetingTitle);
        tvMeetingTitle.setText(entry.getTitle());

        TextView tvTime = (TextView) convertView.findViewById(R.id.time);
        tvTime.setText(entry.getDate()+" "+entry.getTime());

        TextView tvCreator = (TextView) convertView.findViewById(R.id.creator);
        tvCreator.setText(entry.getCreator());        
        return convertView;
	}



    private void showDialog(Meeting entry) {
        // Create and show your dialog
        // Depending on the Dialogs button clicks delete it or do nothing
    }


}
