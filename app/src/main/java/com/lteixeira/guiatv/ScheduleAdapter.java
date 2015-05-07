package com.lteixeira.guiatv;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ScheduleAdapter extends ArrayAdapter<Show> {
	public static final String TAG = "ScheduleAdapter";

	List<Show> shows;
	Context context;
	
	public ScheduleAdapter(Context context, int resource, List<Show> objects) {
		super(context, resource, objects);
		Log.d(TAG, "Construtor");
		shows = objects;
		this.context = context;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		View v = convertView;
		if(v == null){
			LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.list_row, null);
		}
		
		Show show = shows.get(position);
		Time now = new Time();
		now.setToNow();
		
		if(show != null){
			TextView startTime = (TextView) v.findViewById(R.id.rowSigla);
			TextView name = (TextView) v.findViewById(R.id.rowName);
			startTime.setTextColor(Color.BLACK);
			name.setTextColor(Color.BLACK);
			
			if(startTime != null)
				startTime.setText(String.format("%02d:%02d",show.getInicio().hour,show.getInicio().minute));
			
			if(name != null)
				name.setText(show.getName());
			
			if(show.getFim().before(now)){
				startTime.setTextColor(Color.GRAY);
				name.setTextColor(Color.GRAY);
			}
		}
		return v;
	}
	
}
