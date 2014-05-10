package com.example.noisetubeinteractive2;

import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.noisetube.main.JsonResponse;
import com.noisetube.main.LocationMeasurment;
import com.noisetube.main.NotificationReceiver;
import com.noisetube.main.PointMeasurement;
import com.noisetube.main.ServerConnection;
import com.noisetube.main.SoundMeasurement;
import com.noisetube.main.SoundMeasurementService;
import com.noisetube.models.PostRequest;
import com.noisetube.models.PostResponse;

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.noisetubeinteractive2.EXTRA_MESSAGE";
	private DbResponse dbResponse;
	private FinalDbResponse finalDbResponse;
	private SharedPreferences storage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Registering service with category and filter (defined in SoundMeasuremntService)
		IntentFilter filter = new IntentFilter(DbResponse.ACTION_RESP);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		dbResponse = new DbResponse();
		registerReceiver(dbResponse, filter);

		IntentFilter filter2 = new IntentFilter(FinalDbResponse.ACTION_RESP);
		filter2.addCategory(Intent.CATEGORY_DEFAULT);
		finalDbResponse = new FinalDbResponse();
		registerReceiver(finalDbResponse, filter2);

		//Creating notification in 20 hours
		storage = this.getSharedPreferences("prefs", 0);
		Long lastNotification = storage.getLong("Last Notification", 0);
		Log.d("MainActivity", "Last Notification: " + lastNotification);
		if(lastNotification == 0 || (System.currentTimeMillis() - lastNotification) > (20 *60 * 60 * 1000)) {
			SharedPreferences.Editor editor = storage.edit();
			editor.putLong("Last Notification", System.currentTimeMillis());
			editor.apply();
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis() + (20 *60 * 60 * 1000)); //
			
			Intent myIntent = new Intent(MainActivity.this, NotificationReceiver.class);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent,0);

			AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
			alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
			Log.d("MainActivity", "Register Alarm");
		}
		
		

		//Hiding Stop button
		Button buttonStop = (Button) findViewById(R.id.home_btn_stop);

		//Adding fragmets to the mainActivity
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		switch (item.getItemId()) {
		case R.id.action_measurement:
			startPoi();
			System.out.println("actoin_measurement clicked");
			return true;
		case R.id.action_profile:
			startProfile();
			System.out.println("action_profile clicked");
			return true;
		case R.id.action_settings:

			System.out.println("action_settngs clicked");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,false);
			Log.v("onCreateView", "Layout inflated");
			return rootView;
		}

	}

	public class DbResponse extends BroadcastReceiver {

		private int dbLvlInt = 0;
		private int dbLvlPerInt = 100;
		private String dbText= " db";
		public static final String ACTION_RESP = "db_update";

		@Override
		public void onReceive(Context context, Intent intent) {

			try {
				FragmentManager fragmen= getFragmentManager(); 								//Fragment manager containing all the fragments
				Fragment frag = fragmen.findFragmentById(R.id.container); 					//Find one of the fragments by its ID
				TextView textDbLvl = (TextView) frag.getView().findViewById(R.id.db_lvl);   //Get text field inside the fragment
				TextView textDbMax = (TextView) frag.getView().findViewById(R.id.home_max_db);
				TextView textDbMin = (TextView) frag.getView().findViewById(R.id.home_min_db);
				TextView textDbAvg = (TextView) frag.getView().findViewById(R.id.home_avg_db);
				ProgressBar progressBar = (ProgressBar) frag.getView().findViewById(R.id.progress_bar_meter);
				TextView textPoints = (TextView) frag.getView().findViewById(R.id.home_points);
				TextView textPointsTotal = (TextView) frag.getView().findViewById(R.id.home_points_total);
				TextView textMulti  = (TextView) frag.getView().findViewById(R.id.home_multi_loc);
				TextView textMultiBonus = (TextView) frag.getView().findViewById(R.id.home_multi_bonus);
				TextView textLocation = (TextView) frag.getView().findViewById(R.id.home_location);

				SoundMeasurement soundMeasurement = (SoundMeasurement) intent.getSerializableExtra(SoundMeasurementService.PARAM_OUT_SOUNDM);
				PointMeasurement pointMeasurement = (PointMeasurement) intent.getSerializableExtra(SoundMeasurementService.PARAM_OUT_POINTM);
				LocationMeasurment locationMeasurment = (LocationMeasurment) intent.getSerializableExtra(SoundMeasurementService.PARAM_OUT_LOCATIONM);

				System.out.println(pointMeasurement);
				textDbMax.setText(Integer.toString(soundMeasurement.getDbMax()) + dbText);
				textDbMin.setText(Integer.toString(soundMeasurement.getDbMin()) + dbText);
				textDbAvg.setText(Integer.toString(soundMeasurement.getDbAvg()) + dbText);
				textDbLvl.setText(Integer.toString(soundMeasurement.getDbLast())  + dbText);
				progressBar.setProgress((int) Math.round(100 - soundMeasurement.getDbPercent()));
				textPoints.setText(Integer.toString(pointMeasurement.getPoints()));
				textMultiBonus.setText(Integer.toString(pointMeasurement.getBonusPoints()));
				textMulti.setText(Double.toString(pointMeasurement.getLocationMultiplier()));
				textPointsTotal.setText(Integer.toString(pointMeasurement.getTotalPoints()));
				textLocation.setText("(" + Double.toString(locationMeasurment.getLastLat()) + ";" + Double.toString(locationMeasurment.getLastLon()) + ")" );

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}	

	/**
	 * @author Tim
	 * BroadcastReceiver for for last broadcast by the Service. Will execute the asyncService to post the neasurements
	 */
	public class FinalDbResponse extends BroadcastReceiver {

		public static final String ACTION_RESP = "db_final braodcast";

		@Override
		public void onReceive(Context context, Intent intent) {
			SoundMeasurement soundMeasurement = (SoundMeasurement) intent.getExtras().getSerializable(SoundMeasurementService.PARAM_OUT_SOUNDM);
			LocationMeasurment locationMeasurment = (LocationMeasurment) intent.getSerializableExtra(SoundMeasurementService.PARAM_OUT_LOCATIONM);
			PointMeasurement pointMeasurement = (PointMeasurement) intent.getSerializableExtra(SoundMeasurementService.PARAM_OUT_POINTM);

			Gson gson = new Gson();
			Log.i("FinalDbResponse: ", gson.toJson(soundMeasurement).toString());
			Log.i("FinalDbResponse: ", gson.toJson(locationMeasurment).toString());
			Log.i("FinalDbResponse: ", gson.toJson(pointMeasurement).toString());


			PostSoundMeasurement postSoundMeasurement = new PostSoundMeasurement(soundMeasurement, pointMeasurement, locationMeasurment);
			postSoundMeasurement.execute();
		}
	}	

	private Intent mServiceIntent;

	public void startMeasuring(View view) {
		//Intent mServiceIntent;

		LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean enabled = service .isProviderEnabled(LocationManager.GPS_PROVIDER);

		// check if enabled and if not send user to the GSP settings
		// Better solution would be to display a dialog and suggesting to 
		// go to the settings
		if (!enabled) {
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
		} 

		Chronometer chronometer = (Chronometer) findViewById(R.id.home_chrono);
		Button buttonStop = (Button) findViewById(R.id.home_btn_stop);
		Button buttonStart = (Button) findViewById(R.id.home_btn_start);

		chronometer.setBase(SystemClock.elapsedRealtime());
		chronometer.start();
		buttonStop.setVisibility(View.VISIBLE);
		buttonStart.setVisibility(View.GONE);
		mServiceIntent = new Intent(getApplicationContext(), SoundMeasurementService.class);   //Creating Intent to pass to Service
		mServiceIntent.putExtra(SoundMeasurementService.PARAM_IN_MSG, "This it the IN_MSG");   //Adding some dataString to the Intent to pass to service
		startService(mServiceIntent); 														   //Starting service with the intent

	}

	/**
	 * Action when stop button in clicked. This will stop the running SoundMeasurementService
	 * @param view
	 */
	public void stopMeasuring(View view) {
		//TODO 
		Button buttonStop = (Button) findViewById(R.id.home_btn_stop);
		Button buttonStart = (Button) findViewById(R.id.home_btn_start);
		Chronometer chronometer = (Chronometer) findViewById(R.id.home_chrono);

		buttonStart.setVisibility(View.VISIBLE);
		buttonStop.setVisibility(View.GONE);
		chronometer.stop();

		//Posting same intent with this parameters will stop both services
		mServiceIntent.putExtra(SoundMeasurementService.PARAM_COMMAND,SoundMeasurementService.PARAM_STOP_COMMAND);
		startService(mServiceIntent);			
	}


	public void startProfile() {
		Intent intent = new Intent(this, ProfileActivity.class);
		startActivity(intent);
	}

	public void startPoi() {
		Intent intent = new Intent(this, PoiActivity.class);
		startActivity(intent);
	}

	/**
	 * Post measurements to database and pass PostResponse to the next Activity to display progress
	 * @author Tim
	 *
	 */
	public class PostSoundMeasurement extends AsyncTask<Void, Void, JsonResponse> {
		private PostRequest postRequest;

		public PostSoundMeasurement(SoundMeasurement soundMeasurement,PointMeasurement pointMeasurement,LocationMeasurment locationMeasurment) {
			Log.i("PostSoundMeasurement", "Post Created");
			postRequest = new PostRequest(soundMeasurement, pointMeasurement, locationMeasurment);

		}

		@Override
		protected JsonResponse doInBackground(Void... arg) {
			Log.i("PostSoundMeasurement", "Begin Posting Measurment");

			String url = "result/test/1";		
			ServerConnection serverConnection = (ServerConnection) getApplication();
			JsonResponse jsonResponse = new JsonResponse();
			ObjectMapper objectMapper = new ObjectMapper();


			try {
				Log.d("PostSoundMeasurment", objectMapper.writeValueAsString(postRequest));
				jsonResponse = serverConnection.post(url, objectMapper.writeValueAsString(postRequest));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return jsonResponse;		
		}

		@Override
		protected void onPostExecute(JsonResponse jsonResponse) { //Access to the GUI tread
			if (jsonResponse.hasErrors()) {
				System.out.println("Errors Put Points");

				new AlertDialog.Builder(getFragmentManager().findFragmentById(R.id.container).getView().getContext())
				.setTitle("Network Connection Problem")
				.setMessage("Make sure a network connection is present when posting results. Measurements will be saved and added to your acount at a later time.")
				.setPositiveButton("Save", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						Log.i("Main Activity AlertDialog", "User clicked to save");
						//TODO save here
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						Log.i("Main Activity AlertDialog", "User clicked to delete");
					}
				})
				.setIcon(android.R.drawable.ic_dialog_alert)
				.show();

			} else {
				System.out.println("Success Put Point");
				ObjectMapper objectMapper = new ObjectMapper();

				Intent intent = new Intent(getApplicationContext(), PostResultActivity.class);
				try {
					Log.i("MainActivity Response" , "Server Response: " + jsonResponse.toString());
					PostResponse postResponse = objectMapper.readValue(jsonResponse.getMessage(), PostResponse.class);
					System.out.println(postResponse.getPoints().toString());
					intent.putExtra(PostResponse.PARAM_POSTRESPONSE, objectMapper.readValue(jsonResponse.getMessage(), PostResponse.class)); 
					startActivity(intent);
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
}
