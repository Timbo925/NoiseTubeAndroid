package com.noisetube.main;

import java.util.Calendar;
import java.util.GregorianCalendar;

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
	public static final String PARAM_OUT_AVG = "outMsg db Average SoundMeasurementService";
	public static final String PARAM_OUT_MIN = "outMsg db Minimum SoundMeasurementService";
	public static final String PARAM_OUT_MAX = "outMsg db Maximum SoundMeasurementService";
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
			soundMeasurement.measure();	
			Intent broadcatIntent = new Intent();
			broadcatIntent.setAction(DbResponse.ACTION_RESP);
			broadcatIntent.addCategory(Intent.CATEGORY_DEFAULT);
			broadcatIntent.putExtra(PARAM_OUT_MSG, soundMeasurement.getDbLevel());
			broadcatIntent.putExtra(PARAM_OUT_PER, soundMeasurement.getDbPercent());
			broadcatIntent.putExtra(PARAM_OUT_AVG, soundMeasurement.getAvgDb());
			broadcatIntent.putExtra(PARAM_OUT_MAX, soundMeasurement.getMaxDb());
			broadcatIntent.putExtra(PARAM_OUT_MIN, soundMeasurement.getMinDb());
			sendBroadcast(broadcatIntent);
		}
		
		soundMeasurement.stop();
		this.stopSelf();
		Log.v("onHandleIntent", "Ending onHandleIntent");
	}
}
