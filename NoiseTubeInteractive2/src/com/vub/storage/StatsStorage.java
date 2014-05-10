package com.vub.storage;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noisetube.main.JsonResponse;
import com.noisetube.main.ServerConnection;
import com.noisetube.models.OnTaskCompleted;
import com.noisetube.models.Poi;
import com.noisetube.models.Stats;

public class StatsStorage {

	private SharedPreferences storage;
	private SharedPreferences.Editor storageEditor;
	private Stats stats;
	public static final String PARAM_SAVE = "StatsStorage";
	private Context context;
	private OnTaskCompleted task = null;

	public StatsStorage(Context context) {
		this.context = context;
		storage = context.getSharedPreferences("prefs", 0);
		storageEditor = storage.edit();
	}

	public Stats getStats() throws NullPointerException {
		String jsonString = storage.getString(PARAM_SAVE, null);
		if (jsonString == null) {
			throw new NullPointerException();
		} else {
			ObjectMapper mapper = new ObjectMapper();
			try {
				stats = mapper.readValue(jsonString, Stats.class);
				Log.d("StatsStorage", "Serving Stats");
				return stats;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}

		}

	}

	public void setStats(Stats stats) {
		ObjectMapper objectMapper = new ObjectMapper();
		Log.d("StatsStorage" , "Stroing Stats: " + stats.toJsonString());
		try {
			storageEditor.putString(PARAM_SAVE, objectMapper.writeValueAsString(stats));
			storageEditor.apply();	
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}	
	}	
}
