package com.uiproject.meetingplanner;    

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class MeetingPlanner extends Activity {
	
	private LocationManager locationManager;	// for detecting current location
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // used for detecting current position
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new GeoUpdateHandler(this,));
		
    }
    

    private static final String TAG = "MeetingTracker";
    
    Object origin;
	Object destination;
	String mode;
	String language;
	boolean sensor;{
    
    this.origin = "Chicago+IL";
    this.destination = "Madison+WI";
    this.mode="driving";
    this.language="en=US";
    this.sensor=false;}
	
    protected String getEta(Object origin, Object destination, String mode)
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
    
    /*Author:Yuwen
     @Use this method to send your information
     @The detailed info is stored individually into different params.
     @And this method will also handling the response from the server.
     */
	public String getResponseResult() {
		String param1="6",param2="33",param3="44";
		String urlStr = "http://cs-server.usc.edu:21542/newwallapp/forms/project?id="+param1+"&lat="+param2+"&lon="+param3;
		//request.getParameter("param1");
		String responseResult="";
		try {
			URL objUrl = new URL(urlStr);
			URLConnection connect1 = objUrl.openConnection();
			connect1.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(connect1.getInputStream()));
			//Data
			String content;
		
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

