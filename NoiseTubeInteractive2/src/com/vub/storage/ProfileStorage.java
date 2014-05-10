package com.vub.storage;

import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noisetube.main.Profile;
import com.noisetube.models.OnTaskCompleted;

public class ProfileStorage {

	private SharedPreferences storage;
	private SharedPreferences.Editor storageEditor;
	private Profile profile;
	public static final String PARAM_SAVE = "ProfileStorage";
	private Context context;
	private OnTaskCompleted task = null;

	public ProfileStorage(Context context) {
		this.context = context;
		storage = context.getSharedPreferences("prefs", 0);
		storageEditor = storage.edit();
	}

	public Profile getProfile() throws NullPointerException {
		String jsonString = storage.getString(PARAM_SAVE, null);
		if (jsonString == null) {
			throw new NullPointerException();
		} else {
			ObjectMapper mapper = new ObjectMapper();
			try {
				profile = mapper.readValue(jsonString, Profile.class);
				Log.d("ProfileStorage", "Serving Profile");
				return profile;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}

		}

	}

	public void setProfile(Profile profile) {
		ObjectMapper objectMapper = new ObjectMapper();
		Log.d("ProfileStorage" , "Stroing Profile: " + profile.toString());
		try {
			storageEditor.putString(PARAM_SAVE, objectMapper.writeValueAsString(profile));
			storageEditor.apply();	
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}	
	}	
}
