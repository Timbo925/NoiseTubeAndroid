package com.noisetube.main;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.noisetube.main.MainActivity.DbResponse;

public class SoundMeasurementService extends IntentService {

	private int dbLvl= 55;
	public static final String PARAM_IN_MSG = "inMsg SoundMeasurementService";
	public static final String PARAM_OUT_MSG = "outMsg db Level SoundMeasurementService";
	public static final String PARAM_OUT_PER = "outMsg db Percent SoundMeasurementService";
	private boolean loop = true;
	private SoundMeasurement soundMeasurement = new SoundMeasurement();

	public SoundMeasurementService() {
		super("SoundMeasurementService");
	}
	

	@Override
	protected void onHandleIntent(Intent workIntent) {

		Log.v("onHandleIntent", "Handeling intent");
		
		soundMeasurement.start();
		Log.v("onHnadleIntent", "soundMeasurement started");
		
		String msg = workIntent.getStringExtra(PARAM_IN_MSG); //Retreving message from the intent
		
		while (loop) {
			SystemClock.sleep(1000);
			soundMeasurement.measure();
			
			Intent broadcatIntent = new Intent();
			broadcatIntent.setAction(DbResponse.ACTION_RESP);
			broadcatIntent.addCategory(Intent.CATEGORY_DEFAULT);
			broadcatIntent.putExtra(PARAM_OUT_MSG, soundMeasurement.getDbLevel());
			broadcatIntent.putExtra(PARAM_OUT_PER, soundMeasurement.getDbPercent());
			sendBroadcast(broadcatIntent);
		}
		
		
		soundMeasurement.stop();
		this.stopSelf();
		Log.v("onHandleIntent", "Ending onHandleIntent");
	}
}
