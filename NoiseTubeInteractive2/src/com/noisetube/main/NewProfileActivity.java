package com.noisetube.main;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.noisetubeinteractive2.R;

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
	private class GetProfile extends AsyncTask<String, JSONObject, JSONObject> {
		
		@Override
		protected JSONObject doInBackground(String... arg) {
			JSONObject jsonObject = new JSONObject();
			//Setting up the request to the server
			HttpGet get = new HttpGet(arg[0]);
			System.out.println("URL: " + arg[0]);
			HttpClient client = new DefaultHttpClient();
			HttpResponse response;
			
			try {
				response = client.execute(get);
				System.out.println("Response form GetStats: " + response.getStatusLine());
				
				if (response.getStatusLine().getStatusCode() == 200) { //Successfull operation from server
					String jsonString = EntityUtils.toString(response.getEntity()); // Extraction body form responseboy
					jsonObject = new JSONObject(jsonString); // String in JSON format to a jsonObject
					System.out.println("jsonObject: " + jsonObject);
					return jsonObject;
				} else {
					return jsonObject; // In case of an error (no 200 code). null returned
				}
				
			} catch (IOException e) {
				System.out.println("Error in getting JSON");
				e.printStackTrace();
			} catch (JSONException e) {
				System.err.println("JSON request problem");
				e.printStackTrace();
			}		
			return jsonObject;
		}
		
		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			
			//Test if object is set by the request. If null there was a bad return STATUS form the server
			if (!jsonObject.equals(null)) {
				textProfileUserName.setText(jsonObject.optString("userName"));
				textProfileEmail.setText(jsonObject.optString("email"));
				System.out.println("Success with GetProfile");
			} else {
				System.out.println("Problem with GetProfile");
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
