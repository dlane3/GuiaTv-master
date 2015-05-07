package com.lteixeira.guiatv;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ChannelsAdapter extends ArrayAdapter<Channel> {

	List<Channel> channels;
	Context context;
	
	public ChannelsAdapter(Context context, int resource, List<Channel> objects) {
		super(context, resource, objects);
		channels = objects;
		this.context = context;
	}

	public View getView(int position, View convertView, ViewGroup parent){
		View v = convertView;
		if(v == null){
			LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.list_row, null);
		}
		
		Channel channel = channels.get(position);
		
		if(channel != null){
			TextView sigla = (TextView) v.findViewById(R.id.rowSigla);
			TextView name = (TextView) v.findViewById(R.id.rowName);
			
			if(sigla != null)
				sigla.setText(channel.getSigla());
			
			if(name != null)
				name.setText(channel.getName());
		}
		return v;
	}




}
