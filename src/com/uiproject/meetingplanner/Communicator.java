package com.uiproject.meetingplanner;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.json.*;

import android.util.Log;

public class Communicator {

	public static String getResponseResult(String urlStr) {
		// String urlStr =
		// "http://cs-server.usc.edu:21542/newwallapp/forms/showmyusers";
		// String urlStr = "http://10.0.2.2:8080/newwallapp/forms/showmyusers";
		String responseResult = "";
		try {
			URL objUrl = new URL(urlStr);
			URLConnection connect1 = objUrl.openConnection();
			connect1.setConnectTimeout(10000);
			connect1.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(connect1.getInputStream()));
			// Data
			String content;
			// System.out.println("right");
			while ((content = in.readLine()) != null) {
				responseResult += content;
			}
			in.close();
		} catch (Exception e) {
			System.out.println("error!");
		}
		return responseResult;
	}

	public static int createUser(long phoneNumber, String firstName, String lastName, String email, String password) {
		String urlStr = "http://cs-server.usc.edu:21542/newwallapp/forms/mycreateuser?phoneNumber=" + phoneNumber + "&firstName=" + firstName
				+ "&lastName=" + lastName + "&email=" + email + "&password=" + password;
		String result = getResponseResult(urlStr);
		return Integer.valueOf(result);
	}

	public static int logIn(long phoneNumber, String password) {
		String urlStr = "http://cs-server.usc.edu:21542/newwallapp/forms/myverifyuser?phoneNumber=" + phoneNumber + "&password=" + password;
		String result = getResponseResult(urlStr);
		
		// debug msg
		String tag = "Communicator";
		Log.v(tag, urlStr);
		
		return Integer.valueOf(result);
	}

	public static int createMeeting(int userId, String name, String description, int meetingLat, int meetingLon, String meetingAddress,
			String meetingDate, String meetingStartTime, String meetingEndTime, int meetingTrackTime, String people) {
		String urlStr = "http://cs-server.usc.edu:21542/newwallapp/forms/mycreatemeeting?userId=" + userId + "&name=" + name + "&description="
				+ description + "&meetingLat=" + meetingLat + "&meetingLon=" + meetingLon + "&meetingAddress=" + meetingAddress + "&meetingDate="
				+ meetingDate + "&meetingStartTime=" + meetingStartTime + "&meetingEndTime=" + meetingEndTime + "&meetingTrackTime="
				+ meetingTrackTime + "&people=" + people;
		String result = getResponseResult(urlStr);
		return Integer.valueOf(result);
	}
	
	public static Map<Integer,UserInstance> getAllUsers() throws JSONException {
		String urlStr = "http://cs-server.usc.edu:21542/newwallapp/forms/mygetallusers";
		String result = getResponseResult(urlStr);
		JSONObject users = new JSONObject(result);
		JSONArray userIds = users.names();
		JSONArray userInfos = users.toJSONArray(userIds);
		Map<Integer,UserInstance> allUsers = new HashMap<Integer, UserInstance>();
		for ( int i = 0; i<userInfos.length();i++) {
			int userID = userIds.getInt(i);
			String userFirstName = userInfos.getJSONObject(i).getString("firstName");
			String userLastName = userInfos.getJSONObject(i).getString("lastName");
			String userEmail = userInfos.getJSONObject(i).getString("email");
			String userPhone = String.valueOf(userInfos.getJSONObject(i).getLong("phoneNumber"));
			int userLocationLon = userInfos.getJSONObject(i).getInt("lon");
			int userLocationLat = userInfos.getJSONObject(i).getInt("lat");
			UserInstance userInstance = new UserInstance(userID,userFirstName,userLastName,userEmail,userPhone,userLocationLon,userLocationLat);
			allUsers.put(userID, userInstance);
		}
		/*System.out.println(allUsers.get(1).getUserID());
		System.out.println(allUsers.get(1).getUserFirstName());
		System.out.println(allUsers.get(1).getUserLastName());
		System.out.println(allUsers.get(1).getUserEmail());
		System.out.println(allUsers.get(1).getUserPhone());
		System.out.println(allUsers.get(1).getUserLocationLat());
		System.out.println(allUsers.get(1).getUserLocationLon());*/
		return allUsers;
	}
}
