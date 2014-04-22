package com.noisetube.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.os.SystemClock;

public class SoundMeasurement implements Serializable {

	private static final long serialVersionUID = 2901906860850977163L;
	private int dbAvg = 0;
	private int dbMax = 0;
	private int dbMin = 100;
	private int dbLast = 0;
	private double dbPercent;
	//private DbMeter dbMeter = new DbMeter();
	private List<Integer> dbList = new ArrayList<Integer>();
	private int counter = 0;
	private long startTime = SystemClock.elapsedRealtime();
	private long endTime = SystemClock.elapsedRealtime();

	
	//TODO post() function to server with all measurements
	
	public void reset() {
		dbAvg = 0;
		dbMax = 0;
		dbMin = 100;
		dbPercent = 0;
		counter = 0;
		dbList.clear();
		startTime = SystemClock.elapsedRealtime();
		endTime = SystemClock.elapsedRealtime();
	}
	
	
	public void measure() {
		DbMeter dbMeter = new DbMeter();
		int db = dbMeter.measure();  //Returns new db level measured
		dbList.add(db);
		updateStats(db);

		//TODO Retreive location
	}

	private void updateStats(int db) {
		
		//No update to the stats when DbMeter didn't return a good value;
		if (db != 0) {
			
			//Update regular amount of measurements + last db measurement
			counter++;
			dbLast = db;
			endTime = SystemClock.elapsedRealtime();
			
			//Calculation of percentage based on range DbMeter 
			double spread = DbMeter.MAXIMUM_DB-DbMeter.MINIMUM_DB;
			double spreadMulti = 100.0/spread;
			dbPercent = (int) (spreadMulti * (db - DbMeter.MINIMUM_DB));
			
			//Update Average/Min/Max
			dbAvg = ((dbAvg * (counter - 1)) + db) / counter ;
			if (db > dbMax) {dbMax = db;}
			if (db < dbMin) {dbMin = db;}
		}	
	}


	public long getTime() {
		return endTime - startTime;
	}
	/**
	 * @return the dbAvg
	 */
	public int getDbAvg() {
		return dbAvg;
	}


	/**
	 * @return the dbMax
	 */
	public int getDbMax() {
		return dbMax;
	}


	/**
	 * @return the dbMin
	 */
	public int getDbMin() {
		return dbMin;
	}


	/**
	 * @return the dbLast
	 */
	public int getDbLast() {
		return dbLast;
	}


	/**
	 * @return the dbPercent
	 */
	public double getDbPercent() {
		return dbPercent;
	}


	/**
	 * @return the dbList
	 */
	public List<Integer> getDbList() {
		return dbList;
	}


	/**
	 * @param dbList the dbList to set
	 */
	public void setDbList(List<Integer> dbList) {
		this.dbList = dbList;
	}


	/**
	 * @return the counter
	 */
	public int getCounter() {
		return counter;
	}


	/**
	 * @param counter the counter to set
	 */
	public void setCounter(int counter) {
		this.counter = counter;
	}


	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}


	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}


	/**
	 * @return the endTime
	 */
	public long getEndTime() {
		return endTime;
	}


	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}


	/**
	 * @param dbAvg the dbAvg to set
	 */
	public void setDbAvg(int dbAvg) {
		this.dbAvg = dbAvg;
	}


	/**
	 * @param dbMax the dbMax to set
	 */
	public void setDbMax(int dbMax) {
		this.dbMax = dbMax;
	}


	/**
	 * @param dbMin the dbMin to set
	 */
	public void setDbMin(int dbMin) {
		this.dbMin = dbMin;
	}


	/**
	 * @param dbLast the dbLast to set
	 */
	public void setDbLast(int dbLast) {
		this.dbLast = dbLast;
	}


	/**
	 * @param dbPercent the dbPercent to set
	 */
	public void setDbPercent(double dbPercent) {
		this.dbPercent = dbPercent;
	}
	
}
