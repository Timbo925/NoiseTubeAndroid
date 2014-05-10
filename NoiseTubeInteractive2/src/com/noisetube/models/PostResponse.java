package com.noisetube.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representation of the response from server when results are posted.
 * This contains Points for the measurement, new user Stats and list of possible Badges unlocked 
 * @author Tim
 *
 */
public class PostResponse implements Serializable{
	
	private static final long serialVersionUID = 2991148424155562339L;
	public static final String PARAM_POSTRESPONSE = "PostResponse";
	
	@JsonProperty("stats")
	private Stats stats;
	@JsonProperty("points")
	private Points points;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("stats")
	public Stats getStats() {
	return stats;
	}

	@JsonProperty("stats")
	public void setStats(Stats stats) {
	this.stats = stats;
	}

	@JsonProperty("points")
	public Points getPoints() {
	return points;
	}

	@JsonProperty("points")
	public void setPoints(Points points) {
	this.points = points;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}


	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
