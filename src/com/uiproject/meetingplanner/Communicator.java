package com.uiproject.meetingplanner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import org.json.*;

public class Communicator {

	public static String getResponseResult(String urlStr) {
<<<<<<< HEAD
    	//String urlStr = "http://cs-server.usc.edu:21542/newwallapp/forms/showmyusers";
    	//String urlStr = "http://10.0.2.2:8080/newwallapp/forms/showmyusers";
    	String responseResult="";
    	try {
    		URL objUrl = new URL(urlStr);
    		URLConnection connect1 = objUrl.openConnection();
    		connect1.setConnectTimeout(10000);
    		connect1.connect();
    		BufferedReader in = new BufferedReader(new InputStreamReader(connect1.getInputStream()));
    		//Data
    		String content;
    		//System.out.println("right");
    		while((content=in.readLine())!=null)
    		{
    			responseResult+=content;
    		}
    		in.close();
    	} catch (Exception e) {
    		System.out.println("error!");
    	}
    	return responseResult;
    }
	
	public static int createUser(String phoneNumber, String firstName, String lastName, String email, String password) {
    	String urlStr = "http://cs-server.usc.edu:21542/newwallapp/forms/mycreateuser?phoneNumber="+phoneNumber+"&firstName="+firstName+"&lastName="+lastName+"&email="+email+"&password="+password;
    	String result = getResponseResult(urlStr);
    	return Integer.valueOf(result);
    }
	
	public static boolean login(String phoneNumber, String password) {
		String urlStr = "http://cs-server.usc.edu:21542/newwallapp/forms/myverifyuser?phoneNumber="+phoneNumber+"&password="+password;
=======
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

	public static int logIn(long phoneNumber, int password) {
		String urlStr = "http://cs-server.usc.edu:21542/newwallapp/forms/myverifyuser?phoneNumber=" + phoneNumber + "&password=" + password;
		String result = getResponseResult(urlStr);
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
	
	public static String getAllUsers() throws JSONException {
		String urlStr = "http://cs-server.usc.edu:21542/newwallapp/forms/mygetallusers";
>>>>>>> small changes to logIn in Communicator
		String result = getResponseResult(urlStr);
		JSONObject myjson = new JSONObject(result);
		JSONArray nameArray = myjson.names();
		
		return result;
	}
}
