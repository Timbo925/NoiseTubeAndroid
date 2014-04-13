package com.example.noisetubeinteractive2;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	public final static String EXTRA_MESSAGE = "com.example.noisetubeinteractive2.EXTRA_MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	public void sendMessage(View view) {
		//Resoponding on teh button click 'Send'
		Intent intent = new Intent(this, DisplayMessageActivity.class); //Intent used to start another activity (binding between seperate components)
																		//2nd par: class of component witch to deliver the intent
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message); //Values linked to data, EXTRA_MESSAGE should be added as public final static
		
		startActivity(intent);
	}
	
	public void startProfile() {
		Intent intent = new Intent(this, NewProfileActivity.class);
		startActivity(intent);
	}

}
