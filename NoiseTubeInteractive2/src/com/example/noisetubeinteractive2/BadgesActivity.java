package com.example.noisetubeinteractive2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noisetube.main.JsonResponse;
import com.noisetube.main.ServerConnection;
import com.noisetube.models.Badge;
import com.noisetube.models.BadgeCompare;
import com.vub.storage.BadgeStorage;
import com.vub.storage.SessionStorage;

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

		
		ArrayAdapter<Badge> adapter;
		ListView listView;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_badges,
					container, false);

			populateListView(rootView);

			return rootView;
		}

		private void populateListView(View rootView) {
			//Load Badges
			UpdateBadges updateBadges = new UpdateBadges();
			updateBadges.execute();
			
			BadgeStorage badgeStorage = new BadgeStorage(getActivity());
			List<Badge> badges = new ArrayList<Badge>();
			badges = badgeStorage.getBadgeList();
			
			if (badges != null) {		
				Collections.sort(badges, new BadgeCompare());
				System.out.println("From Storage: " + badges);	
			} 
			
			// Build Adapter with custom view resolver
			adapter = new MyListAdapter(getActivity().getApplicationContext(),badges);
			
			//Configure the list view
			listView = (ListView)  rootView.findViewById(R.id.badges_list);
			listView.setAdapter(adapter);
			
		}
		
		private class UpdateBadges extends AsyncTask<Void, Void, List<Badge>> {
			
			boolean changed = false;
			
			@Override
			protected List<Badge> doInBackground(Void... params) {
				try {
					ServerConnection serverConnection = (ServerConnection) getActivity().getApplication();
					SessionStorage sessionStorage = new SessionStorage(getActivity());
					BadgeStorage badgeStorage = new BadgeStorage(getActivity());
					String session = sessionStorage.getSession();
					changed = false;
					if (session != null) {
						JsonResponse jsonResponse = serverConnection.get("badge");
						JsonResponse jsonResponse2 = serverConnection.get("badge/" + session);
						System.out.println(jsonResponse.toString());
						System.out.println(jsonResponse2.toString());
						
						if (!jsonResponse.hasErrors()) {
							ObjectMapper mapper = new ObjectMapper();
							List<Badge> badges2 = mapper.readValue(jsonResponse.getMessage(), new TypeReference<List<Badge>>(){});
							JSONObject json = new JSONObject(jsonResponse2.getMessage());
							List<Integer> achievedList = new ArrayList<Integer>();
							
							JSONArray jsonArray = json.getJSONArray("badges");
							
							for (int i = 0; i< jsonArray.length(); i++) {
								achievedList.add(jsonArray.getInt(i));
							}
							
							for (Badge b : badges2) {
								if (achievedList.contains(b.getIdBadge())) {
									b.setAchieved(true);
								}
							}
							
							badgeStorage.setBadgeList(badges2);
							//badges = badges2;
							
							changed = true;
							
							System.out.println(badges2.toString());
							System.out.println(achievedList);
							
							return badges2;
						}
						
					} 
					
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				return null;
			}
			
			@Override
			protected void onPostExecute(List<Badge> badges) {
				super.onPostExecute(badges);
				if (changed) {
					System.out.println("Lengt On Post Execute:" + badges.size());
					adapter.clear();
					System.out.println("Cleared adapter");
					adapter.addAll(badges);
					//adapter.notifyDataSetChanged();
				}
			}
		}

		private class MyListAdapter extends ArrayAdapter<Badge> {
			public MyListAdapter(Context context, List<Badge> badges) {
				super(getActivity(), R.layout.fragment_badges_list_item, badges);
				System.out.println("Size in Adapter Constructor: " + badges.size());
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				// Make sure we have a view to inflate because for the fist element we don't have this view
				View itemView = convertView;
				if (itemView == null) {
					LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
					itemView = inflater.inflate(R.layout.fragment_badges_list_item, parent, false);
				}

				//Selecting correct user from the arraylist
				Badge badge = getItem(position); 

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
