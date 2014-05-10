package com.noisetube.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.Gson;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"points",
"multi_place",
"multi_time",
"multi_special"
})
public class Points implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String POINTS = "POINTS";


	@JsonProperty("points")
	private Integer points;
	@JsonProperty("multi_place")
	private double multi_place;
	@JsonProperty("multi_time")
	private double multi_time;
	@JsonProperty("multi_special")
	private double multi_special;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	
	@Override
	public String toString() {
		return "Points [points=" + points + ", multi_place=" + multi_place
				+ ", multi_time=" + multi_time + ", multi_special="
				+ multi_special + ", additionalProperties="
				+ additionalProperties + "]";
	}
	public String toJsonString() {
		Gson gson = new Gson();
		return gson.toJson(this).toString();
	}
	@JsonProperty("points")
	public Integer getPoints() {
		return points;
	}

	@JsonProperty("points")
	public void setPoints(Integer points) {
		this.points = points;
	}

	@JsonProperty("multi_place")
	public double getMulti_place() {
		return multi_place;
	}

	@JsonProperty("multi_place")
	public void setMulti_place(double multi_place) {
		this.multi_place = multi_place;
	}

	@JsonProperty("multi_time")
	public double getMulti_time() {
		return multi_time;
	}

	@JsonProperty("multi_time")
	public void setMulti_time(double multi_time) {
		this.multi_time = multi_time;
	}

	@JsonProperty("multi_special")
	public double getMulti_special() {
		return multi_special;
	}

	@JsonProperty("multi_special")
	public void setMulti_special(double multi_special) {
		this.multi_special = multi_special;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
