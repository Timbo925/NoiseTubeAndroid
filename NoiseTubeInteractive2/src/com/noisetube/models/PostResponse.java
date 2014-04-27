package com.noisetube.models;

import java.io.Serializable;
import java.util.List;

/**
 * Representation of the response from server when results are posted.
 * This contains Points for the measurement, new user Stats and list of possible Badges unlocked 
 * @author Tim
 *
 */
public class PostResponse implements Serializable{
	
	private Points points;
	private Stats stats;
	private List<Badge> badges;
	
	private static final long serialVersionUID = 2991148424155562339L;
	public static final String PARAM_POSTRESPONSE = "PostResponse";

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PostResponse [points=" + points + ", stats=" + stats
				+ ", badges=" + badges + "]";
	}

	/**
	 * @return the points
	 */
	public Points getPoints() {
		return points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(Points points) {
		this.points = points;
	}

	/**
	 * @return the stats
	 */
	public Stats getStats() {
		return stats;
	}

	/**
	 * @param stats the stats to set
	 */
	public void setStats(Stats stats) {
		this.stats = stats;
	}

	/**
	 * @return the basges
	 */
	public List<Badge> getBadges() {
		return badges;
	}

	/**
	 * @param basges the basges to set
	 */
	public void setBadges(List<Badge> badges) {
		this.badges = badges;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
