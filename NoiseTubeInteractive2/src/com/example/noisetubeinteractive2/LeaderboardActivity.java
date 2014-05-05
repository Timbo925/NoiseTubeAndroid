package com.example.noisetubeinteractive2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.client.ClientProtocolException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noisetube.adapters.LeaderboardAdapter;
import com.noisetube.main.JsonResponse;
import com.noisetube.main.ServerConnection;
import com.noisetube.models.LeaderboardEntry;
import com.noisetube.models.LeaderboardType;

public class LeaderboardActivity extends Activity implements
ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leaderboard);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
		.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	public void onBackPressed() {
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.leaderboard, menu);
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

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a ScoreLeaderboardFragment (defined as a static inner class
			// below).

			switch (position) {
			case 0:
				return ScoreLeaderboardFragment.newInstance(LeaderboardType.maxExp);
			case 1:
				return ScoreLeaderboardFragment.newInstance(LeaderboardType.level);
			case 2:
				return ScoreLeaderboardFragment.newInstance(LeaderboardType.amountMeasurments);
			}
			return new ScoreLeaderboardFragment();
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	public static class ScoreLeaderboardFragment extends Fragment {

		private LeaderboardAdapter adapter;
		private ListView listView;
		
		public static final String TYPE = "leaderboard_type";

		/**
		 * Default constructor
		 */
		public ScoreLeaderboardFragment() {	
		}

		/**
		 * @param type - Type will beside the kind of leaderboard loaded from server
		 * @return - fragment with argumesnt added
		 */
		public static ScoreLeaderboardFragment newInstance(LeaderboardType type) {
			ScoreLeaderboardFragment fragment = new ScoreLeaderboardFragment();
			Bundle args = new Bundle();
			args.putSerializable(TYPE, type);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_leaderboard_score,container, false);

			LeaderboardType type = (LeaderboardType) getArguments().getSerializable(TYPE);
			System.out.println("Get Type: " + type);

			//Configure the list view
			listView = (ListView)  rootView.findViewById(R.id.leaderboard_listview_score);
			
			// Building custom adapter to be used
			adapter = new LeaderboardAdapter(new ArrayList<LeaderboardEntry>(), getActivity(), type);
			Log.d("onCreateView", "Adapter build and set");
			
			listView.setAdapter(adapter);

			//TODO get session Id
			GetLeaderboard loadLeaderboard = new GetLeaderboard();
			loadLeaderboard.execute("leaderboard/test/", type.toString());
			
			return rootView;
		}

		//Params: url + type
		public class GetLeaderboard extends AsyncTask<String, Void, JsonResponse> {

			@Override
			protected JsonResponse doInBackground(String... params) {
				Log.d("GetLeaderBoard", "Starting backgound");
				ServerConnection serverConnection = (ServerConnection) getActivity().getApplication();
				JsonResponse jsonResponse = new JsonResponse();
				try {

					String url = params[0] + params[1];
					jsonResponse = serverConnection.get(url);

				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return jsonResponse;

			}
			@Override
			protected void onPostExecute(JsonResponse jsonResponse) {
				super.onPostExecute(jsonResponse);
				if(jsonResponse.hasErrors()) {
					System.out.println("Get leaderboard has erros: " + jsonResponse.getMessage());
				} else {
					ObjectMapper mapper = new ObjectMapper();
					try {
						List<LeaderboardEntry> leaderboardEntries = mapper.readValue(jsonResponse.getMessage(), new TypeReference<List<LeaderboardEntry>>(){});
						System.out.println("Leaderboard Entrys from server: " + leaderboardEntries);
						
						adapter.setLeaderboardEntries(leaderboardEntries);
						Log.d("GetLeaderboard", "Notifying adapterof data set changed");
						//adapter.notifyDataSetChanged(); //Not working this function is called inside the setter
						
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			}

		}
	}
}
