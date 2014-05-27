package com.example.noisetubeinteractive2;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.noisetube.main.JsonResponse;
import com.noisetube.main.Profile;
import com.noisetube.main.ServerConnection;
import com.noisetube.models.Stats;
import com.vub.storage.ProfileStorage;
import com.vub.storage.SessionStorage;
import com.vub.storage.StatsStorage;

public class ProfileActivity extends Activity {

	TextView textProfileUserName;
	TextView textProfileEmail;
	TextView textExp;
	TextView textLvl;
	TextView textAmount;
	TextView textTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_display_profile_list_line1);

		textProfileUserName = (TextView) findViewById(R.id.profile_userName);
		textProfileEmail = (TextView) findViewById(R.id.profile_email);
		textExp = (TextView) findViewById(R.id.profile_exp);
		textLvl = (TextView) findViewById(R.id.profile_lvl);
		textAmount = (TextView) findViewById(R.id.profile_msr);
		textTime = (TextView) findViewById(R.id.profile_time);

		SharedPreferences storage = getSharedPreferences("prefs", 0);

		if (!storage.contains(StatsStorage.PARAM_SAVE) || !storage.contains(ProfileStorage.PARAM_SAVE)) {
			SessionStorage sessionStorage = new SessionStorage(getApplicationContext());
			String sessionId = sessionStorage.getSession();
			new GetProfile().execute("user/" + sessionId);
		} else {
			ProfileStorage profileStorage = new ProfileStorage(getApplicationContext());
			StatsStorage statsStorage = new StatsStorage(getApplicationContext());
			Profile profile = profileStorage.getProfile();
			Stats stats = statsStorage.getStats();
			
			Log.d("ProfileActivity", "Profile: " + profile);
			Log.d("ProfileActivity", "Stats: " + stats);
			
			textProfileUserName.setText(profile.getUserName());
			textProfileEmail.setText(profile.getEmail());
			textExp.setText(Integer.toString(stats.getExp() - stats.getLastLevel())+ "/" + Integer.toString((stats.getNextLevel() - stats.getLastLevel())));
			textLvl.setText(Integer.toString(stats.getLevel()));
			textAmount.setText(Integer.toString(stats.getAmountMeasurments()));
			textTime.setText(stats.getTotalTime());

		}
	}

	private class GetProfile extends AsyncTask<String, Void, JsonResponse> {

		@Override
		protected JsonResponse doInBackground(String... arg) {
			ServerConnection serverConnection = (ServerConnection) getApplication();
			JsonResponse jsonResponse = new JsonResponse();
			try {
				jsonResponse = serverConnection.get(arg[0]);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return jsonResponse;
		}

		@Override
		protected void onPostExecute(JsonResponse jsonResponse) { // Function has access to the UI treat

			if (jsonResponse.hasErrors()) {
				Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(intent);
			} else {
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					Profile profile = objectMapper.readValue(jsonResponse.getMessage(), Profile.class);
					ProfileStorage profileStorage = new ProfileStorage(getApplicationContext());
					profileStorage.setProfile(profile);
				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_profile, menu);
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

	public static class ProfileFragment extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_new_profile, container, false);
			return rootView;
		}
	}

	public void startBadges(View v) {
		Intent intent = new Intent(this, BadgesActivity.class);
		startActivity(intent);
	}

	public void startLeaderboard(View v) {
		Intent intent = new Intent(this, LeaderboardActivity.class);
		startActivity(intent);
	}

}
