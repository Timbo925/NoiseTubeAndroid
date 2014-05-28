package com.vub.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noisetube.main.JsonResponse;
import com.noisetube.main.ServerConnection;
import com.noisetube.models.Badge;
import com.noisetube.models.OnTaskCompleted;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"idBadge",
"name",
"description",
"challange",
"icon"
})
public class BadgeStorage {

	private SharedPreferences storage;
//	private Activity activity; //Might be null
	private SharedPreferences.Editor storageEditor;
	private List<Badge> badges = new ArrayList<Badge>();
	//private BadgeAdapter badgeAdapter = null;
	private static final String PARAM_SAVE = "BadgentOfIntrestStorage";
	private Context context;
	private OnTaskCompleted task = null;
	
	//String statsString = storage.getString(Stats.STATS, null);
	
//	public BadgeStorage(Activity activity) {
//		this.activity = activity;
//		storage = activity.getSharedPreferences("prefs", 0);
//		storageEditor = storage.edit();
//	}

	public BadgeStorage(Context context) {
		this.context = context;
		storage = context.getSharedPreferences("prefs", 0);
		storageEditor = storage.edit();
	}


	public List<Badge> getBadgeList(){
		String jsonString = storage.getString(PARAM_SAVE, null);
		if (jsonString == null) {
			return null;
		} else {
			ObjectMapper mapper = new ObjectMapper();
			List<Badge> badges;
			try {
				badges = mapper.readValue(jsonString, new TypeReference<List<Badge>>(){});
				Log.d("BadgeStorge", "Serving from Badge Storage");
				return badges;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}

		}
	} 
	
	public void setBadgeList(List<Badge> badges) {
		ObjectMapper objectMapper = new ObjectMapper();
		Log.d("BadgeStorage" , "Stroing Badge List: " + badges);
		try {
			storageEditor.putString(PARAM_SAVE, objectMapper.writeValueAsString(badges));
			storageEditor.apply();	
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}	
	}
	
	public class GetBadge extends AsyncTask<String, Void, JsonResponse> {

		@Override
		protected JsonResponse doInBackground(String... params) {
			Log.d("GetBadge", "Starting backgound");
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
				System.out.println("GetBadge has erros: " + jsonResponse.getMessage());
			} else {
				ObjectMapper mapper = new ObjectMapper();
				try {
					List<Badge> badges2 = mapper.readValue(jsonResponse.getMessage(), new TypeReference<List<Badge>>(){});
					System.out.println("POI Entrys from server: " + badges2);
					setBadgeList(badges2);					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}

	}

	
	
}
