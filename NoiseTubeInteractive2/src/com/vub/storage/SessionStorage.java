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

public class SessionStorage {

	private SharedPreferences storage;
	private SharedPreferences.Editor storageEditor;
	public static final String PARAM_SAVE = "SessionStorage";
	private Context context;
	private OnTaskCompleted task = null;

	public SessionStorage(Context context) {
		this.context = context;
		storage = context.getSharedPreferences("prefs", 0);
		storageEditor = storage.edit();
	}

	public String getSession() throws NullPointerException {
		return storage.getString(PARAM_SAVE, null);
	}

	public void setSession(String sessionId) {
		storageEditor.putString(PARAM_SAVE, sessionId);
		storageEditor.apply();	
	}	
}
