package com.uiproject.meetingplanner;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UserAdapter extends BaseAdapter {
	private Context context;
    private List<UserInstance> userList;
    private List<UserInstance> checkedUsers;
    private UserInstance entry;
    
	public UserAdapter(Context context, List<UserInstance> userList, List<UserInstance> checkedList) {
		super();
		this.context = context;
		this.userList = userList;
		this.checkedUsers = checkedList;
	}

	
	public int getCount() {
		return userList.size();
	}

	public Object getItem(int position) {
		return userList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		entry = userList.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }
        
        ImageView ivUserImage = (ImageView) convertView.findViewById(R.id.userpic);
        // set user image here
        
        ImageView ivCircleImage = (ImageView) convertView.findViewById(R.id.selectcircle);
        if (checkedUsers.contains(entry) && ivCircleImage.getVisibility() != View.VISIBLE) {
        	Log.d("visibility", "setting visible");
        	ivCircleImage.setVisibility(View.VISIBLE);
        	ivCircleImage.setAdjustViewBounds(true);
        }
        else if (ivCircleImage.getVisibility() == View.VISIBLE){
        	ivCircleImage.setVisibility(View.GONE);
        }
        
        TextView tvUserName = (TextView) convertView.findViewById(R.id.username);
        tvUserName.setText(entry.getUserFirstName() + " " + entry.getUserLastName());
                
        return convertView;
	}
}
