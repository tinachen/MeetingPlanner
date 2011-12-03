package com.uiproject.meetingplanner;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;


public class CommunicateService extends Service {
	
	public static final String CommunicateServiceTag = "CommunicateService";
	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
    private static final String TAG = "MeetingTracker";
	public static int MID;
	private int uid;
	MultiThread thread1;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void onCreate(){
		Log.d("CommunicateService","OnCreate");
	//	getIntent().getExtra("mid");
		super.onCreate();
	}
	
	public void onStart(Intent intent, int startId) {      //Create new thread for communicating data
		Log.d("CommunicateService", "onStart");
		if(thread1==null){
			thread1 = new MultiThread();
			thread1.start();
		}
		MID=intent.getIntExtra("mid", -1);
		Log.d(CommunicateServiceTag, "onCreate mid = " + MID);
		super.onStart(intent, startId);

	}
	
	public void onDestroy() {
		Log.d("CommunicateService", "onDestroy");
		super.onDestroy();
	}
	
	public class MultiThread extends Thread{
		boolean status = true;
		public void run(){
			while(status){
				try{
					displayResult();
					Thread.sleep(5000);                   //Set auto location update interval here
				}
				catch(Exception e) {   
					e.printStackTrace();
				}
			}
		}
		
	}
	
    public void displayResult() throws JSONException, ParseException
    {
    	ServerMsg sm1=new ServerMsg(6);
    	String result = getResponseResult(sm1);
    	Log.d("Show the result",result);
		JSONObject message = new JSONObject(result);
		int tag = message.getInt("tag");
		JSONObject locations = message.getJSONObject("locations");
		if(locations!=null){ //return msg;           don't send message and continue
			JSONArray userIds = locations.names();
			JSONArray locationInfos = locations.toJSONArray(userIds);
			Bundle msg = new Bundle();
			//Bundle location = new Bundle();
			Bundle locs = new Bundle();
	
			for (int i=0; i<locationInfos.length();i++) {
				//locs.put(userIds.getInt(i), new UserInstance(userIds.getInt(i),locationInfos.getJSONObject(i).getInt("lon"),locationInfos.getJSONObject(i).getInt("lat"),locationInfos.getJSONObject(i).getString("eta")));
				Bundle loc = new Bundle();
				//	loc.putInt("userID", userIds.getInt(i));
				loc.putInt("lon",locationInfos.getJSONObject(i).getInt("lon"));
				loc.putInt("lat",locationInfos.getJSONObject(i).getInt("lat"));
				//if(locationInfos.getJSONObject(i).getString)
				loc.putString("eta",locationInfos.getJSONObject(i).getString("eta"));
				locs.putBundle(String.valueOf(userIds.getInt(i)), loc);
			}
			
			msg.putInt("tag", tag);
			msg.putBundle("locations", locs);
			Intent intent = new Intent();
			intent.putExtra("message", msg);
			intent.setAction("com.uiproject.meetingplanner");
			//Broadcast received location info out
			sendBroadcast(intent);
			}

    	}
    Object origin;
	Object destination;
	String mode;
	String language;
	boolean sensor;
		
    public String getResponseResult(ServerMsg sm) throws ParseException, JSONException {
    	SharedPreferences settings =getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
    	uid = settings.getInt("uid", -1);
  //  	GeoUpdateHandler geoHandler = new GeoUpdateHandler(this);
    	String userId=new Integer(uid).toString();  	
    	String currenctLat=new Integer(MainPage.guh.getCurrentLat()).toString(); 
    	String etaLat=new Double(MainPage.guh.getCurrentLatDouble()).toString();
    	String currenctLng=new Integer(MainPage.guh.getCurrentLng()).toString();
    	String etaLng=new Double(MainPage.guh.getCurrentLngDouble()).toString();
    	String originLoc=etaLat +"," + etaLng;
    	String destinationLat;
    	String destinationLng;
    	
    	Map<Integer,MeetingInstance> allMeetingMap=new HashMap<Integer,MeetingInstance>();
    	allMeetingMap=Communicator.getAllMeetings();
    	destinationLat = new Double(allMeetingMap.get(MID).getMeetingLat()*0.000001).toString();
    	destinationLng = new Double(allMeetingMap.get(MID).getMeetingLon()*0.000001).toString();
    	String destinationLoc=destinationLat +"," + destinationLng ;
    	
    	{  
    	 this.origin = originLoc;
    	 this.destination = destinationLoc;
    	 this.mode="driving";
    	 this.language="en=US";
    	 this.sensor=false;}
    	
    	String etaValue = getEta(this.origin,this.destination,this.mode);
    	Log.d("eta value before encode",etaValue);
    	try {
			etaValue = URLEncoder.encode(etaValue,"utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	Log.d("eta value",etaValue);
    	String meetingId=new Integer(MID).toString();
    	//Send latest location info to server
    	String urlStr = "http://cs-server.usc.edu:21542/newwallapp/forms/myupdatelocation?userId=" + userId + "&meetingId=" + meetingId +"&lat=" + currenctLat +"&lon=" +currenctLng + "&eta=" + etaValue;
    	Log.d("urlstring",urlStr);
    	String responseResult="";
    	try {
    		URL objUrl = new URL(urlStr);
    		URLConnection connect1 = objUrl.openConnection();
    		connect1.setConnectTimeout(10000);
    		connect1.connect();
    		BufferedReader in = new BufferedReader(new InputStreamReader(connect1.getInputStream()));
    		//Data
    		String content;
    		System.out.println("right");
    		while((content=in.readLine())!=null)
    		{
    			responseResult+=content;
    		}
    		in.close();
    	} catch (Exception e) {
    		System.out.println("error!");
    	}
    	return responseResult;              //Return others location from server
    }
    

    protected String getEta(Object origin, Object destination, String mode)         //Calculate ETA
    {
    	this.origin = origin;
    	this.destination = destination;
    	this.mode = mode;
    	String myURL = "http://maps.googleapis.com/maps/api/distancematrix/xml?origins="+origin+"&destinations="+destination+"&mode="+mode+"&language="+language+"&sensor="+sensor+"";
    	String readableValue = "";
    	try
        {
    		URL                url;
    		URLConnection      urlConn;
    		DataInputStream    dis;

    		//My Directions
    		url = new URL(myURL);

    		urlConn = url.openConnection();
    		urlConn.setDoInput(true);
    		urlConn.setUseCaches(false);

    		dis = new DataInputStream(urlConn.getInputStream());
    		String line;
    		String value;
    		boolean done;
    		done = false;
    		
    		while (((line = dis.readLine()) != null) && (done == false))
    		{
    			line = line.toString();
    			line = line.trim();
    			if (line.equals("<duration>"))
    			{
    				Log.v(TAG, "Here I am!");
    				value = dis.readLine();
    				value = value.trim();
    				readableValue = dis.readLine();
    				readableValue = readableValue.trim();
    				if (value.startsWith("<value>"))
    				{
    					value = value.replaceAll("<value>", "");
    					value = value.replaceAll("</value>", "");
    				}
    				if (readableValue.startsWith("<text>"))
    				{
    					readableValue = readableValue.replaceAll("<text>", "");
    					readableValue = readableValue.replaceAll("</text>", "");
    					//Log.v(TAG, "The final value is: " + readableValue);
    					done = true;
    				}
    			}
    		}
            dis.close();
        }

    	catch (MalformedURLException mue) 
    	{
    		Log.e(TAG, "Sorry! Please double check the values passed: " + mue);
    	}
        catch (IOException ioe) 
        {
        	Log.e(TAG, "Sorry! Could not read values properly: " + ioe);
        }
        
    	if (readableValue != "")
    	{
    		return readableValue;
    	}
    	else
    	{
    		return "Oh no! We could not find the ETA. Please try again."; 
    	}
    }

}
