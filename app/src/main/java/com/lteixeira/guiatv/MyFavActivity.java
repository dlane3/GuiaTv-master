package com.lteixeira.guiatv;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MyFavActivity extends Activity
{

    private ListView GetAllFavListView;
    private JSONArray jArray;
    //private static LayoutInflater inflater = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fav_list);
        this.GetAllFavListView = (ListView) this.findViewById(R.id.Fav_list);
        this.GetAllFavListView = (ListView) this.findViewById(R.id.Fav_channel);

        new GetAllFavsTask().execute(new ApiConnector());

    }



    public void setListAdapter(JSONArray jArray) // after we have the data, set the listview here...
    {
        this.jArray = jArray;
        this.GetAllFavListView.setAdapter(new GetAllFavListViewAdapter(jArray, this));
    }

    public class GetAllFavsTask extends AsyncTask<ApiConnector, Long, JSONArray>
    {

        @Override
        protected JSONArray doInBackground(ApiConnector... params)
        {
            // This is executed in the background thread
            return params[0].GetFavShows();
        }

        @Override
        protected void onPostExecute(JSONArray jArray)
        {
            // once do in background is done - it sends the result to the main thread...here
            setListAdapter(jArray);
        }

    }


  //  @Override
  //  public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
 //       getMenuInflater().inflate(R.menu.menu_my_fav, menu);
  //      return true;
  //  }

  //  @Override
  //  public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    //    int id = item.getItemId();

        //noinspection SimplifiableIfStatement
   //     if (id == R.id.action_settings) {
    //        return true;
      //  }

     //   return super.onOptionsItemSelected(item);
  //  }
}
