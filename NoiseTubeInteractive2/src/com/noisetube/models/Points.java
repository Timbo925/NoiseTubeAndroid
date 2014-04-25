package com.noisetube.models;

import java.io.Serializable;

public class Points implements Serializable {

	private static final long serialVersionUID = 1L;
	private int points;
	private double multiplierLocation;
	private double multiplierTime;
	private double multiplierSpecial;
	private int oldLvl;
	private int newLvl;
	private int OldExp;
	private int OldMax;
	private int NewExp;
	private int NewMax;
	public static final String POINTS = "POINTS";
	
	
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
	public int getOldLvl() {
		return oldLvl;
	}
	/**
	 * @param oldLvl the oldLvl to set
	 */
	public void setOldLvl(int oldLvl) {
		this.oldLvl = oldLvl;
	}
	/**
	 * @return the newLvl
	 */
	public int getNewLvl() {
		return newLvl;
	}
	/**
	 * @param newLvl the newLvl to set
	 */
	public void setNewLvl(int newLvl) {
		this.newLvl = newLvl;
	}
	/**
	 * @return the oldExp
	 */
	public int getOldExp() {
		return OldExp;
	}
	/**
	 * @param oldExp the oldExp to set
	 */
	public void setOldExp(int oldExp) {
		OldExp = oldExp;
	}
	/**
	 * @return the oldMax
	 */
	public int getOldMax() {
		return OldMax;
	}
	/**
	 * @param oldMax the oldMax to set
	 */
	public void setOldMax(int oldMax) {
		OldMax = oldMax;
	}
	/**
	 * @return the newExp
	 */
	public int getNewExp() {
		return NewExp;
	}
	/**
	 * @param newExp the newExp to set
	 */
	public void setNewExp(int newExp) {
		NewExp = newExp;
	}
	/**
	 * @return the newMax
	 */
	public int getNewMax() {
		return NewMax;
	}
	/**
	 * @param newMax the newMax to set
	 */
	public void setNewMax(int newMax) {
		NewMax = newMax;
	}
	
	
}
