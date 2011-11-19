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
    private List<Tracker> trackerList;
    
	public TrackerAdapter(Context context, List<Tracker> trackerList) {
		super();
		this.context = context;
		this.trackerList = trackerList;
	}

	@Override
	public int getCount() {
		return trackerList.size();
	}

	@Override
	public Object getItem(int position) {
		return trackerList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tracker entry = trackerList.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.tracker_list_item, null);
        }
        
        TextView tvTrackerName = (TextView) convertView.findViewById(R.id.trackername);
        tvTrackerName.setText(entry.getName());
        
        TextView tvEta = (TextView) convertView.findViewById(R.id.eta);
        tvEta.setText(entry.getEta());
        tvEta.setTextColor(Color.RED);
                
        return convertView;
	}
}
