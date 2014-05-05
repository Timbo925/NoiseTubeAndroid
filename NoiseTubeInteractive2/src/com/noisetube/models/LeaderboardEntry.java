package com.noisetube.models;

import java.util.HashMap;
import java.util.Map;

public class LeaderboardEntry {

	private int rank;
	private int idStats;
	private int exp;
	private int level;
	private int amountMeasurments;
	private String totalTime;
	private int maxExp;
	private String userName;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	
	@Override
	public String toString() {
		return "LeaderboardEntry [rank=" + rank + ", idStats=" + idStats
				+ ", exp=" + exp + ", level=" + level + ", amountMeasurments="
				+ amountMeasurments + ", totalTime=" + totalTime + ", maxExp="
				+ maxExp + ", userName=" + userName + ", additionalProperties="
				+ additionalProperties + "]";
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public LeaderboardEntry withRank(int rank) {
		this.rank = rank;
		return this;
	}

	public int getIdStats() {
		return idStats;
	}

	public void setIdStats(int idStats) {
		this.idStats = idStats;
	}

	public LeaderboardEntry withIdStats(int idStats) {
		this.idStats = idStats;
		return this;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public LeaderboardEntry withExp(int exp) {
		this.exp = exp;
		return this;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public LeaderboardEntry withLevel(int level) {
		this.level = level;
		return this;
	}

	public int getAmountMeasurments() {
		return amountMeasurments;
	}

	public void setAmountMeasurments(int amountMeasurments) {
		this.amountMeasurments = amountMeasurments;
	}

	public LeaderboardEntry withAmountMeasurments(int amountMeasurments) {
		this.amountMeasurments = amountMeasurments;
		return this;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public LeaderboardEntry withTotalTime(String totalTime) {
		this.totalTime = totalTime;
		return this;
	}

	public int getMaxExp() {
		return maxExp;
	}

	public void setMaxExp(int maxExp) {
		this.maxExp = maxExp;
	}

	public LeaderboardEntry withMaxExp(int maxExp) {
		this.maxExp = maxExp;
		return this;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LeaderboardEntry withUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}