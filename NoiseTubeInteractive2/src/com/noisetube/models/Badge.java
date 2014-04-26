package com.noisetube.models;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Representation of a badge in listView
 * @author Tim
 *
 */
public class Badge implements Serializable {

	@Override
	public String toString() {
		return "Badge [icon=" + icon + ", name=" + name + ", description="
				+ description + ", achieved=" + achieved + "]";
	}

	private static final long serialVersionUID = 1L;
	private String icon;
	private String name;
	private String description;
	private boolean achieved;

	/**
	 * @param icon - icon name in drawables
	 * @param name - name of the icon
	 * @param description - dadge description
	 */
	public Badge(String icon, String name, String description, boolean achieved) {
		super();
		this.icon = icon;
		this.name = name;
		this.description = description;
		this.achieved = achieved;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the achieved
	 */
	public boolean isAchieved() {
		return achieved;
	}

	/**
	 * @param achieved the achieved to set
	 */
	public void setAchieved(boolean achieved) {
		this.achieved = achieved;
	}

}
