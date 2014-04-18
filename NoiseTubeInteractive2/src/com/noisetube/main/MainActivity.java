package com.noisetube.main;

import com.example.noisetubeinteractive2.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.noisetubeinteractive2.EXTRA_MESSAGE";
	private DbResponse dbResponse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Registering service with category and filter (defined in SoundMeasuremntService)
		IntentFilter filter = new IntentFilter(DbResponse.ACTION_RESP);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		dbResponse = new DbResponse();
		registerReceiver(dbResponse, filter);

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
				textDbMax.setText(Integer.toString(intent.getIntExtra(SoundMeasurementService.PARAM_OUT_MAX, 0)) + dbText);
				textDbMin.setText(Integer.toString(intent.getIntExtra(SoundMeasurementService.PARAM_OUT_MIN, 0)) + dbText);
				textDbAvg.setText(Integer.toString(intent.getIntExtra(SoundMeasurementService.PARAM_OUT_AVG, 0)) + dbText);
				textDbLvl.setText(Integer.toString(intent.getIntExtra(SoundMeasurementService.PARAM_OUT_MSG, 0))  + dbText); 								//Set layout to received message
				progressBar.setProgress(100 - intent.getIntExtra(SoundMeasurementService.PARAM_OUT_PER, 0));
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}	

	public void startMeasuring(View view) {
		Intent mServiceIntent;
		Chronometer chronometer = (Chronometer) findViewById(R.id.home_chrono);
		Button buttonStop = (Button) findViewById(R.id.home_btn_stop);
		Button buttonStart = (Button) findViewById(R.id.home_btn_start);
		
		chronometer.start();
		buttonStop.setVisibility(View.VISIBLE);
		buttonStart.setVisibility(View.GONE);
		mServiceIntent = new Intent(getApplicationContext(), SoundMeasurementService.class);   //Creating Intent to pass to Service
		mServiceIntent.putExtra(SoundMeasurementService.PARAM_IN_MSG, "This it the IN_MSG");   //Adding some dataString to the Intent to pass to service
		startService(mServiceIntent); 														   //Starting service with the intent

	}
	
	public void stopMeasuring(View view) {
		//TODO stopMeasuring button
		Button buttonStop = (Button) findViewById(R.id.home_btn_stop);
		Button buttonStart = (Button) findViewById(R.id.home_btn_start);
		
		buttonStart.setVisibility(View.VISIBLE);
		buttonStop.setVisibility(View.GONE);
	}

	public void startProfile() {
		Intent intent = new Intent(this, NewProfileActivity.class);
		startActivity(intent);
	}

}
