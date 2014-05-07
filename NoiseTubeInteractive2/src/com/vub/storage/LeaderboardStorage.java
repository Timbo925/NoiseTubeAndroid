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
import com.noisetube.adapters.LeaderboardAdapter;
import com.noisetube.adapters.PoiAdapter;
import com.noisetube.main.JsonResponse;
import com.noisetube.main.ServerConnection;
import com.noisetube.models.LeaderboardEntry;
import com.noisetube.models.LeaderboardType;
import com.noisetube.models.Poi;

public class LeaderboardStorage {

	private SharedPreferences storage;
	private Activity activity;
	private SharedPreferences.Editor storageEditor;
	private List<LeaderboardEntry> leaderboardEntries = new ArrayList<LeaderboardEntry>();
	private LeaderboardAdapter leaderboardAdapter = null;
	private static final String PARAM_SAVE = "Leaderboardstorage";
	private LeaderboardType type;
	
	//String statsString = storage.getString(Stats.STATS, null);
	
	public LeaderboardStorage(Activity activity, LeaderboardType type) {
		this.type = type;
		this.activity = activity;
		storage = activity.getSharedPreferences("prefs", 0);
		storageEditor = storage.edit();
	}
	
	
	public List<LeaderboardEntry> getLeaderboardEntries() {
		String jsonString = storage.getString(PARAM_SAVE+"_"+type, null);
		if (jsonString == null) {
			return null;
		} else {
			ObjectMapper mapper = new ObjectMapper();
			List<LeaderboardEntry> pois;
			try {
				leaderboardEntries = mapper.readValue(jsonString, new TypeReference<List<LeaderboardEntry>>(){});
				Log.d("LeaderboardStorage", "Serving from Poi Storage");
				return leaderboardEntries;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}

		}
	} 
		
	public void setPoiList(List<LeaderboardEntry> leaderboardEntries) {
		ObjectMapper objectMapper = new ObjectMapper();
		//Collections.sort(pois);
		Log.d("LeaderboardStorage" , "Stroing Poi List");
		try {
			storageEditor.putString(PARAM_SAVE+"_"+type, objectMapper.writeValueAsString(leaderboardEntries));
			storageEditor.apply();	
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}	
	}
}
