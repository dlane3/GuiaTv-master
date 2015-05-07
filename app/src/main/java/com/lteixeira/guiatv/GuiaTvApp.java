package com.lteixeira.guiatv;

import java.util.List;

import android.app.Application;
import android.text.format.Time;
import android.util.Log;

public class GuiaTvApp extends Application {
	
	//Debug Tag
	public final static String TAG = "GuiaTvApp";
	
	private Channel choice;
	private Channel last;
	private Time day;
	List<Channel> channels;
	private Show show;
	List<Show> shows;
	
	public int positionFirstShow(){
		Time today = new Time();
		today.setToNow();
		if(day.monthDay == today.monthDay){
			for(int i = 0; i < shows.size(); i++){
				if(shows.get(i).getInicio().after(today)){
					Log.d(TAG,"True i="+i);
					Log.d(TAG,"True ini="+shows.get(i).getInicio().toMillis(true));
					Log.d(TAG,"True tod="+today.toMillis(true));
					return i - 2; //para mostrar também o último programa antes do actual
				}
			}
		}
		return 0;
	}
	
	public Channel getLast() {
		return last;
	}

	public void setLast(Channel last) {
		this.last = last;
	}
	
	public List<Channel> getChannels() {
		return channels;
	}

	public void setChannels(List<Channel> channels) {
		this.channels = channels;
	}

	public List<Show> getShows() {
		return shows;
	}

	public void setShows(List<Show> shows) {
		this.shows = shows;
	}

	public Channel getChoice(){
		return choice;
	}
	
	public void setChoice(Channel choice){
		this.choice = choice;
	}
	
	public Show getShow(){
		return show;
	}
	
	public void setShow(Show show){
		this.show = show;
	}
	
	public Time getDay() {
		return day;
	}

	public void setDay(Time day) {
		this.day = day;
	}
}