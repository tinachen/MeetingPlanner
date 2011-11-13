package com.uiproject.meetingplanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CommunicateService extends Service {
	MultiThread thread1;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void onCreate(){
		Log.d("CommunicateService","OnCreate");
		super.onCreate();
	}
	
	public void onStart(Intent intent, int startId) {
		Log.d("CommunicateService", "onStart");
		if(thread1==null){
			thread1 = new MultiThread();
			thread1.start();
		}
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
					Thread.sleep(10000);
				}
				catch(Exception e) {   
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
    public void displayResult() throws JSONException
    {
    	Random r=new Random();
    	int ran=r.nextInt(10);
    	int test = 10+ran;
    	
    	ServerMsg sm1=new ServerMsg(test);
    	String data = getResponseResult(sm1);
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
    	String param1=new Integer(sm.userID).toString(); 
    //	String param2=new Integer(sm.myLat).toString();
    //	String param3=new Integer(sm.myLong).toString();
    	String urlStr = "http://cs-server.usc.edu:21542/newwallapp/forms/yuwen?i="+param1;
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
