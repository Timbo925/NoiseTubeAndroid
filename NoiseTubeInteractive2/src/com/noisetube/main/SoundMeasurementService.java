package com.noisetube.main;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.example.noisetubeinteractive2.MainActivity.DbResponse;
import com.example.noisetubeinteractive2.MainActivity.FinalDbResponse;

public class SoundMeasurementService extends IntentService {

	public static final String PARAM_IN_MSG = "inMsg SoundMeasurementService";
	public static final String PARAM_OUT_MSG = "outMsg db Level SoundMeasurementService";
	public static final String PARAM_OUT_PER = "outMsg db Percent SoundMeasurementService";
	public static final String PARAM_OUT_AVG = "outMsg db Average SoundMeasurementService";
	public static final String PARAM_OUT_MIN = "outMsg db Minimum SoundMeasurementService";
	public static final String PARAM_OUT_MAX = "outMsg db Maximum SoundMeasurementService";
	public static final String PARAM_OUT_TIME = "outMsg total time SoundMeasurementService";
	public static final String PARAM_COMMAND = "command";
	public static final String PARAM_STOP_COMMAND = "stop";
	public static final String PARAM_OUT_POINTM = "PointMeasurement Serialized";
	public static final String PARAM_OUT_LOCATIONM = "LocationMeasurment Serialized";
	public static final String PARAM_OUT_SOUNDM = "SoundMeasurement Serialized";
	
	private boolean isStoped = false;
	private static final long measurmentsSecond = 1000;
	private SoundMeasurement soundMeasurement = new SoundMeasurement();
	private PointMeasurement pointMeasurement;
	private LocationMeasurment locationMeasurment;
	private Context context;

	public SoundMeasurementService() {
		super("SoundMeasurementService");
	}
	
	/**
	 * To stop Intentservice start service with extra ("command","stop")
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent.hasExtra("command")) {
			isStoped = intent.getStringExtra(PARAM_COMMAND).equals(PARAM_STOP_COMMAND);
		}
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {

		Log.v("onHandleIntent", "Started serice");

		// Make sure while loop is only executed when this is the original service not the service started to stop
		// the original servcie by changing the parameter
		if (intent.hasExtra(PARAM_COMMAND)) {
			isStoped = intent.getStringExtra(PARAM_COMMAND).equals(PARAM_STOP_COMMAND);
			Log.v("onHandleIntent", "STOP Command received");
			return; 
		}
		
		
		soundMeasurement = new SoundMeasurement();
		pointMeasurement = new PointMeasurement(this);
		locationMeasurment = new LocationMeasurment(this);
		
		locationMeasurment.start();
		
		//Loop and refresches all elements. Results are broadcasted so they can be displayerd
		long oldTime = SystemClock.elapsedRealtime();
		while (!isStoped) {
			while ((SystemClock.elapsedRealtime() - oldTime) < measurmentsSecond) {
				
			}
			locationMeasurment.measure();
			soundMeasurement.measure();	
			pointMeasurement.measure(locationMeasurment.getLastLat() , locationMeasurment.getLastLon());
			
			//Creating broadcast
			Intent broadcastIntent = new Intent();
			broadcastIntent.setAction(DbResponse.ACTION_RESP);
			broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
			broadcastIntent.putExtra(PARAM_OUT_SOUNDM, soundMeasurement);
			broadcastIntent.putExtra(PARAM_OUT_POINTM, pointMeasurement);
			broadcastIntent.putExtra(PARAM_OUT_LOCATIONM, locationMeasurment);
			sendBroadcast(broadcastIntent);
			oldTime = SystemClock.elapsedRealtime();
		}
		
		locationMeasurment.stop();
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(FinalDbResponse.ACTION_RESP);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcastIntent.putExtra(PARAM_OUT_SOUNDM, soundMeasurement);
		broadcastIntent.putExtra(PARAM_OUT_POINTM, pointMeasurement);
		broadcastIntent.putExtra(PARAM_OUT_LOCATIONM, locationMeasurment);
		sendBroadcast(broadcastIntent);
		//TODO steps when stop measuring
		this.stopSelf();
		Log.v("onHandleIntent", "Closing service");
	}
}
