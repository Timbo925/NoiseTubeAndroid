package com.noisetube.main;

import com.noisetube.main.MainActivity.DbResponse;

import android.app.IntentService;
import android.content.Intent;
import android.hardware.Camera.ShutterCallback;
import android.util.Log;

public class SoundMeasurementService extends IntentService {

	private int dbLvl= 55;
	private static final String dbAppend = " db";
	public static final String PARAM_IN_MSG = "inMsg";
	public static final String PARAM_OUT_MSG = "outMsg";
	//TODO create soundmeter class

	public SoundMeasurementService() {
		super("SoundMeasurementService");
	}

	 
	@Override
	protected void onHandleIntent(Intent workIntent) {

		Log.v("onHandleIntent", "Handeling intent");
		String msg = workIntent.getStringExtra(PARAM_IN_MSG);

		Intent broadcatIntent = new Intent();
		broadcatIntent.setAction(DbResponse.ACTION_RESP);
		broadcatIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcatIntent.putExtra(PARAM_OUT_MSG, dbLvl + dbAppend);
		sendBroadcast(broadcatIntent);
		
		this.stopSelf();
		Log.v("onHandleIntent", "Ending onHandleIntent");
	}
}
