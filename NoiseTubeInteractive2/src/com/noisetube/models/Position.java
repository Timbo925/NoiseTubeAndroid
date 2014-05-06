package com.noisetube.models;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"x",
	"y"
})
public class Position {

	@JsonProperty("x")
	private float x;
	@JsonProperty("y")
	private float y;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Position(float x, float y) {
		this.x = x;
		this.y = y;
	}
	@JsonProperty("x")
	public float getX() {
		return x;
	}

	@JsonProperty("x")
	public void setX(float x) {
		this.x = x;
	}

	public Position withX(float x) {
		this.x = x;
		return this;
	}

	@JsonProperty("y")
	public float getY() {
		return y;
	}

	@JsonProperty("y")
	public void setY(float y) {
		this.y = y;
	}

	public Position withY(float y) {
		this.y = y;
		return this;
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