package com.noisetube.main;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Profile implements Serializable {

	private static final long serialVersionUID = -7381178845691984820L;
	@JsonProperty("idUser")
	private Integer idUser;
	@JsonProperty("userName")
	private String userName;
	@JsonProperty("email")
	private String email;
	@JsonProperty("Stats_idStats")
	private Integer stats_idStats;
	@JsonProperty("password")
	private String password;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("idUser")
	public Integer getIdUser() {
		return idUser;
	}

	@JsonProperty("idUser")
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	@JsonProperty("userName")
	public String getUserName() {
		return userName;
	}

	@JsonProperty("userName")
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}

	@JsonProperty("Stats_idStats")
	public Integer getStats_idStats() {
		return stats_idStats;
	}

	@JsonProperty("Stats_idStats")
	public void setStats_idStats(Integer stats_idStats) {
		this.stats_idStats = stats_idStats;
	}

	@JsonProperty("password")
	public String getPassword() {
		return password;
	}

	@JsonProperty("password")
	public void setPassword(String password) {
		this.password = password;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Profile [idUser=" + idUser + ", userName=" + userName
				+ ", email=" + email + ", stats_idStats=" + stats_idStats
				+ ", password=" + password + ", additionalProperties="
				+ additionalProperties + "]";
	}
	
	

}
