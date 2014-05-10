package com.noisetube.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class LocationMeasurment implements Serializable, GooglePlayServicesClient.ConnectionCallbacks,GooglePlayServicesClient.OnConnectionFailedListener,LocationListener {



	private static final long serialVersionUID = 8406332998661787571L;
	//transient allows values to be excluded in serialization
	private transient Context context;
	private List<Double> locationList = new ArrayList<Double>();
	private List<Float> accuracy = new ArrayList<Float>();
	private double lastLat = 0.0;
	private double lastLon = 0.0;
	//private transient LocationManager locationManager;
	//private String provider;
	private transient Location location;

	private transient LocationClient locationclient;
	private transient LocationRequest locationrequest;
	boolean connected = false;

	public LocationMeasurment(Context context) {
		this.context = context;
		Log.i("LocationMeasurment", "LocationManager created with context: " + context);
		locationclient = new LocationClient(context, this, this);
		locationclient.connect();


	}

	void measure () {
		if (connected) {
			Log.d("LocationMeasurment", "Measuring");
			locationList.add(location.getLatitude());
			locationList.add(location.getLongitude());
			accuracy.add(location.getAccuracy());
			lastLat = location.getLatitude();
			lastLon = location.getLongitude();
			Log.d("LocatinMeasurment", "Location: " + location.getLatitude() + "/" + location.getLongitude() + " and accuracy: " + location.getAccuracy());
		} else {
			Log.d("LocatinMeasurment", "Not Connected");
		}

	}

	void stop() {
		locationclient.disconnect();
	}

	@Override
	public void onLocationChanged(Location location) {
		if(location!=null){
			Log.i("LocationMeasurment", "Location Request on Change:" + location.getLatitude() + "," + location.getLongitude());
			this.location = location;
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		Log.i("LocationMeasurment", "Connected");
		locationrequest = LocationRequest.create();
		locationrequest.setInterval(100);
		locationclient.requestLocationUpdates(locationrequest, this);
		location = locationclient.getLastLocation();
		Log.i("LocationMeasurment", "Last Known Location :" + location.getLatitude() + "," + location.getLongitude());
		connected = true;

	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

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
