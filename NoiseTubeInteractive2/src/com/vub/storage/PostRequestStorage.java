package com.vub.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noisetube.main.JsonResponse;
import com.noisetube.main.ServerConnection;
import com.noisetube.models.PostRequest;

public class PostRequestStorage {

	private SharedPreferences storage;
//	private Activity activity; //Might be null
	private SharedPreferences.Editor storageEditor;
	private List<PostRequest> postRequests = new ArrayList<PostRequest>();
	private static final String PARAM_SAVE = "PostRequestntOfIntrestStorage";
	private Context context;
	
	//String statsString = storage.getString(Stats.STATS, null);
	
//	public PostRequestStorage(Activity activity) {
//		this.activity = activity;
//		storage = activity.getSharedPreferences("prefs", 0);
//		storageEditor = storage.edit();
//	}

	public PostRequestStorage(Context context) {
		this.context = context;
		storage = context.getSharedPreferences("prefs", 0);
		storageEditor = storage.edit();
	}


	public List<PostRequest> getPostRequestList() {
		String jsonString = storage.getString(PARAM_SAVE, null);
		if (jsonString == null) {
			return null;
		} else {
			ObjectMapper mapper = new ObjectMapper();
			List<PostRequest> postRequests;
			try {
				postRequests = mapper.readValue(jsonString, new TypeReference<List<PostRequest>>(){});
				Log.d("PostRequestStorge", "Serving from PostRequest Storage");
				return postRequests;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}

		}
	} 
	
	//TODO Refresh
	
	public void setPostRequestList(List<PostRequest> postRequests) {
		ObjectMapper objectMapper = new ObjectMapper();
		Log.d("PostRequestStorage" , "Stroing PostRequest List: " + postRequests);
		try {
			storageEditor.putString(PARAM_SAVE, objectMapper.writeValueAsString(postRequests));
			storageEditor.apply();	
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}	
	}
	
	public class GetPostRequest extends AsyncTask<String, Void, JsonResponse> {

		@Override
		protected JsonResponse doInBackground(String... params) {
			Log.d("GetPostRequest", "Starting backgound");
			ServerConnection serverConnection = (ServerConnection) context.getApplicationContext();
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
				System.out.println("GetPostRequest has erros: " + jsonResponse.getMessage());
			} else {
				ObjectMapper mapper = new ObjectMapper();
				try {
					//Log.d("GetPostRequest", jsonResponse.getMessage());
					List<PostRequest> postRequests2 = mapper.readValue(jsonResponse.getMessage(), new TypeReference<List<PostRequest>>(){});
					System.out.println("POI Entrys from server: " + postRequests2);
					//postRequests = postRequests2;
					setPostRequestList(postRequests2);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}

	}	
}
