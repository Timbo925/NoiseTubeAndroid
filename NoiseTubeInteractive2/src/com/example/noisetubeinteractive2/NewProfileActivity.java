package com.example.noisetubeinteractive2;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class NewProfileActivity extends Activity {

	private static String statsURL = "http://192.168.1.8:3002/user/5db7088d63a6737ee272f5f1dff8ac37ede5cbef27ea8380f8d691ce59de2d1a";
	TextView textProfile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_display_profile_list_line1);

		final ListView listview = (ListView) findViewById(R.id.profile_list); //Getting listView layout
		textProfile = (TextView) findViewById(R.id.profile_line_line_2);
		textProfile.setText("Timbo925");
		System.out.println("executing GetStats");
		new GetStats().execute(statsURL);
	
//		if (savedInstanceState == null) {
//			getFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
	}
	
	private class GetStats extends AsyncTask<String, JSONObject, JSONObject> {
		
		@Override
		protected JSONObject doInBackground(String... arg) {
			JSONObject jsonObject = new JSONObject();
			HttpGet get = new HttpGet(arg[0]);
			System.out.println("URL: " + arg[0]);
			HttpClient client = new DefaultHttpClient();
			HttpResponse response;
			try {
				response = client.execute(get);
				System.out.println("Response form GetStats: " + response.getStatusLine());
				String jsonString = EntityUtils.toString(response.getEntity());
				JSONObject jsonObject2 = new JSONObject(jsonString);
				jsonObject = jsonObject2;
				//jsonObject.getJSONObject(jsonString);
				System.out.println("jsonObject: " + jsonObject);
				System.out.println("userName is: " + jsonObject.optString("userName"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error in getting JSON");
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			return jsonObject;
		}
		
		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			System.out.println("onPostExecute");
			//super.onPostExecute(jsonObject);
			textProfile.setText(jsonObject.optString("userName"));
		
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_new_profile,container, false);
			return rootView;
		}
	}
	
	public static class ProfileFragment extends Fragment {
		
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_new_profile, container, false);
			return rootView;
		}
	}

}
