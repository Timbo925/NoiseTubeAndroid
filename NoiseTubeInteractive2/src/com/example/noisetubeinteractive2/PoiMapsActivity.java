package com.example.noisetubeinteractive2;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.noisetube.models.Poi;
import com.noisetube.models.Position;
import com.vub.storage.PoiStorage;

public class PoiMapsActivity extends Activity {
	private GoogleMap map;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poi_maps);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);
		
		PoiStorage poiStorage = new PoiStorage(getApplicationContext());
		List<Poi> poiList = new ArrayList<Poi>();
		poiList = poiStorage.getPoiList();

		if (poiList != null) {
			for (Poi poi : poiList) {
				switch (poi.getType()) {
				case "POINT":
					MarkerOptions mo = new MarkerOptions();
					mo.position(new LatLng(poi.getPositions().iterator().next().getX(), poi.getPositions().iterator().next().getY()));
					mo.title(poi.getName());
					mo.snippet("Bonus Points: " + poi.getBonusPoints() + " | Bonus Multiplier" + poi.getBonusMulti());
					map.addMarker(mo);
					
					CircleOptions co = new CircleOptions();
					co.center(new LatLng(poi.getPositions().iterator().next().getX(), poi.getPositions().iterator().next().getY()));
					co.radius(poi.getRadius() * 1000);
					co.strokeColor(0x40333EE8);
					co.fillColor(0x20333EE8);
					map.addCircle(co);
					break;
				case "AREA":
					int ctr = 0;
					float averageX = 0;
					float averageY = 0;
					for (int i = 0; i<poi.getPosition().size(); i += 2) {
						averageX = ((averageX * ctr) + poi.getPosition().get(i)) / (ctr + 1);
						averageY = ((averageY * ctr) + poi.getPosition().get(i+1)) / (ctr + 1);
						ctr++;
					}
					
					MarkerOptions mo2 = new MarkerOptions();
					mo2.position(new LatLng(averageX, averageY));
					mo2.title(poi.getName());
					mo2.snippet("Bonus Points: " + poi.getBonusPoints() + " | Bonus Multiplier: " + poi.getBonusMulti());
					map.addMarker(mo2);
					
					
					PolygonOptions po = new PolygonOptions();
					for (Position p : poi.getPositions()) {
						System.out.println("PASS HERE");
						po.add(new LatLng(p.getX(), p.getY()));
					}
					po.fillColor(0x40E88A22);
					po.strokeColor(0x40E88A22);
					map.addPolygon(po);
					break;
				default:
					break;
				}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.poi_maps, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class MyMapFragment extends MapFragment {

		public MyMapFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			Log.d("Maps","oncreateView van de map befor inflate");
			View rootView = inflater.inflate(R.layout.fragment_poi_maps,container, false);
			
			
			return rootView;
		}
	}

}
