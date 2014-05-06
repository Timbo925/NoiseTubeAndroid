package com.noisetube.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;



public class Poi implements Comparable<Poi>{

	private int idPoi;
	private String name;
	private List<Float> position = new ArrayList<Float>();
	private String description;
	private int bonusPoints;
	private float bonusMulti;
	private int radius;
	private String type;
	private Double distance;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonIgnore
	public List<Position> getPoints() {
		List<Position> points = new ArrayList<Position>();
		for (int i = 0; i < position.size()/2; i = i +2) {
			points.add(new Position(position.get(i), position.get(i+1)));
		}
		return points;
	}
	
	@JsonIgnore
	public void setPoints(List<Position> positions) {
		List<Float> floats = new ArrayList<Float>();
		for (Position p: positions) {
			floats.add(p.getX());
			floats.add(p.getY());
		}
		this.position = floats;
	}

	public int compareTo (Poi poi) {
		if (poi.radius < radius) {
			return -1;
		} else if (poi.radius == radius) {
			return 0;
		} else {
			return 1;
		}
	}
	public int getIdPoi() {
		return idPoi;
	}

	public void setIdPoi(int idPoi) {
		this.idPoi = idPoi;
	}

	public Poi withIdPoi(int idPoi) {
		this.idPoi = idPoi;
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Poi withName(String name) {
		this.name = name;
		return this;
	}

	public List<Float> getPosition() {
		return position;
	}

	public void setPosition(List<Float> position) {
		this.position = position;
	}

	public Poi withPosition(List<Float> position) {
		this.position = position;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Poi withDescription(String description) {
		this.description = description;
		return this;
	}

	public int getBonusPoints() {
		return bonusPoints;
	}

	public void setBonusPoints(int bonusPoints) {
		this.bonusPoints = bonusPoints;
	}

	public Poi withBonusPoints(int bonusPoints) {
		this.bonusPoints = bonusPoints;
		return this;
	}

	public float getBonusMulti() {
		return bonusMulti;
	}

	public void setBonusMulti(float bonusMulti) {
		this.bonusMulti = bonusMulti;
	}

	public Poi withBonusMulti(float bonusMulti) {
		this.bonusMulti = bonusMulti;
		return this;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public Poi withRadius(int radius) {
		this.radius = radius;
		return this;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Poi withType(String type) {
		this.type = type;
		return this;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Poi withDistance(Double distance) {
		this.distance = distance;
		return this;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}