package com.lteixeira.guiatv;

import java.util.List;

public interface ScheduleList {
	public void showDialog();
	public void removeDialog();
	public void saveSchedule(List<Show> result);
	public void fillList(List<Show> result);	
}
