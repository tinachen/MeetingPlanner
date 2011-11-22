package com.uiproject.meetingplanner;

import com.uiproject.meetingplanner.database.MeetingPlannerDatabaseHelper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyAlarmService extends Service {

	@Override
	public void onCreate() {

		// TODO Auto-generated method stub

		Toast.makeText(this, "MyAlarmService.onCreate()", Toast.LENGTH_LONG).show();
		

	}

	@Override
	public IBinder onBind(Intent intent) {

		// TODO Auto-generated method stub

		Toast.makeText(this, "MyAlarmService.onBind()", Toast.LENGTH_LONG).show();

		return null;

	}

	@Override
	public void onDestroy() {

		// TODO Auto-generated method stub

		super.onDestroy();

		Toast.makeText(this, "MyAlarmService.onDestroy()", Toast.LENGTH_LONG).show();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		int icon = R.drawable.call;
		CharSequence tickerText = "New Meeting";
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon, tickerText, when);
		Context context = getApplicationContext();
		CharSequence contentTitle = "New Meeting Approaching";
		CharSequence contentText = "Project Discussion";
		Intent notificationIntent = new Intent(this, DisplayMeeting.class);
		notificationIntent.putExtra("mid", intent.getIntExtra("mid", 2) );
		Toast.makeText(this, String.valueOf(intent.getIntExtra("mid", 2)), Toast.LENGTH_LONG).show();
		notificationIntent.putExtra("status",MeetingPlannerDatabaseHelper.ATTENDINGSTATUS_ATTENDING);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		final int HELLO_ID = 1;

		mNotificationManager.notify(HELLO_ID, notification);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {

		// TODO Auto-generated method stub

		Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_LONG).show();

		return super.onUnbind(intent);

	}

}
