package com.lteixeira.guiatv;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import android.text.format.Time;
import android.util.Log;
import android.util.Xml;

public class RequestScheduleTask extends AsyncTask<URL, Integer, List<Show>> {
	//Debug Tag
	public static final String TAG = "RequestScheduleTask";
	
	static final String KEY_RESPONSE = "GetChannelByDateIntervalResponse";
	static final String KEY_RESPONSE_II = "GetChannelByDateIntervalResult";
	static final String KEY_LIST = "Programs";
	static final String KEY_SHOW = "Program";
	static final String KEY_ID = "Id";
	static final String KEY_NAME = "Title";
	static final String KEY_DESCRIPTION = "Description";
	static final String KEY_INICIO = "StartTime";
	static final String KEY_FIM = "EndTime";
	
	private ScheduleList callback;
	
	RequestScheduleTask(ScheduleList callback){
		this.callback = callback;
	}
	
	@Override
	protected void onPreExecute() {
		callback.showDialog();
	}

	@Override
	protected List<Show> doInBackground(URL... urls) {
		URL url = urls[0];
		List<Show> response;
		HttpURLConnection urlConnection = null;
		try{
			Log.d(TAG,url.toString());
			urlConnection = (HttpURLConnection) url.openConnection();
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			response = xmlToShow(in);
			return response;
		}catch(IOException ex){
			Log.d(TAG,"IOException");
			InputStream in = urlConnection.getErrorStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String line = null;
			try{
			while((line = reader.readLine()) != null)
				sb.append(line);
			in.close();
			Log.d(TAG,"IOException " + sb.toString());
		
			}catch(IOException e){
				
			}
		}catch(XmlPullParserException ex){
			
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<Show> result) {
		callback.removeDialog();
		callback.saveSchedule(result);
		callback.fillList(result);
	}
	
	private List<Show> xmlToShow(InputStream in) throws XmlPullParserException, IOException {
		try{
			Log.d(TAG,"xmlToShow");
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in,null);
			parser.nextTag();
			return readShowList(parser);
		}finally{
			in.close();
		}
	}
	
	private List<Show> readShowList(XmlPullParser parser) throws XmlPullParserException, IOException {
		List<Show> shows = new ArrayList<Show>();
		parser.require(XmlPullParser.START_TAG, null, KEY_RESPONSE);
		
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG)
				continue;
			
			String name = parser.getName();
			if(name.equals(KEY_RESPONSE_II))
				shows = readShowListII(parser);
			else
				skip(parser);
		}
		return shows;
	}

	private List<Show> readShowListII(XmlPullParser parser) throws XmlPullParserException, IOException {
		List<Show> shows = new ArrayList<Show>();
		parser.require(XmlPullParser.START_TAG, null, KEY_RESPONSE_II);
		
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG)
				continue;
			
			String name = parser.getName();
			if(name.equals(KEY_LIST))
				shows = readShowListIII(parser);
			else
				skip(parser);
		}
		return shows;
	}

	private List<Show> readShowListIII(XmlPullParser parser) throws XmlPullParserException, IOException {
		List<Show> shows = new ArrayList<Show>();
		parser.require(XmlPullParser.START_TAG, null, KEY_LIST);
		
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG)
				continue;
			
			String name = parser.getName();
			if(name.equals(KEY_SHOW))
				shows.add(readShow(parser));
			else
				skip(parser);
		}
		return shows;
	}

	private Show readShow(XmlPullParser parser) throws XmlPullParserException, IOException {
		int id = 0;
		String showName = null;
		String description = null;
		Time inicio = new Time();
		Time fim = new Time();
		parser.require(XmlPullParser.START_TAG, null, KEY_SHOW);
		
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG)
				continue;
			
			String name = parser.getName();
			if(name.equals(KEY_ID))
				id = readId(parser);
			else if(name.equals(KEY_NAME))
				showName = readName(parser);
			else if(name.equals(KEY_DESCRIPTION))
				description = readDescription(parser);
			else if(name.equals(KEY_INICIO))
				inicio = readTime(parser,KEY_INICIO);
			else if(name.equals(KEY_FIM))
				fim = readTime(parser,KEY_FIM);
			else
				skip(parser);
		}
		return new Show(id,showName,description,inicio,fim);
	}

	private Time readTime(XmlPullParser parser,String key) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, key);
		String time = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, key);
		return parseTime(time);
	}
	
	private Time parseTime(String time) {
		StringTokenizer st = new StringTokenizer(time," ");
		String data = st.nextToken();
		String hours = st.nextToken();
		st = new StringTokenizer(data,"-");
		int year = Integer.parseInt(st.nextToken());
		int month = Integer.parseInt(st.nextToken()) - 1;
		int day = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(hours,":");
		int hour = Integer.parseInt(st.nextToken());
		int minute = Integer.parseInt(st.nextToken());
		
		Time t = new Time();
		t.set(0, minute, hour, day, month, year);
		return t;
	}

	private String readDescription(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, KEY_DESCRIPTION);
		String description = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, KEY_DESCRIPTION);
		return description;
	}

	private String readName(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, KEY_NAME);
		String name = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, KEY_NAME);
		return name;
	}

	private int readId(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, KEY_ID);
		String id = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, KEY_ID);
		return Integer.parseInt(id);
	}

	private String readText(XmlPullParser parser) throws XmlPullParserException, IOException{
		String result = "";
		if(parser.next() == XmlPullParser.TEXT){
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}
	
	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
		if(parser.getEventType() != XmlPullParser.START_TAG)
			throw new IllegalStateException();
		int depth = 1;
		while(depth != 0){
			switch(parser.next()){
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}
	

}
