package com.uiproject.meetingplanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseManager;

public class TrackerMap extends MapActivity {

	public static final String TAG = "TrackerMap";
	public static final String PREFERENCE_FILENAME = "MeetAppPrefs";
	
	protected MyItemizedOverlay itemizedoverlay, itemizedoverlay2;
	protected MapView mapView;
	protected int mid;
	protected MapController mc;
	protected View persontracker;
	protected TextView name_text, eta_text;
	private MeetingPlannerDatabaseManager db;
	boolean zoom = false;
	ArrayList<Map<Integer,UserInstance>> list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackermap);

        mid = getIntent().getIntExtra("mid", -1);
        Log.d(TAG, "onCreate: mid = " + mid);
        
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mc = mapView.getController();
        persontracker = findViewById(R.id.persontracker);
    	persontracker.setVisibility(View.GONE);
    	name_text = (TextView) findViewById(R.id.name);
    	eta_text =(TextView) findViewById(R.id.eta);
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.marker);
        itemizedoverlay = new MyItemizedOverlay(drawable, this);
        itemizedoverlay2 = new MyItemizedOverlay(this.getResources().getDrawable(R.drawable.goal_marker), this);
        mapOverlays.add(itemizedoverlay);
        mapOverlays.add(itemizedoverlay2);
        /*
	    db = new MeetingPlannerDatabaseManager(this, MeetingPlannerDatabaseHelper.DATABASE_VERSION);
	    db.open();
        MeetingInstance m = db.getMeeting(mid);
        db.close();
        GeoPoint meetingloc = new GeoPoint(m.getMeetingLat(), m.getMeetingLon());
        itemizedoverlay.setMeetingloc(meetingloc);
        */

        
        /*
        
        Intent intent = new Intent(TrackerMap.this, CommunicateService.class);
        intent.putExtra("mid", mid);
        startService(intent);
        
        TestReceiver2 receiver2 =new TestReceiver2();
		IntentFilter filter2 = new IntentFilter();
		filter2.addAction("com.uiproject.meetingplanner");
		registerReceiver(receiver2, filter2); 
		*/
        
    }
    
    public void onResume(){
    	super.onResume();

		fakeit();
    }
    
    public void fakeit(){
    	GeoPoint p = new GeoPoint(34019941, -118289108);
    	itemizedoverlay2.setMeetingloc(p);
    	itemizedoverlay2.addMeetingLoc();
    	itemizedoverlay2.doPopulate();
    	
    	list= new ArrayList<Map<Integer,UserInstance>>();
        Map<Integer,UserInstance> userLocations = new HashMap<Integer, UserInstance>();
        UserInstance u = new UserInstance(1);
        u.setUserEta("24 min");
        u.setUserLocationLat(34115483);
        u.setUserLocationLon(-118152738);
        u.setUserFirstName("Tina");
        u.setUserLastName("Chen");
        userLocations.put(1, u);
        UserInstance u2 = new UserInstance(2);
        u2.setUserEta("20 min");
        u2.setUserLocationLat(34149940);
        u2.setUserLocationLon(-118269295);
        u2.setUserFirstName("Elizabeth");
        u2.setUserLastName("Deng");
        userLocations.put(2, u2);
        UserInstance u3 = new UserInstance(3);
        u3.setUserEta("20 min");
        u3.setUserLocationLat(34101428);
        u3.setUserLocationLon(-118096870);
        u3.setUserFirstName("Mengfei");
        u3.setUserLastName("Xu");
        userLocations.put(3, u3);
        list.add(userLocations);
    	updateMap(list.get(0));

    }
        
    @Override
    public boolean isRouteDisplayed() {
        return false;
    }

    public void updateMap(Map<Integer, UserInstance> attendees){
    	
    	Log.d(TAG, "updateMap size = " + attendees.size());
    	
    	itemizedoverlay.clear();
    	MyOverlayItem myoi;
    	OverlayItem oi;
    	Set<Integer> keys = attendees.keySet();
    	UserInstance a;
    	for (Integer i : keys){
    		a = attendees.get(i);
    		oi = new OverlayItem(new GeoPoint(a.getUserLocationLat(), a.getUserLocationLon()), "", "");
    		Log.d(TAG, "updateMap user = " + a.getUserFirstName() + " " + a.getUserLastName() + ", lat = " + a.getUserLocationLat()
    				+ ", lon = " + a.getUserLocationLon());
    		myoi = new MyOverlayItem(oi, a.getUserFirstName() + " " + a.getUserLastName(), a.getUserEta());
    		itemizedoverlay.addOverlay(myoi);
    	}
    	
    	itemizedoverlay.doPopulate();
    	//if (!zoom){
	        mc.zoomToSpan(itemizedoverlay.getLatSpanE6(), itemizedoverlay.getLonSpanE6());
	        
	        // set the center
	        mc.setCenter(itemizedoverlay.getCenter());
	        zoom = true;
    	//}
    	
    }
    
    public void close(View button){
    	persontracker.setVisibility(View.GONE);
    }
    
    
    public class TestReceiver2 extends BroadcastReceiver { 
    	public TestReceiver2 (){
    	}

        public void onReceive(Context context, Intent intent) { 
            // TODO Auto-generated method stub       
            Log.d ("Receiver","Success");
            Bundle message = intent.getBundleExtra("message");
            int tag = message.getInt("tag");
            Bundle locations = message.getBundle("locations");
            Map<Integer,UserInstance> userLocations = new HashMap<Integer, UserInstance>();
            for (String i : locations.keySet()){
            	Bundle location = locations.getBundle(i);
            	userLocations.put(Integer.valueOf(i), new UserInstance(Integer.valueOf(i),location.getInt("lat"),location.getInt("lon"),location.getString("eta")));
            }
            
            Log.d("Receiver", "Map size = " + userLocations.size());
            updateMap(userLocations);
            Log.d("tag","tag: "+tag);
            Log.d("AAA","userId: "+6);
            /*Log.d("AAA","lat: "+userLocations.get(6).getUserLocationLat());
            Log.d("AAA","lon: "+userLocations.get(6).getUserLocationLon());
            Log.d("AAA","eta: "+userLocations.get(6).getUserEta());*/
          
            
        } 
        
    } 
   
    
    public void toList(View button){  	

    	Intent intent = new Intent(TrackerMap.this, TrackerEtaList.class);
		intent.putExtra("mid", mid);
		TrackerMap.this.startActivityForResult(intent, 0);
    }

    // menu 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logoutonly, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:{
            	Logout.logout(this);
            	break;
            }
        }
        return true;
    }
    
    //overlay class
    private class MyItemizedOverlay extends ItemizedOverlay<OverlayItem> {
    	private ArrayList<MyOverlayItem> mOverlays = new ArrayList<MyOverlayItem>();
    	private GeoPoint center;
    	private Context mContext;
    	private MyOverlayItem meetingloc;
        
    	public MyItemizedOverlay(Drawable defaultMarker) {
    		super(boundCenterBottom(defaultMarker));
    	}
    	
    	public MyItemizedOverlay(Drawable defaultMarker, Context context) {
    	  super(boundCenterBottom(defaultMarker));
    	  mContext = context;
    	  center = new GeoPoint(0, 0);
    	}

    	public void setMeetingloc(GeoPoint p){
    		OverlayItem oi = new OverlayItem(p, "", "");
    		//oi.setMarker(mContext.getResources().getDrawable(R.drawable.goal_marker));//TODO
    		MyOverlayItem myoi = new MyOverlayItem(oi, "588 Group meeting", "");
    		meetingloc = myoi;
    		addOverlay(myoi);
    	}
    	
    	@Override
    	protected OverlayItem createItem(int i) {
    	  return mOverlays.get(i).getOverlayItem();
    	}

    	@Override
    	public int size() {
    		return mOverlays.size();
    	}
    	
    	public void clear(){
    		mOverlays.clear();
    		center = new GeoPoint(0, 0);
    	}
    	
    	@Override
    	protected boolean onTap(int index) {
    	  MyOverlayItem item = mOverlays.get(index);
    	  name_text.setText(item.getName());
    	  eta_text.setText(item.getEta());
      	  persontracker.setVisibility(View.VISIBLE);

    	  return true;
    	}
    	
    	public void addMeetingLoc(){
    		addOverlay(meetingloc);
    	}
    	
    	public void addOverlay(MyOverlayItem overlay) {
    	    mOverlays.add(overlay);

    	    //calculate the new center point
    	    GeoPoint p = overlay.getOverlayItem().getPoint();
    	    int size = mOverlays.size();
    	    int lat = p.getLatitudeE6();
    	    int lon = p.getLongitudeE6() ;
    	    int cLat = center.getLatitudeE6() * (size - 1);
    	    int cLon = center.getLongitudeE6() * (size - 1);
    	    int newLat =  (lat + cLat) / size;
    	    int newLon =  (lon + cLon) / size;
    	    GeoPoint c = new GeoPoint(newLat, newLon);
    	    
    	    //set the new center point
    	    center = c;
        	Log.d(TAG, "add to itemized overlay: " + overlay.toString());
    	    
    	}
    	
    	public void doPopulate(){
    		populate();
    	}
    	
    	public GeoPoint getCenter(){
    		return center;
    	}
    	
    }
    
    private class MyOverlayItem{
    	
    	private OverlayItem oi;
    	private String name;
    	private String eta; // in sec
    	
    	public MyOverlayItem(OverlayItem o, String n, String e){
    		oi = o;
    		name = n;
    		eta = e;
    	}
    	
    	public String getName(){
    		return name;
    	}
    	    	
    	public String getEta(){
    		return eta;
    	}
    	
    	public OverlayItem getOverlayItem(){
    		return oi;
    	}
    	
    	public String toString(){
    		return name + "; eta: " + eta + "; current pos: " + oi.getPoint().toString();
    	}
    }


}
