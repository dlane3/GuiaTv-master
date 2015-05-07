package com.lteixeira.guiatv;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class ChannelListActivity extends ListActivity implements ChannelList{

	//Debug Tag
	public final static String TAG = "ChannelListActivity";
	private ProgressDialog dialog;
	
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		dialog = new ProgressDialog(this);
		if(((GuiaTvApp)getApplication()).getChannels() == null)
			updateChannels();
		else
			setListAdapter(new ChannelsAdapter(this, R.layout.list_row, ((GuiaTvApp)getApplication()).getChannels()));
		Log.d(TAG,"CREATED");

	}


	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
    {
		super.onListItemClick(l, v, position, id);
		Intent scheduleIntent = new Intent(this,Schedule.class);
		Channel channel = (Channel) l.getItemAtPosition(position);
		((GuiaTvApp)getApplication()).setChoice(channel);
		startActivity(scheduleIntent);
		Log.d(TAG, channel.toString());
 	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu)
    {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_channel_list, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle item selection
        switch (item.getItemId())
        {
            case R.id.menu_settings:
                Toast.makeText(getBaseContext(), "You Clicked on the Settings OPTION", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.favorite_settings:
                Toast.makeText(getBaseContext(), "You Clicked on the Favorite Shows OPTION", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(this, MyFavActivity.class);
				startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	
	public void updateChannels(){
		try {
			URL url = new URL("http://services.sapo.pt/EPG/GetChannelList");
			RequestChannelsTask request = new RequestChannelsTask(this);
			request.execute(url);
		} catch (MalformedURLException e) {
		}	
	}



	@Override
	public void startDialog() {
		dialog.setMessage("Downloading...");
		dialog.show();
	}



	@Override
	public void removeDiolog() {
		if(dialog.isShowing())
			dialog.dismiss();
	}



	@Override
	public void saveResults(List<Channel> result) {
		((GuiaTvApp)getApplication()).setChannels(result);
	}



	@Override
	public void fillList(List<Channel> result) {
		ChannelsAdapter adapter = new ChannelsAdapter(this, R.layout.list_row, result);
		setListAdapter(adapter);
	}

}
