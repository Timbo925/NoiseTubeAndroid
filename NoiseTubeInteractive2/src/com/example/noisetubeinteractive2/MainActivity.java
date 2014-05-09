package com.example.noisetubeinteractive2;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
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

import com.google.gson.Gson;
import com.noisetube.main.JsonResponse;
import com.noisetube.main.LocationMeasurment;
import com.noisetube.main.PointMeasurement;
import com.noisetube.main.ServerConnection;
import com.noisetube.main.SoundMeasurement;
import com.noisetube.main.SoundMeasurementService;
import com.noisetube.models.Points;
import com.noisetube.models.PostResponse;
import com.noisetube.models.Stats;

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.noisetubeinteractive2.EXTRA_MESSAGE";
	private DbResponse dbResponse;
	private FinalDbResponse finalDbResponse;

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

		//Hiding Stop button
		Button buttonStop = (Button) findViewById(R.id.home_btn_stop);
		//buttonStop.setVisibility(View.GONE);

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
		private SoundMeasurement soundMeasurement;
		private PointMeasurement pointMeasurement;
		private LocationMeasurment locationMeasurment;
		
		public PostSoundMeasurement(SoundMeasurement soundMeasurement,PointMeasurement pointMeasurement,LocationMeasurment locationMeasurment) {
			this.soundMeasurement = soundMeasurement;
			this.locationMeasurment = locationMeasurment;
			this.pointMeasurement = pointMeasurement;
		}

		@Override
		protected JsonResponse doInBackground(Void... arg) {
			String url = "http://nuNogNiet";		
			ServerConnection serverConnection = (ServerConnection) getApplication();
			JsonResponse jsonResponse = new JsonResponse();
			Gson gson = new Gson();		
			jsonResponse = serverConnection.post(url, gson.toJson(arg[0]).toString());
			return jsonResponse;		
		}

		@Override
		protected void onPostExecute(JsonResponse jsonResponse) { //Access to the GUI tread
			if (jsonResponse.hasErrors()) {
				System.out.println("Errors Put Points");

				//TODO Use real points response
				Points points = new Points();
				points.setMultiplierLocation(2);
				points.setMultiplierSpecial(1);
				points.setMultiplierTime(1);
				points.setPoints(222);

				Stats stats = new Stats();
				stats.setAmount(11);
				stats.setExp(501);
				stats.setExpMax(1010);
				stats.setLevel(4);
				stats.setTime(600);

				PostResponse postResponse = new PostResponse();
				postResponse.setPoints(points);
				postResponse.setStats(stats);

				Intent intent = new Intent(getApplicationContext(), PostResultActivity.class);
				intent.putExtra(PostResponse.PARAM_POSTRESPONSE, postResponse);

				startActivity(intent);
			} else {
				System.out.println("Success Put Point");

			}
		}			
	}

}
