package com.example.noisetubeinteractive2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.noisetube.models.Badge;
import com.noisetube.models.BadgeCompare;

public class BadgesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_badges);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			.add(R.id.container, new BadgeFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.badges, menu);
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
	public static class BadgeFragment extends Fragment {

		List<Badge> badges = new ArrayList<Badge>();

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_badges,
					container, false);

			populateListView(rootView);

			return rootView;
		}

		private void populateListView(View rootView) {

			// TODO retrieve correct users from the API (remove test data)
			badges.add(new Badge("badge_1x", "First 1", "Successfully do a measurement", false));
			badges.add(new Badge("badge_facebook", "Charing 2", "Post results to facebook", true));
			badges.add(new Badge("badge_friendster", "Friends 3", "Ask your friends to join", true));
			badges.add(new Badge("badge_google_like", "Liked 4", "Like us on Google+", true));
			badges.add(new Badge("badge_friendster", "Friends 5", "Ask your friends to join", false));
			badges.add(new Badge("badge_1x", "First 6", "Successfully do a measurement", false));
			badges.add(new Badge("badge_facebook", "Charing 7", "Post results to facebook", true));
			badges.add(new Badge("badge_google_like", "Liked 8", "Like us on Google+", true));
			System.out.println(badges);

			Collections.sort(badges, new BadgeCompare());

			System.out.println(badges);
			// Build Adapter with custom view reslolver

			ArrayAdapter<Badge> adapter = new MyListAdapter();

			//Configure the list view
			ListView listView = (ListView)  rootView.findViewById(R.id.badges_list);
			listView.setAdapter(adapter);
		}

		private class MyListAdapter extends ArrayAdapter<Badge> {
			public MyListAdapter() {
				super(getActivity(), R.layout.fragment_badges_list_item, badges);
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				// Make sure we have a view to inflate because for the fist element we dont have this view
				View itemView = convertView;
				if (itemView == null) {
					LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
					itemView = inflater.inflate(R.layout.fragment_badges_list_item, parent, false);
				}

				//Selecting correct user from the arraylist
				Badge badge = badges.get(position); 

				//Filling the view
				TextView textName = (TextView) itemView.findViewById(R.id.badge_name);
				TextView textDescription = (TextView) itemView.findViewById(R.id.badge_desctiption);
				ImageView imageIcon = (ImageView) itemView.findViewById(R.id.badge_icon);

				textName.setText(badge.getName());
				textDescription.setText(badge.getDescription());

				//Getting drawable with the name corresponding in badge
				Context context = imageIcon.getContext();
				int id = context.getResources().getIdentifier(badge.getIcon(), "drawable",context.getPackageName());
				imageIcon.setImageResource(id);
				if (!badge.isAchieved()) {
					imageIcon.setAlpha(Float.valueOf("0.3"));
					textDescription.setAlpha(Float.valueOf("0.3"));
					textName.setAlpha(Float.valueOf("0.3"));
				} else {
					imageIcon.setAlpha(Float.valueOf("1"));
					textDescription.setAlpha(Float.valueOf("1"));
					textName.setAlpha(Float.valueOf("1"));
				}

				return itemView;
			}

		}
	}

}
