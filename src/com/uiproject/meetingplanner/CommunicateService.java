package com.uiproject.meetingplanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
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
	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
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
	
	public void onStart(Intent intent, int startId) {
		Log.d("CommunicateService", "onStart");
		if(thread1==null){
			thread1 = new MultiThread();
			thread1.start();
		}
	//	getIntent().getExtra("mid");
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
					Thread.sleep(5000);
				}
				catch(Exception e) {   
					e.printStackTrace();
				}
			}
		}
		
	}
	
    public void displayResult() throws JSONException
    {
    	Log.d("asd","1");

    	ServerMsg sm1=new ServerMsg(6);
    	Log.d("asd","2");
    	String result = getResponseResult(sm1);
    	Log.d("Show the result",result);
   // 	Map<String,Object> msg =new HashMap<String,Object> ();
		JSONObject message = new JSONObject(result);
		int tag = message.getInt("tag");
	//	msg.put("tag",tag);
		JSONObject locations = message.getJSONObject("locations");
		if(locations!=null){ //return msg;           don't send message and continue
			Log.d("##############","tttttttt");
			JSONArray userIds = locations.names();
			JSONArray locationInfos = locations.toJSONArray(userIds);
			Bundle msg = new Bundle();
			//Bundle location = new Bundle();
			Bundle locs = new Bundle();
	//		Map<Integer,UserInstance> locs = new HashMap<Integer, UserInstance>();
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
			//	msg.put("locations", locs);
			msg.putInt("tag", tag);
			msg.putBundle("locations", locs);
		
			Intent intent = new Intent();
			intent.putExtra("message", msg);
			intent.setAction("com.uiproject.meetingplanner");
			sendBroadcast(intent);
			Log.d("##############","Hello");
			}
			//return msg;
		
/*    	//HashMap<Integer, Msg> map = new HashMap<Integer, Msg>();
    	HashMap<Integer, ServerMsg> map = new HashMap<Integer, ServerMsg>();
		JSONObject myjson = new JSONObject(data);
		JSONArray nameArray = myjson.names();
		JSONArray valArray = myjson.toJSONArray(nameArray);
		for (int i = 0; i < valArray.length(); i++) {
			int la = valArray.getJSONObject(i).getInt("lat");
			int lo = valArray.getJSONObject(i).getInt("lon");
			map.put(nameArray.getInt(i), new ServerMsg(la,lo));
		}
		
		// Output the map
		for (Integer i: map.keySet()){
			
			//System.out.println(i+":"+map.get(i).lat+","+map.get(i).lon);
			//textview1.setText(i+":"+map.get(i).lat+","+map.get(i).lon);
			ServerMsg[] responseitem=new ServerMsg[10];
			int j=0;
			ServerMsg response=new ServerMsg(i,map.get(i).myLat,map.get(i).myLong);
			responseitem[j]=response;
		//	textview1.setText(responseitem[j].userID+":"+responseitem[j].myLat+","+responseitem[j].myLong);
			j++;
		}
*/	
    	}
    
    public String getResponseResult(ServerMsg sm) {
    	SharedPreferences settings =getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
    	uid = settings.getInt("uid", -1);
    	GeoUpdateHandler geoHandler = new GeoUpdateHandler(this);
    	String Userid=new Integer(uid).toString();
    	Log.d("Userid",Userid);
    	
		
		
    	String CurrenctLat=new Integer(geoHandler.getCurrentLat()).toString(); 
    	Log.d("CurLat",CurrenctLat);
    	String CurrenctLng=new Integer(geoHandler.getCurrentLng()).toString(); 
    	Log.d("CurLng",CurrenctLng);
    //	String param2=new Integer(sm.myLat).toString();
    //	String param3=new Integer(sm.myLong).toString();
    //	String urlStr = "http://cs-server.usc.edu:21542/newwallapp/forms/yuwen?i="+param1;
    	String urlStr = "http://cs-server.usc.edu:21542/newwallapp/forms/myupdatelocation?userId=" + Userid + "&meetingId=2&lat=" + CurrenctLat +"&lon=" +CurrenctLng + "&eta=9";
    //	String urlStr = "http://cs-server.usc.edu:21542/newwallapp/forms/myupdatelocation?userId=6&meetingId=2&lat=9&lon=9&eta=9";
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
    	return responseResult;
    }

}
