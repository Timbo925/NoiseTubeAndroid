package com.example.noisetubeinteractive2;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.noisetube.models.LeaderboardType;
import com.noisetube.models.UserLeaderboard;

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
				return ScoreLeaderboardFragment.newInstance(LeaderboardType.SCORE);
			case 1:
				return ScoreLeaderboardFragment.newInstance(LeaderboardType.LEVEL);
			case 2:
				return ScoreLeaderboardFragment.newInstance(LeaderboardType.AMOUNT);
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

		private List<UserLeaderboard> users = new ArrayList<UserLeaderboard>();
		private boolean populated = false;

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

			populateListView(rootView);

			return rootView;
		}

		private void populateListView(View rootView) {
			// Create list of items
			// TODO retreive correct users from the API
			if (!populated) {
				populated = true;
				LeaderboardType type = (LeaderboardType) getArguments().getSerializable(TYPE);
				System.out.println("Get Type: " + type);
				for (int i = 0;i < 8; i++) {
					users.add(new UserLeaderboard("Timbo", 22*(9-i)));
					users.add(new UserLeaderboard("Jobo", 23*(9-i)));
					users.add(new UserLeaderboard("Monique", 24*(9-i)));		
				}

			}

			// Build Adapter with custom view reslolver
			ArrayAdapter<UserLeaderboard> adapter = new MyListAdapter();

			//Configure the list view
			ListView listView = (ListView)  rootView.findViewById(R.id.leaderboard_listview_score);
			listView.setAdapter(adapter);
		}

		private class MyListAdapter extends ArrayAdapter<UserLeaderboard> {
			public MyListAdapter() {
				super(getActivity(), R.layout.fragment_leaderboard_score_item, users);
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				// Make sure we have a view to inflate because for the fist element we dont have this view
				View itemView = convertView;
				if (itemView == null) {
					LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
					itemView = inflater.inflate(R.layout.fragment_leaderboard_score_item, parent, false);
				}

				//Selecting correct user from the arraylist
				UserLeaderboard userLeaderboard = users.get(position); 

				//Filling the view
				TextView textUserName = (TextView) itemView.findViewById(R.id.leaderboard_username);
				TextView textScore = (TextView) itemView.findViewById(R.id.leaderboard_points);

				textUserName.setText(userLeaderboard.getUserName());
				textScore.setText(Integer.toString(userLeaderboard.getPoints()));
				return itemView;
			}


		}
	}
}
