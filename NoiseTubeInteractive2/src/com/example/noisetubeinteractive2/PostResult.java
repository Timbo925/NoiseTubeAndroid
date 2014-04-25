package com.example.noisetubeinteractive2;

import com.noisetube.models.Points;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class PostResult extends Activity {
	
	Points points;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_result);
		
		points = (Points) this.getIntent().getExtras().getSerializable(Points.POINTS);
		System.out.println("onCreate: " + points);
		
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PointsFragment extends Fragment {
		
		Points points;
		TextView textPoints;
		TextView textMultiLoc;
		TextView textMultiTime;
		TextView textMultiBonus;
		TextView textPointsTotal;
		
		public PointsFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_post_result,
					container, false);
			

			points = (Points) getActivity().getIntent().getExtras().getSerializable(Points.POINTS);
			System.out.println("onCreateView inside fragments: " + points);
			
			 
			return rootView;
		}
	}

}
