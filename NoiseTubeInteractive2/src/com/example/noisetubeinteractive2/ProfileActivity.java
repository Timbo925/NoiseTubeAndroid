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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.noisetube.main.JsonResponse;
import com.noisetube.main.ServerConnection;
import com.noisetube.models.Stats;

public class ProfileActivity extends Activity {

	private static String statsURL = "http://192.168.1.8:3002/user/5db7088d63a6737ee272f5f1dff8ac37ede5cbef27ea8380f8d691ce59de2d1a";
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
		String statsString = storage.getString(Stats.STATS, null);
		boolean found = storage.contains(Stats.STATS);
		System.out.println(found); //Returns False --> Not found
		
		Log.d("ProfileActivity", "StatsString: " +statsString);
		
		if (statsString == null) {
			//Getting profile information from server
			new GetProfile().execute(statsURL);
		} else {
			//Profile info exists in the keyvalue store
			
			//TODO set username from SharedPreferences
			Gson gson = new Gson();
			Stats stats = gson.fromJson(statsString, Stats.class);

			textExp.setText(stats.getExp() + "/" + stats.getExpMax());
			textLvl.setText(Integer.toString(stats.getLevel()));
			textAmount.setText(Integer.toString(stats.getAmount()));
			textTime.setText(Integer.toString(stats.getTime()));
		}

		//		if (savedInstanceState == null) {
		//			getFragmentManager().beginTransaction()
		//				.add(R.id.container, new PlaceholderFragment()).commit();
		//		}
	}

	//TODO Also retrieve STATS to include into profile
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
			JsonObject jsonObject = new JsonObject();
			if (jsonResponse.hasErrors()) {
				textProfileUserName.setText("Bob");
				textProfileEmail.setText("Bob@gmail.com");
			} else {

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
