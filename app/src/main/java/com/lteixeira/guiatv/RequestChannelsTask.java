package com.lteixeira.guiatv;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import android.util.Xml;

public class RequestChannelsTask extends AsyncTask<URL, Integer, List<Channel>>{
	static final String KEY_RESPONSE = "GetChannelListResponse";
	static final String KEY_LIST = "GetChannelListResult";
	static final String KEY_CHANNEL = "Channel";
	static final String KEY_NAME = "Name";
	static final String KEY_SIGLA = "Sigla";

	
	//Debug TAG
	public final static String TAG = "REQUESTTASK";
	
	private ChannelList callback;
	
	public RequestChannelsTask(ChannelList callback) {
		this.callback = callback;
	}
	
	protected void onPreExecute(){
		callback.startDialog();
	}
	
	@Override
	protected List<Channel> doInBackground(URL... urls) {
		URL url = urls[0];
		List<Channel> response ;
		try{
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			response = xmlToChannel(in);
			return response;
		}catch(IOException ex){
			
		}catch(XmlPullParserException ex){
			
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<Channel> result) {
		callback.removeDiolog();
		callback.saveResults(result);
		callback.fillList(result);
	}
	
	private List<Channel> xmlToChannel(InputStream response) throws XmlPullParserException, IOException{
		try{
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(response,null);
			parser.nextTag();
			return readChannelList(parser);
		}finally{
			response.close();
		}
	}
	
	
	
	private List<Channel> readChannelList(XmlPullParser parser) throws XmlPullParserException, IOException {
		List<Channel> channels = new ArrayList<Channel>();
		parser.require(XmlPullParser.START_TAG, null, KEY_RESPONSE);
		
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG)
				continue;
			
			String name = parser.getName();
			if(name.equals(KEY_LIST))
				channels = readChannelListII(parser);
			else
				skip(parser);
		}
			return channels;
	}

	private List<Channel> readChannelListII(XmlPullParser parser) throws XmlPullParserException, IOException{
		List<Channel> channels = new ArrayList<Channel>();
		parser.require(XmlPullParser.START_TAG, null, KEY_LIST);
		
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG)
				continue;
			
			String name = parser.getName();
			if(name.equals(KEY_CHANNEL))
				channels.add(readChannel(parser));
			else
				skip(parser);
		}
			return channels;
	}

	private Channel readChannel(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, KEY_CHANNEL);
		String sigla = null;
		String channelName = null;
		
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG)
				continue;
			String name = parser.getName();
			if(name.equals(KEY_NAME))
				channelName = readName(parser);
			else if(name.equals(KEY_SIGLA))
				sigla = readSigla(parser);
			else
				skip(parser);
		}
		return new Channel(sigla, channelName);
	}

	private String readSigla(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, KEY_SIGLA);
		String sigla = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, KEY_SIGLA);
		return sigla;
	}

	private String readText(XmlPullParser parser) throws XmlPullParserException, IOException {
		String result = "";
		if(parser.next() == XmlPullParser.TEXT){
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}

	private String readName(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, KEY_NAME);
		String name = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, KEY_NAME);
		return name;
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
