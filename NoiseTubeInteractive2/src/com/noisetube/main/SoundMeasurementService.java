package com.noisetube.main;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.util.Log;

import com.example.noisetubeinteractive2.MainActivity.DbResponse;
import com.example.noisetubeinteractive2.MainActivity.FinalDbResponse;
import com.google.gson.Gson;

public class SoundMeasurementService extends IntentService {

	public static final String PARAM_IN_MSG = "inMsg SoundMeasurementService";
	public static final String PARAM_OUT_MSG = "outMsg db Level SoundMeasurementService";
	public static final String PARAM_OUT_PER = "outMsg db Percent SoundMeasurementService";
	public static final String PARAM_OUT_AVG = "outMsg db Average SoundMeasurementService";
	public static final String PARAM_OUT_MIN = "outMsg db Minimum SoundMeasurementService";
	public static final String PARAM_OUT_MAX = "outMsg db Maximum SoundMeasurementService";
	public static final String PARAM_OUT_TIME = "outMsg total time SoundMeasurementService";
	public static final String PARAM_OUT_SOUNDM = "outMsg SoundMeasurement in Json SoundMeasurementService";
	public static final String PARAM_COMMAND = "command";
	public static final String PARAM_STOP_COMMAND = "stop";
	private boolean isStoped = false;
	private SoundMeasurement soundMeasurement;
	

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
		while (!isStoped) {
			soundMeasurement.measure();	
			//Creating broadcast
			Intent broadcastIntent = new Intent();
			broadcastIntent.setAction(DbResponse.ACTION_RESP);
			broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
			broadcastIntent.putExtra(PARAM_OUT_MSG, soundMeasurement.getDbLast());
			broadcastIntent.putExtra(PARAM_OUT_PER, (int) soundMeasurement.getDbPercent());
			broadcastIntent.putExtra(PARAM_OUT_AVG, (int) soundMeasurement.getDbAvg());
			broadcastIntent.putExtra(PARAM_OUT_MAX, soundMeasurement.getDbMax());
			broadcastIntent.putExtra(PARAM_OUT_MIN, soundMeasurement.getDbMin());
			broadcastIntent.putExtra(PARAM_OUT_TIME, soundMeasurement.getTime());
			broadcastIntent.putExtra(PARAM_OUT_SOUNDM, soundMeasurement);
			sendBroadcast(broadcastIntent);
		}
		
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(FinalDbResponse.ACTION_RESP);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcastIntent.putExtra(PARAM_OUT_SOUNDM, soundMeasurement);
		sendBroadcast(broadcastIntent);
		//TODO steps when stop measuring
		this.stopSelf();
		Log.v("onHandleIntent", "Closing service");
	}
}
