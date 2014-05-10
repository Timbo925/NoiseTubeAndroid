package com.noisetube.models;

import java.io.Serializable;
import java.util.List;

import com.noisetube.main.LocationMeasurment;
import com.noisetube.main.PointMeasurement;
import com.noisetube.main.SoundMeasurement;


public class PostRequest implements Serializable{
	
	private SoundMeasurement soundMeasurement;
	private PointMeasurement pointMeasurement;
	private LocationMeasurment locationMeasurment;
	
	public static final String PARAM_POSTRESPONSE = "PostResponse";

	public PostRequest(SoundMeasurement soundMeasurement, PointMeasurement pointMeasurement, LocationMeasurment locationMeasurment) {
		this.soundMeasurement = soundMeasurement;
		this.pointMeasurement = pointMeasurement;
		this.locationMeasurment = locationMeasurment;
	}

	/**
	 * @return the soundMeasurement
	 */
	public SoundMeasurement getSoundMeasurement() {
		return soundMeasurement;
	}

	/**
	 * @param soundMeasurement the soundMeasurement to set
	 */
	public void setSoundMeasurement(SoundMeasurement soundMeasurement) {
		this.soundMeasurement = soundMeasurement;
	}

	/**
	 * @return the pointMeasurement
	 */
	public PointMeasurement getPointMeasurement() {
		return pointMeasurement;
	}

	/**
	 * @param pointMeasurement the pointMeasurement to set
	 */
	public void setPointMeasurement(PointMeasurement pointMeasurement) {
		this.pointMeasurement = pointMeasurement;
	}

	/**
	 * @return the locationMeasurment
	 */
	public LocationMeasurment getLocationMeasurment() {
		return locationMeasurment;
	}

	/**
	 * @param locationMeasurment the locationMeasurment to set
	 */
	public void setLocationMeasurment(LocationMeasurment locationMeasurment) {
		this.locationMeasurment = locationMeasurment;
	}
	
	
	
}
