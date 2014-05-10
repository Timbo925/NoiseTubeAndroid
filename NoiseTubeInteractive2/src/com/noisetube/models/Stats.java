package com.noisetube.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

public class Stats implements Serializable{

	@JsonProperty("idStats")
	private Integer idStats;
	@JsonProperty("exp")
	private Integer exp;
	@JsonProperty("level")
	private Integer level;
	@JsonProperty("amountMeasurments")
	private Integer amountMeasurments;
	@JsonProperty("totalTime")
	private String totalTime;
	@JsonProperty("maxExp")
	private Integer maxExp;
	@JsonProperty("nextLevel")
	private Integer nextLevel;
	@JsonProperty("lastLevel")
	private Integer lastLevel;

	private static final long serialVersionUID = 1705459163208937073L;
	public static final String STATS = "STATS";

	public String toJsonString() {
		Gson gson = new Gson();
		return gson.toJson(this).toString();
	}

	@JsonProperty("lastLevel")
	public Integer getLastLevel() {
		return lastLevel;
	}
	
	@JsonProperty("lastLevel")
	public void setLastLevel(Integer lastLevel) {
		this.lastLevel = lastLevel;
	}

	@JsonProperty("nextLevel")
	public Integer getNextLevel() {
		return nextLevel;
	}
	
	@JsonProperty("nextLevel")
	public void setNextLevel(Integer netxLevel) {
		this.nextLevel = netxLevel;
	}

	@JsonProperty("idStats")
	public Integer getIdStats() {
		return idStats;
	}

	@JsonProperty("idStats")
	public void setIdStats(Integer idStats) {
		this.idStats = idStats;
	}

	@JsonProperty("exp")
	public Integer getExp() {
		return exp;
	}

	@JsonProperty("exp")
	public void setExp(Integer exp) {
		this.exp = exp;
	}

	@JsonProperty("level")
	public Integer getLevel() {
		return level;
	}

	@JsonProperty("level")
	public void setLevel(Integer level) {
		this.level = level;
	}

	@JsonProperty("amountMeasurments")
	public Integer getAmountMeasurments() {
		return amountMeasurments;
	}

	@JsonProperty("amountMeasurments")
	public void setAmountMeasurments(Integer amountMeasurments) {
		this.amountMeasurments = amountMeasurments;
	}

	@JsonProperty("totalTime")
	public String getTotalTime() {
		return totalTime;
	}

	@JsonProperty("totalTime")
	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	@JsonProperty("maxExp")
	public Integer getMaxExp() {
		return maxExp;
	}

	@JsonProperty("maxExp")
	public void setMaxExp(Integer maxExp) {
		this.maxExp = maxExp;
	}
}
