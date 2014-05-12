package com.noisetube.main;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.example.noisetubeinteractive2.MainActivity;
import com.example.noisetubeinteractive2.R;

public class MyAlarmService extends Service {

	public final static String notificationBonus = "Notification Bonus";
	private NotificationManager mManager;

	@Override
	public IBinder onBind(Intent arg0)
	{
		return null;
	}

	@Override
	public void onCreate() 
	{ 
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId)
	{

		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(this)
		.setSmallIcon(R.drawable.ic_launcher_bakcup)
		.setContentTitle("NoiseTube Bonus")
		.setContentText("Come back today and earn extra 100 points!");
		Intent resultIntent = new Intent(this, MainActivity.class);
		resultIntent.putExtra(notificationBonus, 100); //TOOD variable points for notification

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);


		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
				stackBuilder.getPendingIntent(
						0,
						PendingIntent.FLAG_UPDATE_CURRENT
						);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(startId, mBuilder.build());
	}

	@Override
	public void onDestroy() 
	{
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}