package com.noisetube.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PointMeasurement implements Serializable {

	private static final long serialVersionUID = 5762611276853002477L;
	private List<Integer> poiIds = new ArrayList<Integer>(); //list to track poiId that are already included in the multiplier

	public int points = 0;
	public int bonusPoints;
	public double locationMultiplier;


	public int getTotalPoints() {
		return (int) ((points + bonusPoints) * locationMultiplier);
	}

	public void measure() {
		//TODO - get location
		//	   - inside POI?
		points++;
		bonusPoints = 20;
		locationMultiplier = 1.1;

	}




	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}




	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}




	/**
	 * @return the bonusPoints
	 */
	public int getBonusPoints() {
		return bonusPoints;
	}




	/**
	 * @param bonusPoints the bonusPoints to set
	 */
	public void setBonusPoints(int bonusPoints) {
		this.bonusPoints = bonusPoints;
	}




	/**
	 * @return the locationMultiplier
	 */
	public double getLocationMultiplier() {
		return locationMultiplier;
	}




	/**
	 * @param locationMultiplier the locationMultiplier to set
	 */
	public void setLocationMultiplier(double locationMultiplier) {
		this.locationMultiplier = locationMultiplier;
	}



}
