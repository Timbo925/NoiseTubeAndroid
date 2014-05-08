package com.noisetube.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

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

	/**
	 * @param positions - List of positions representing the polygon
	 * @return - returns true if this point is inside the polygon. Otherwise false.
	 */
	public boolean insidePolygon(List<Position> positions) {

		List<Float> polyX = new ArrayList<Float>();
		List<Float> polyY = new ArrayList<Float>();

		for (Position pos : positions) {
			polyX.add(pos.x);
			polyY.add(pos.y);
		}

		double minX = Collections.min(polyX);
		double maxX = Collections.max(polyX);
		double minY = Collections.min(polyY);
		double maxY = Collections.max(polyY);

		//Checking the outer bounds of the polygon. 
		//If this isn't satisfied no furture calcuations are nessesary.

		if (this.x < minX || this.x > maxX || this.y < minY || this.y > maxY) {
			return false;
		}

		//Detection inside a list of of coordinates. Based on ras casting
		//Code based on http://www.ecse.rpi.edu/
		int i, j, c = 0;
		int nvert = polyX.size();
		for (i = 0, j = nvert-1; i < nvert; j = i++) {
		//	System.out.println("j: " + j + " i: " + i + " polyY.i: " + polyY.get(i) + " polyY.j: " + polyY.get(j) + " polyX.i: " + polyX.get(i) + " polyX.j: " + polyX.get(j));
			if ( ((polyY.get(i)>this.y) != (polyY.get(j)>this.y)) && 
				 (this.x < (polyX.get(j)-polyX.get(i)) * (this.y-polyY.get(i)) 
						 / (polyY.get(j)-polyY.get(i)) + polyX.get(i)) )
				c++;
		}
		//System.out.println("c: " + c);
		if (c % 2 == 0) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 
	 * @param position - Postion of a point to test
	 * @param radius - Range of the given point in km
	 * @return true if point in range of this point
	 * 
	 * Calculations based on 'The haversine formula'
	 */
	public boolean inRangePosition(Position position, int radius) {
		
		double toRad = Math.PI / 180;
		int R = 6371;
		double p1 = position.x * toRad;
		double p2 = this.x * toRad;
		double deltaP = (this.x - position.x) * toRad;
		double deltaL = (this.y - position.y) * toRad;
		double a =  (Math.sin(deltaP / 2) * Math.sin(deltaP/2)) + (Math.cos(p1) * Math.cos(p2) * Math.sin(deltaL / 2) * Math.sin(deltaL/2));
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double d =  R * c;
		
		//System.out.println("Calcualted distance: " + d + " compared with radious: " + radius + " Point 1: " + this.x + "/" + this.y + " Point2 " + position.x + "/" + position.y);

		if (d < radius) {
			return true;
		} else {
			return false;
		}
	}

}