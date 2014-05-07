package com.noisetube.adapters;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.noisetubeinteractive2.R;
import com.noisetube.models.LeaderboardEntry;
import com.noisetube.models.LeaderboardType;

public class LeaderboardAdapter extends ArrayAdapter<LeaderboardEntry> {

	private List<LeaderboardEntry>  leaderboardEntries;
	private Context context;
	private LeaderboardType type;
	
	public LeaderboardAdapter(List<LeaderboardEntry> leaderboardEntries, Context ctx, LeaderboardType type) {
		super(ctx, R.layout.fragment_leaderboard_score_item, leaderboardEntries);
		this.leaderboardEntries = leaderboardEntries;
		this.context = ctx;
		this.type = type;
	}
	
	public List<LeaderboardEntry> getLeaderboardEntries() {
		return leaderboardEntries;
	}

	public void setLeaderboardEntries(List<LeaderboardEntry> leaderboardEntriesIn) {
		Log.d("MyListAdapter", "Set dataset: " + leaderboardEntries);
		leaderboardEntries.clear();
		if (leaderboardEntriesIn != null) {
			leaderboardEntries.addAll(leaderboardEntriesIn);
			this.notifyDataSetChanged();
		}
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		// Make sure we have a view to inflate because for the fist element we dont have this view
		View itemView = convertView;
		if (itemView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
			itemView = inflater.inflate(R.layout.fragment_leaderboard_score_item, parent, false);
		}

		//Selecting correct user from the arraylist
		LeaderboardEntry leaderboardEntry = leaderboardEntries.get(position);

		//Filling the view
		//Log.d("Adapter" , "Expanding");
		TextView textUserName = (TextView) itemView.findViewById(R.id.leaderboard_username);
		TextView textScore = (TextView) itemView.findViewById(R.id.leaderboard_points);
		TextView textRank = (TextView) itemView.findViewById(R.id.leaderboard_rank);

		textUserName.setText(leaderboardEntry.getUserName());
		textRank.setText(Integer.toString(leaderboardEntry.getRank()));
		
		if (LeaderboardType.maxExp == type) {
			textScore.setText(Integer.toString(leaderboardEntry.getMaxExp()));
		} else if (LeaderboardType.amountMeasurments == type) {
			textScore.setText(Integer.toString(leaderboardEntry.getAmountMeasurments()));
		} else {
			textScore.setText(Integer.toString(leaderboardEntry.getLevel()));
		}
		
		return itemView;
	}


}