package com.example.noisetubeinteractive2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noisetube.adapters.PoiAdapter;
import com.noisetube.main.JsonResponse;
import com.noisetube.main.ServerConnection;
import com.noisetube.models.Poi;
import com.vub.storage.PoiStorage;

public class PoiActivity extends Activity {

	public void startMap(View v) {
		Intent intent = new Intent(this, PoiMapsActivity.class);
		startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poi);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			.add(R.id.container, new PoiFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.poi, menu);
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
	public static class PoiFragment extends Fragment implements LocationListener {

		private PoiStorage poiStorage; 
		private PoiAdapter adapter;
		private ListView listView;
		private LocationManager locationManager;
		private String provider;
		private Location location;
		private Location locationBase;

		public PoiFragment() {
		}
		
		public void startMap() {
			Intent intent = new Intent(getActivity(), PoiMapsActivity.class);
			startActivity(intent);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_poi, container,false);

			Log.d("PoiFragment", "Creating View");
			poiStorage = new PoiStorage(getActivity());
			listView = (ListView) rootView.findViewById(R.id.poi_listView);

			adapter = new PoiAdapter(new ArrayList<Poi>(), getActivity());
			
			//Setting up location manager with corresponding criteria
			locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			provider = locationManager.getBestProvider(criteria, false);
			location = locationManager.getLastKnownLocation(provider);
			locationBase = location;

			try {
				adapter.setPois(poiStorage.getPoiList());
				Log.d("PoiActivity", "Using Local Poi");

				//Loading new points based on last known location
				GetPoi loadPois = new GetPoi();
				loadPois.execute("poi/"+ location.getLatitude() +"/"+ location.getLongitude()+"/10" , "");			
			} catch (NullPointerException e) {
				Log.i("PoiActivity onCreateView", "No pois found in database, update poiStorage");
				GetPoi loadPois = new GetPoi();
				if (location != null) {
					loadPois.execute("poi/"+ location.getLatitude() +"/"+ location.getLongitude()+"/10" , "");		
				}
				
			}

			listView.setAdapter(adapter);
			return rootView;
		}

		/**
		 * If the location changed more than 2 km we ask for new Poi from the server. Adapter will be updated
		 * @param location
		 */
		@Override
		public void onLocationChanged(Location location) {
			if (location.distanceTo(locationBase) > 2) {
				locationBase = location;
				GetPoi loadPois = new GetPoi();
				loadPois.execute("poi/"+ location.getLatitude() +"/"+ location.getLongitude()+"/100" , "");		
			}		
		}
		
		

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}


		//Updating the poi list and informing adapter when changed
		public class GetPoi extends AsyncTask<String, Void, JsonResponse> {

			@Override
			protected JsonResponse doInBackground(String... params) {
				Log.d("GetPoi", "Starting backgound");
				ServerConnection serverConnection = (ServerConnection) getActivity().getApplication();
				JsonResponse jsonResponse = new JsonResponse();
				try {

					String url = params[0] + params[1];
					jsonResponse = serverConnection.get(url);

				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return jsonResponse;

			}
			@Override
			protected void onPostExecute(JsonResponse jsonResponse) {
				super.onPostExecute(jsonResponse);
				if(jsonResponse.hasErrors()) {
					System.out.println("GetPoi has erros: " + jsonResponse.getMessage());
				} else {
					ObjectMapper mapper = new ObjectMapper();
					try {
						//Log.d("GetPoi", jsonResponse.getMessage());
						List<Poi> pois = mapper.readValue(jsonResponse.getMessage(), new TypeReference<List<Poi>>(){});
						//System.out.println("POI Entrys from server: " + pois);

						//If offline only local storage will be used
						poiStorage.setPoiList(pois);

						Log.d("GetPoi", "Notifying adapterof data set changed");
						adapter.setPois(pois);

					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}

		}
	}

}
