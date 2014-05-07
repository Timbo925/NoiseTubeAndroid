package com.vub.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noisetube.adapters.PoiAdapter;
import com.noisetube.main.JsonResponse;
import com.noisetube.main.ServerConnection;
import com.noisetube.models.Poi;

public class PoiStorage {

	private SharedPreferences storage;
	private Activity activity;
	private SharedPreferences.Editor storageEditor;
	private List<Poi> pois = new ArrayList<Poi>();
	private PoiAdapter poiAdapter = null;
	private static final String PARAM_SAVE = "PointOfIntrestStorage";
	
	//String statsString = storage.getString(Stats.STATS, null);
	
	public PoiStorage(Activity activity) {
		this.activity = activity;
		storage = activity.getSharedPreferences("prefs", 0);
		storageEditor = storage.edit();
	}
	
	
	public List<Poi> getPoiList() {
		String jsonString = storage.getString(PARAM_SAVE, null);
		if (jsonString == null) {
			return null;
		} else {
			ObjectMapper mapper = new ObjectMapper();
			List<Poi> pois;
			try {
				pois = mapper.readValue(jsonString, new TypeReference<List<Poi>>(){});
				Log.d("PoiStorge", "Serving from Poi Storage");
				return pois;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}

		}
	} 
	
	//TODO Refresh
	
	public void setPoiList(List<Poi> pois) {
		ObjectMapper objectMapper = new ObjectMapper();
		Collections.sort(pois);
		Log.d("PoiStorage" , "Stroing Poi List");
		try {
			storageEditor.putString(PARAM_SAVE, objectMapper.writeValueAsString(pois));
			storageEditor.apply();	
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}	
	}
	
	public class GetPoi extends AsyncTask<String, Void, JsonResponse> {

		@Override
		protected JsonResponse doInBackground(String... params) {
			Log.d("GetPoi", "Starting backgound");
			ServerConnection serverConnection = (ServerConnection) activity.getApplication();
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
					List<Poi> pois2 = mapper.readValue(jsonResponse.getMessage(), new TypeReference<List<Poi>>(){});
					System.out.println("POI Entrys from server: " + pois2);
					pois = pois2;
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}

	}
	
	
}
