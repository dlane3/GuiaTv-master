package com.lteixeira.guiatv;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Owner on 5/6/2015.
 */
public class GetAllFavListViewAdapter extends BaseAdapter
{
    private JSONArray dataArray; // to handle the json being used
    private Activity activity; // activity to be inflated
    private static LayoutInflater inflater = null;


    public GetAllFavListViewAdapter(JSONArray jArray, Activity a)
    {
        // passing into the constructor both the json array and activity
        this.dataArray = jArray;
        this.activity = a;

        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.dataArray.length();
    } // this passes back the number of items in the data array

    @Override
    public Object getItem(int position) {
        return position;
    } // this returns the position of the item

    @Override
    public long getItemId(int position) {
        return position;
    } // also returns the position of the item

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // set up the convert view if it's null
        TextView cell;
        if(convertView==null)
        {  // so if it is null, it has not been used before - you must inflate the view first
            convertView = inflater.inflate(R.layout.fav_list ,null);
            cell = new TextView();
            //cell.Show = (TextView) convertView.findViewById(R.id.Fav_list);
            //cell.Channel = (TextView) convertView.findViewById(R.id.Fav_channel);
            //cell.ID = (TextView) convertView.findViewById(R.id.RegNumber);

            convertView.setTag(cell);  // set the tag so I can reuse it.
        }
        else
        {
            cell = (TextView) convertView.getTag();
        }
        // change the data of the cell here...
        // get the data from the JSON array and use the position that was passed into the getView function
        try
        {
            JSONObject jsonObject = this.dataArray.getJSONObject(position);

            // Populate the view using the JSON object
            //cell.Show.setText("Show: "+jsonObject.getString("Show"));
            //cell.Channel.setText("Channel: "+jsonObject.getString("Channel"));
            //cell.Brand.setText("Brand: "+jsonObject.getString("Brand"));
            //cell.RegNumber.setText("RegNumber: "+jsonObject.getString("RegNumber"));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


        return convertView;
    }

    private class TextView
    {
        private TextView Show;
        private TextView Channel;


        // private TextView ID;
        // private TextView RegNumber;
    }


}
