package org.kafkaparser.pojo;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect 
public class Data {

	private ArrayList<String> searchTerms;
	private String url;
	
	private String botName="RTS";


	public String getBotName() {
		return botName;
	}
	public void setBotName(String botName) {
		this.botName = botName;
	}
	public ArrayList<String> getSearchTerms() {
		return searchTerms;
	}
	public void setSearchTerms(ArrayList<String> searchTerms) {
		this.searchTerms = searchTerms;
	}
	
	@JsonCreator
	public Data(@JsonProperty("url")String url,@JsonProperty("searchTerms")ArrayList<String> searchTerms,@JsonProperty("botName") String botName)
	{
		this.url=url;
		this.searchTerms=searchTerms;
		this.botName = botName;
		
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}


	

	
	@Override public String toString()
	{
		return "data is "+url+"cool man";
	}
}
