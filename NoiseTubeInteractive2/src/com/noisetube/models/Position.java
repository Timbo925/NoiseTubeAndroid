package com.noisetube.models;

import java.util.HashMap;
import java.util.Map;

public class Position {

	private Double x;
	private Double y;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Position withX(Double x) {
		this.x = x;
		return this;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Position withY(Double y) {
		this.y = y;
		return this;
	}


	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}