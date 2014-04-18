package com.noisetube.main;

import java.io.File;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

public class SoundMeasurement {
	public static final int MINIMUM_DB = 10;
	public static final int MAXIMUM_DB = 100;
	private static final int sampleRate = 44100;
	
	private int dbLevel;
	private int dbPercent;
	
	private AudioRecord mRecorder;
	private File mRecording;
	private short[] mBuffer;
	private final String startRecordingLabel = "Start recording";
	private final String stopRecordingLabel = "Stop recording";
	private boolean mIsRecording = false;
	private int bufferSize;
	
	private int Counter =  0;
	private int AvgDb = 0;
	private int MaxDb = 0;
	private int MinDb = 100;

	public int getMaxDb() {
		return MaxDb;
	}
	public void setMaxDb(int maxDb) {
		MaxDb = maxDb;
	}
	public SoundMeasurement() {
		
	}
	public int getDbLevel() {
		return dbLevel;
	}
	public void setDbLevel(int dbLevel) {
		this.dbLevel = dbLevel;
	}
	public int getDbPercent() {
		return dbPercent;
	}
	public void setDbPercent(int dbPercent) {
		this.dbPercent = dbPercent;
	}
	
	public int getCounter() {
		return Counter;
	}
	public void setCounter(int counter) {
		Counter = counter;
	}
	public int getAvgDb() {
		return AvgDb;
	}
	public void setAvgDb(int avgDb) {
		AvgDb = avgDb;
	}
	public int getMinDb() {
		return MinDb;
	}
	public void setMinDb(int minDb) {
		MinDb = minDb;
	}
	public void start() {
		bufferSize = AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT);
		mBuffer = new short[bufferSize*4];
		mRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC, 44100, AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT, bufferSize*4);
		//mRecorder.startRecording();
	}
	
	public void measure() {
		short[] output = new short[bufferSize];
		double average = 0.0;
		mRecorder.startRecording();
		double sum = 0;
		int readSize = mRecorder.read(mBuffer, 0, mBuffer.length);   //Reading the buffer that is currently stored
		mRecorder.stop();
		
		for (int i = 0; i < readSize; i++) {
			sum += mBuffer[i] * mBuffer[i];
			average += Math.abs(mBuffer[i]);
		}
		//System.out.println(average);
		//System.out.println(readSize);
		if (readSize > 0) {
			//Old Calculations
			//TODO remove this 
			final double amplitude = sum / readSize;
			int lvl = ((int) Math.sqrt(amplitude)); //TODO make it possible to calibrate this value
			lvl = (int) (20* Math.log10(lvl)); // A-weighting
			
			
			//New Calcutations
			double x = average/((bufferSize*4)-1);
		    double db=0;
		    // calculating the pascal pressure based on the idea that the max amplitude (between 0 and 32767) is 
		    // relative to the pressure
		    double pressure = x/51805.5336; //the value 51805.5336 can be derived from asuming that x=32767=0.6325 Pa and x=1 = 0.00002 Pa (the reference value)
		    db = (20 * Math.log10(pressure/0.00002));

			if (db < MINIMUM_DB) {
				dbLevel = -10001;
				dbPercent = 0;
			} else if (db > MAXIMUM_DB) {
				dbLevel = -10000;
				dbPercent = 100;
			} else {
				dbLevel = (int) db;
				double spread = MAXIMUM_DB-MINIMUM_DB;
				double spreadMulti = 100.0/spread;
				dbPercent = (int) (spreadMulti * (db - MINIMUM_DB));
				Counter++;
				AvgDb = ((AvgDb * (Counter - 1)) + dbLevel)/Counter;
				if (dbLevel > MaxDb ) {
					MaxDb = dbLevel;
				}
				if (dbLevel < MinDb) {
					MinDb = dbLevel;
				}
				
				//Log.d("measure() 1", "db=[" + lvl + "], percent=[" + dbPercent + "] ,amplitude=[" + amplitude + "]");
				Log.d("measure() 2", "db=[" + (int) db + "], percent=[" + dbPercent + "],average=[" + average + "] ,pressure=[" + pressure + "]");
			}
		}
	}
	
	public void stop() {
		//mRecorder.stop();
	}
	
}
