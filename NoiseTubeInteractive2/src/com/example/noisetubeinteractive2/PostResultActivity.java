package com.example.noisetubeinteractive2;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.noisetube.models.Points;
import com.noisetube.models.PostResponse;
import com.noisetube.models.Stats;
import com.vub.storage.StatsStorage;

public class PostResultActivity extends Activity {
	
	Points points;
	int pointsInt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_result);
		

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
	
	public void startShare(View v) {

		PostResponse postResponse = (PostResponse) this.getIntent().getExtras().getSerializable(PostResponse.PARAM_POSTRESPONSE);
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, "I just scored " + Integer.toString(postResponse.getPoints().getPoints()) + " points in NoiseTube! www.noisetube.be");
		sendIntent.setType("text/plain");
		startActivity(sendIntent);
	}
	
	public static class PointsFragment extends Fragment {
		
		private PostResponse postResponse;
		private TextView textMultiLoc;
		private TextView textMultiTime;
		private TextView textMultiBonus;
		private TextView textPointsTotal;
		private ProgressBar progressBar;
		private TextView progressText;
		private TextView levelText;
		private int size;
		boolean levelUp = false;
		int level;
		
		public PointsFragment() {
		}
		
		public PostResponse getPostResponse() {
			return postResponse;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_post_result,container, false);
			
			postResponse = (PostResponse) getActivity().getIntent().getExtras().getSerializable(PostResponse.PARAM_POSTRESPONSE);
			StatsStorage statsStorage = new StatsStorage(getActivity());
			statsStorage.setStats(postResponse.getStats());
			SharedPreferences storage = getActivity().getSharedPreferences("prefs", 0); 					//Accessing local keyvalue store
			SharedPreferences.Editor storageEditor = storage.edit();					//Make editing possible
			Log.d("PostResultActivity", "StatsJson: " + postResponse.getStats().toJsonString());
			Log.d("PostResultActivity", "PointsJson: " + postResponse.getPoints().toJsonString());
			
			storageEditor.putString(Stats.STATS, postResponse.getStats().toJsonString());	//Save Stats in Json format
			storageEditor.apply();													     	//Commit all changes 

			Log.d("PostResultActivity", "PostResponse" + postResponse.toString());
			
			
			textMultiBonus = (TextView) rootView.findViewById(R.id.post_multi_bonus);
			textMultiLoc = (TextView) rootView.findViewById(R.id.post_multi_loc);
			textMultiTime = (TextView) rootView.findViewById(R.id.post_multi_time);
			textPointsTotal = (TextView) rootView.findViewById(R.id.post_points_total);
			progressBar = (ProgressBar) rootView.findViewById(R.id.post_progressbar);
			progressText = (TextView) rootView.findViewById(R.id.post_progress_text);
			levelText = (TextView) rootView.findViewById(R.id.post_lvl);
			
			textMultiBonus.setText(Double.toString(postResponse.getPoints().getMulti_special()));
			textMultiLoc.setText(Double.toString(postResponse.getPoints().getMulti_place()));
			textMultiTime.setText(Double.toString(postResponse.getPoints().getMulti_time()));
			textPointsTotal.setText(Float.toString(postResponse.getPoints().getPoints()));
			
			size = postResponse.getStats().getNextLevel() - postResponse.getStats().getLastLevel();
			int levelProgress = postResponse.getStats().getExp() - postResponse.getStats().getLastLevel();
			if (size < levelProgress) {
				size = levelProgress; 
				levelUp = true; 
				level = postResponse.getStats().getLevel();
				levelText.setText(Integer.toString(postResponse.getStats().getLevel() - 1));
			} else {
				levelText.setText(Integer.toString(postResponse.getStats().getLevel()));
			}
			progressBar.setMax(size);
			progressBar.setProgress(0);
			progressText.setText(Integer.toString(levelProgress) + "/" + Integer.toString(size));
			
			
			ProgressBarAnimation animation = new ProgressBarAnimation();
			animation.execute(size, levelProgress);
			
			//TODO save and show possible new badges
			
			return rootView;
		}	
		public class ProgressBarAnimation extends AsyncTask<Integer, Integer, Void> {
			int counter = 0;
		    @Override
		    protected Void doInBackground(Integer... params) {
		        while (progressBar.getProgress() < params[1]) {
		            publishProgress(counter);
		            counter++;
		            try {
		                Thread.sleep(10);
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		        return null;
		    }

		    @Override
		    protected void onProgressUpdate(Integer... values) {
		        progressBar.incrementProgressBy(1);
		        progressText.setText(Integer.toString(values[0]) + "/" + Integer.toString(size));        
		    }

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				
				if (levelUp) {
					levelText.setText(Integer.toString(level));
				}
			}
		    
		    
		}
	}
}
