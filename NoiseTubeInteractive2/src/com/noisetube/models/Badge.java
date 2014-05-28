package com.noisetube.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representation of a badge in listView
 * @author Tim
 *
 */
public class Badge implements Serializable {

	private static final long serialVersionUID = 2855015009572643325L;
	private boolean achieved = false;
	@JsonProperty("idBadge")
	private Integer idBadge;
	@JsonProperty("name")
	private String name;
	@JsonProperty("description")
	private String description;
	@JsonProperty("challange")
	private String challange;
	@JsonProperty("icon")
	private String icon;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Badge() {
		
	}
	public Badge(boolean achieved, Integer idBadge, String name, String description, String challange, String icon) {
		super();
		this.achieved = achieved;
		this.idBadge = idBadge;
		this.name = name;
		this.description = description;
		this.challange = challange;
		this.icon = icon;
	}
	

	@Override
	public String toString() {
		return "Badge [achieved=" + achieved + ", idBadge=" + idBadge
				+ ", name=" + name + ", description=" + description
				+ ", challange=" + challange + ", icon=" + icon + "]";
	}
	public boolean isAchieved() {
		return achieved;
	}

	public void setAchieved(boolean achieved) {
		this.achieved = achieved;
	}

	@JsonProperty("idBadge")
	public Integer getIdBadge() {
	return idBadge;
	}

	@JsonProperty("idBadge")
	public void setIdBadge(Integer idBadge) {
	this.idBadge = idBadge;
	}

	@JsonProperty("name")
	public String getName() {
	return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
	this.name = name;
	}

	@JsonProperty("description")
	public String getDescription() {
	return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
	this.description = description;
	}

	@JsonProperty("challange")
	public String getChallange() {
	return challange;
	}

	@JsonProperty("challange")
	public void setChallange(String challange) {
	this.challange = challange;
	}

	@JsonProperty("icon")
	public String getIcon() {
	return icon;
	}

	@JsonProperty("icon")
	public void setIcon(String icon) {
	this.icon = icon;
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
