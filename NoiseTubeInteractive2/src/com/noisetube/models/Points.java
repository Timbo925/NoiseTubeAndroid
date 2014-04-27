package com.noisetube.models;

import java.io.Serializable;

import com.google.gson.Gson;

public class Points implements Serializable {

	private static final long serialVersionUID = 1L;
	private int points;
	private double multiplierLocation;
	private double multiplierTime;
	private double multiplierSpecial;
	public static final String POINTS = "POINTS";
	
	public String toJsonString() {
		Gson gson = new Gson();
		return gson.toJson(this).toString();
	}
	
	public long getPointsTotal() {
		return Math.round( points*multiplierLocation*multiplierSpecial*multiplierTime);
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
	 * @return the multiplierLocation
	 */
	public double getMultiplierLocation() {
		return multiplierLocation;
	}
	/**
	 * @param multiplierLocation the multiplierLocation to set
	 */
	public void setMultiplierLocation(double multiplierLocation) {
		this.multiplierLocation = multiplierLocation;
	}
	/**
	 * @return the multiplierTime
	 */
	public double getMultiplierTime() {
		return multiplierTime;
	}
	/**
	 * @param multiplierTime the multiplierTime to set
	 */
	public void setMultiplierTime(double multiplierTime) {
		this.multiplierTime = multiplierTime;
	}
	/**
	 * @return the multiplierSpecial
	 */
	public double getMultiplierSpecial() {
		return multiplierSpecial;
	}
	/**
	 * @param multiplierSpecial the multiplierSpecial to set
	 */
	public void setMultiplierSpecial(double multiplierSpecial) {
		this.multiplierSpecial = multiplierSpecial;
	}
	/**
	 * @return the oldLvl
	 */
	
}
