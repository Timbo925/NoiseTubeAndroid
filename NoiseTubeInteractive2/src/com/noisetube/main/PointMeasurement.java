package com.noisetube.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.noisetube.models.Poi;
import com.noisetube.models.Position;
import com.vub.storage.PoiStorage;

public class PointMeasurement implements Serializable {

	private static final long serialVersionUID = 5762611276853002477L;
	private List<Integer> poiIds = new ArrayList<Integer>(); //list to track poiId that are already included in the multiplier
	private transient Context context;
	private transient PoiStorage poiStorage;
	public int points = 0;
	public double pointsReal = 0;
	public int bonusPoints;
	public double locationMultiplier;	

	public PointMeasurement(Context context) {
		this.context = context;
		System.out.println("PoiStorage with context");
		poiStorage = new PoiStorage(context);
		poiStorage.update();
	}

	public int getTotalPoints() {
		return (int) (points + bonusPoints);
	}

	public void measure(double lat, double lon) {
		//TODO - get location
		//	   - inside POI?
		try {
			List<Poi> pois = poiStorage.getPoiList();
			Log.d("PointMeasurement.measure", "The Poi List: " + pois );
			double multi = 1;
			for (Poi poi: pois) {
				Position tesetPos = new Position((float) lat, (float) lon);
				//System.out.println(poi.getBonusMulti());
				boolean inside = false;
				if (poi.getPosition().size() > 2) {
					//We have a polygon
					inside = tesetPos.insidePolygon(poi.getPositions());
					//Log.d("PointMeasurment", "Polygon test: " + inside + " poiId: " + poi.getIdPoi());
				} else if (poi.getPosition().size() == 2) {
					inside = tesetPos.inRangePosition(poi.getPositions().get(0), poi.getRadius());
					//Log.d("PointMeasurment", "Point test: " + inside + " poiId: " + poi.getIdPoi());
				} else {
					inside = false;
					//Log.d("PointMeasurment", "Unknown size: " + inside + " poiId: " + poi.getIdPoi());
				}
				
				if (inside) {
					Log.d("PointMeasurment", "Inside Poi: " + poi);
				//	System.out.println("Recieved List: " + poiIds);
					multi += poi.getBonusMulti();
					//System.out.println("New Multi: " + multi);
					if (!poiIds.contains(poi.getIdPoi())) {
						//We have not yet given the bonus for this POI
						//System.out.println("Give Bonus: " + poi.getBonusPoints());
						this.bonusPoints += poi.getBonusPoints();
						poiIds.add(poi.getIdPoi());
					}
					
				}
			}
			
			//System.out.println("Adding Points: " + 1*multi + " Real Points: " + pointsReal);
			pointsReal += 1 * multi;
			points = (int) pointsReal; //We round the points number for better looks
			locationMultiplier = Math.round(multi * 100.0) / 100.0;
			
		} catch (NullPointerException e) {
			System.out.println("Update()");
			poiStorage.update();
			e.printStackTrace();
		}
	}




	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}




	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}




	/**
	 * @return the bonusPoints
	 */
	public int getBonusPoints() {
		return bonusPoints;
	}




	/**
	 * @param bonusPoints the bonusPoints to set
	 */
	public void setBonusPoints(int bonusPoints) {
		this.bonusPoints = bonusPoints;
	}




	/**
	 * @return the locationMultiplier
	 */
	public double getLocationMultiplier() {
		return locationMultiplier;
	}




	/**
	 * @param locationMultiplier the locationMultiplier to set
	 */
	public void setLocationMultiplier(double locationMultiplier) {
		this.locationMultiplier = locationMultiplier;
	}



}
