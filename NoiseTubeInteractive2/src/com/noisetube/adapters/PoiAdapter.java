package com.noisetube.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.noisetubeinteractive2.R;
import com.noisetube.models.Poi;
import com.noisetube.models.LeaderboardType;


public class PoiAdapter extends ArrayAdapter<Poi> {

	private List<Poi>  pois;
	private Context context;
	
	public PoiAdapter(List<Poi> pois, Context ctx) {
		super(ctx, R.layout.fragment_poi_list_item, pois);
		this.pois = pois;
		this.context = ctx;
	}
	
	public List<Poi> getPois() {
		return pois;
	}

	public void setPois(List<Poi> poisIn) {
		//Log.d("MyListAdapter", "Set dataset: " + pois);
		pois.clear();
		pois.addAll(poisIn);
		this.notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		// Make sure we have a view to inflate because for the fist element we dont have this view
		View itemView = convertView;
		if (itemView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
			itemView = inflater.inflate(R.layout.fragment_poi_list_item, parent, false);
		}

		//Selecting correct user from the arraylist
		Poi poi = pois.get(position);

		//Filling the view
		//Log.d("Adapter" , "Expanding");
		TextView textTitle = (TextView) itemView.findViewById(R.id.poi_item_title);
		TextView textMulti = (TextView) itemView.findViewById(R.id.poi_item_multi);
		TextView textBonus = (TextView) itemView.findViewById(R.id.poi_item_bonus);
		TextView textDistance = (TextView) itemView.findViewById(R.id.poi_item_distance);
		
		textTitle.setText(poi.getName());
		textMulti.setText(Float.toString(poi.getBonusMulti()));
		textBonus.setText(Float.toString(poi.getBonusPoints()));
		textDistance.setText((Double.toString(poi.getDistance())) + " km" );
		
		return itemView;
	}


}