package com.example.noisetubeinteractive2;

import java.io.Console;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.noisetube.models.Points;
import com.noisetube.models.PostResponse;
import com.noisetube.models.Stats;

public class PostResultActivity extends Activity {
	
	Points points;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_result);
		
		//points = (Points) this.getIntent().getExtras().getSerializable(Points.POINTS);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PointsFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post_result, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.action_settings:
			return true; 
		case android.R.id.home:
			System.out.println("clicked home");
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	public void startLeaderboard(View v) {
		Intent intent = new Intent(this, LeaderboardActivity.class);
		startActivity(intent);
	}
	
	public void startHome(View v) {
		onBackPressed();
	}
	
	public void startBadges(View v) {
		Intent intent = new Intent(this, BadgesActivity.class);
		startActivity(intent);
	}
	
	public static class PointsFragment extends Fragment {
		
		private PostResponse postResponse;
		private TextView textPoints;
		private TextView textMultiLoc;
		private TextView textMultiTime;
		private TextView textMultiBonus;
		private TextView textPointsTotal;
		
		public PointsFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_post_result,container, false);
			
			postResponse = (PostResponse) getActivity().getIntent().getExtras().getSerializable(PostResponse.PARAM_POSTRESPONSE);

			SharedPreferences storage = getActivity().getSharedPreferences("prefs", 0); 					//Accessing local keyvalue store
			SharedPreferences.Editor storageEditor = storage.edit();					//Make editing possible
			Log.d("PostResultActivity", "StatsJson: " + postResponse.getStats().toJsonString());
			
			storageEditor.putString(Stats.STATS, postResponse.getStats().toJsonString());	//Save Stats in Json format
			storageEditor.apply();													     	//Commit all changes 

			Log.d("PostResultActivity", "PostResponse" + postResponse);
			
			textPoints = (TextView) rootView.findViewById(R.id.post_points);
			textMultiBonus = (TextView) rootView.findViewById(R.id.post_multi_bonus);
			textMultiLoc = (TextView) rootView.findViewById(R.id.post_multi_loc);
			textMultiTime = (TextView) rootView.findViewById(R.id.post_multi_time);
			textPointsTotal = (TextView) rootView.findViewById(R.id.post_points_total);
			
			textPoints.setText(Integer.toString(postResponse.getPoints().getPoints()));
			textMultiBonus.setText(Double.toString(postResponse.getPoints().getMultiplierSpecial()));
			textMultiLoc.setText(Double.toString(postResponse.getPoints().getMultiplierLocation()));
			textMultiTime.setText(Double.toString(postResponse.getPoints().getMultiplierTime()));
			textPointsTotal.setText(Float.toString(postResponse.getPoints().getPointsTotal()));
			
			//TODO save and show possible new badges
			return rootView;
		}	
	}
}
