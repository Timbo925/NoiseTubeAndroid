package com.noisetube.models;

import java.io.Serializable;

import com.google.gson.Gson;

import android.content.SharedPreferences;

public class Stats implements Serializable{
	
	private int level;
	private int exp;
	private int expMax;
	private int amount;
	private int time;
	
	private static final long serialVersionUID = 1705459163208937073L;
	private static final String STATS = "STATS";
	
	public String toJsonString() {
		Gson gson = new Gson();
		return gson.toJson(this).toString();
	}
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * @return the exp
	 */
	public int getExp() {
		return exp;
	}
	/**
	 * @param exp the exp to set
	 */
	public void setExp(int exp) {
		this.exp = exp;
	}
	/**
	 * @return the expMax
	 */
	public int getExpMax() {
		return expMax;
	}
	/**
	 * @param expMax the expMax to set
	 */
	public void setExpMax(int expMax) {
		this.expMax = expMax;
	}
	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}
	
	
	
}
