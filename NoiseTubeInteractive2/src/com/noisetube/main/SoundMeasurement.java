package com.noisetube.main;

import java.io.File;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

public class SoundMeasurement {
	public static final int MINIMUM_DB = 20;
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
	
	public void start() {
		bufferSize = AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT);
		mBuffer = new short[bufferSize];
		mRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC, 44100, AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT, bufferSize);
		mRecorder.startRecording();
	}
	
	public void measure() {
		short[] output = new short[bufferSize];
		double sum = 0;
		int readSize = mRecorder.read(mBuffer, 0, mBuffer.length);
		for (int i = 0; i < readSize; i++) {
			sum += mBuffer[i] * mBuffer[i];
		}
		if (readSize > 0) {
			final double amplitude = sum / readSize;
			int lvl = ((int) Math.sqrt(amplitude)); //TODO make it possible to calibrate this value
			lvl = (int) (2 + (20* Math.log10(lvl))); // A weighting
			
			
			
			if (lvl < MINIMUM_DB) {
				dbLevel = -10001;
				dbPercent = 0;
			} else if (lvl > MAXIMUM_DB) {
				dbLevel = -10000;
				dbPercent = 0;
			} else {
				dbLevel = lvl;
				double spread = MAXIMUM_DB-MINIMUM_DB;
				double spreadMulti = 100.0/spread;
				dbPercent = 100 - (int) (spreadMulti * (lvl - MINIMUM_DB));
				Log.v("measure() 1", "db lvl: " + lvl + "and db percent: " + dbPercent);
			}
		}
	}
	
	public void stop() {
		mRecorder.stop();
	}
	
}
