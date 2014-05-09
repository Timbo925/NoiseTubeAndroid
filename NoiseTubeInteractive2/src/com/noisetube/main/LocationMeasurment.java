package com.noisetube.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class LocationMeasurment implements LocationListener, Serializable {

	
	
	private static final long serialVersionUID = 8406332998661787571L;
	//transient allows values to be excluded in serialization
	private transient Context context;
	private List<Double> locationList = new ArrayList<Double>();
	private List<Float> accuracy = new ArrayList<Float>();
	private double lastLat;
	private double lastLon;
	private transient LocationManager locationManager;
	private String provider;
	private transient Location location;
	
	public LocationMeasurment(Context context) {
		this.context = context;
		Log.i("LocationMeasurment", "LocationManager created with context: " + context);
		
		// Retrieving the location manager
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		// Setting up cretaria for the location managers
		Criteria criteria = new Criteria();
	    provider = locationManager.getBestProvider(criteria, false);
	    //Updating to last known location. Might not be accurate in the beginning
	    location = locationManager.getLastKnownLocation(provider);
	    lastLat = location.getLatitude();
		lastLon = location.getLongitude();
		accuracy.add(location.getAccuracy());
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
	    
	}
	
	void measure () {
		
		locationList.add(location.getLatitude());
		locationList.add(location.getLongitude());
		accuracy.add(location.getAccuracy());
		lastLat = location.getLatitude();
		lastLon = location.getLongitude();
		Log.d("LocatinMeasurment", "Location: " + location.getLatitude() + "/" + location.getLongitude() + " with provider: " + provider + " and accuracy: " + location.getAccuracy());
	}
	
	void stop() {
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		//Keeping location up to date on change.
		Log.d("LocatinMeasurment", "New Location: " + location.getLatitude() + "/" + location.getLongitude() + " with provider: " + provider);
		this.location = location;
		//TODO update the list
	}

	@Override
	public void onProviderDisabled(String provider) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {	
	}

	/**
	 * @return the locationList
	 */
	public List<Double> getLocationList() {
		return locationList;
	}

	/**
	 * @param locationList the locationList to set
	 */
	public void setLocationList(List<Double> locationList) {
		this.locationList = locationList;
	}

	/**
	 * @return the lastLat
	 */
	public double getLastLat() {
		return lastLat;
	}

	/**
	 * @param lastLat the lastLat to set
	 */
	public void setLastLat(double lastLat) {
		this.lastLat = lastLat;
	}

	/**
	 * @return the lastLon
	 */
	public double getLastLon() {
		return lastLon;
	}

	/**
	 * @param lastLon the lastLon to set
	 */
	public void setLastLon(double lastLon) {
		this.lastLon = lastLon;
	}
}
