package com.lteixeira.guiatv;

import java.util.List;

public interface ChannelList {
	public void startDialog();
	public void removeDiolog();
	public void saveResults(List<Channel> result);
	public void fillList(List<Channel> result);
}
