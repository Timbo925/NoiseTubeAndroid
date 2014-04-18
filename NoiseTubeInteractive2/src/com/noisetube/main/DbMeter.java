package com.noisetube.main;

import java.io.File;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

public class DbMeter {

	private AudioRecord mRecorder;
	private File mRecording;
	private short[] mBuffer;
	private final String startRecordingLabel = "Start recording";
	private final String stopRecordingLabel = "Stop recording";
	private boolean mIsRecording = false;
	private int bufferSize;
	public static final int MINIMUM_DB = 10;
	public static final int MAXIMUM_DB = 100;
	private static final int sampleRate = 44100;
	private int dbLevel = 0;
	
	public DbMeter() {
		bufferSize = AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT);
		bufferSize = bufferSize *4;
		mBuffer = new short[bufferSize];
		mRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC, 44100, AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT, bufferSize*4);
	}
	
	public int measure() {
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
			double x = average/(bufferSize-1);
		    double db=0;
		    // calculating the pascal pressure based on the idea that the max amplitude (between 0 and 32767) is 
		    // relative to the pressure
		    double pressure = x/51805.5336; //the value 51805.5336 can be derived from asuming that x=32767=0.6325 Pa and x=1 = 0.00002 Pa (the reference value)
		    db = (20 * Math.log10(pressure/0.00002));
		    
		    db = lvl;

			if (db < MINIMUM_DB) {
				dbLevel = 0;
			} else if (db > MAXIMUM_DB) {
				dbLevel = 0;
				//dbPercent = 100;
			} else {
				dbLevel = (int) db;
		
				
				//Log.d("measure() 2", "db=[" + (int) db  + "],average=[" + average + "] ,pressure=[" + pressure + "]");
			}
		}
		return dbLevel;
	}

	public static int getMinimumDb() {
		return MINIMUM_DB;
	}

	public static int getMaximumDb() {
		return MAXIMUM_DB;
	}
	
	
}
