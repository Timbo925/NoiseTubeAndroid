package com.example.noisetubeinteractive2;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.noisetubeinteractive2.R;
import com.google.gson.JsonObject;
import com.noisetube.main.JsonResponse;
import com.noisetube.main.ServerConnection;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileActivity extends Activity {

	private static String statsURL = "http://192.168.1.8:3002/user/5db7088d63a6737ee272f5f1dff8ac37ede5cbef27ea8380f8d691ce59de2d1a";
	TextView textProfileUserName;
	TextView textProfileEmail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_display_profile_list_line1);

		textProfileUserName = (TextView) findViewById(R.id.profile_userName);
		textProfileEmail = (TextView) findViewById(R.id.profile_email);

		//AsyncTask that gets Profile information form server
		new GetProfile().execute(statsURL);
	
//		if (savedInstanceState == null) {
//			getFragmentManager().beginTransaction()
//				.add(R.id.container, new PlaceholderFragment()).commit();
//		}
	}

	//TODO Also retreive STATS to include into profile
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
