package com.noisetube.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Poi {

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