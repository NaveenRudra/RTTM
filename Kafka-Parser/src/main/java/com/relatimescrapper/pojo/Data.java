package com.relatimescrapper.pojo;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect 
public class Data {

	private ArrayList<String> searchTerms;


	public ArrayList<String> getSearchTerms() {
		return searchTerms;
	}
	public void setSearchTerms(ArrayList<String> searchTerms) {
		this.searchTerms = searchTerms;
	}
	@JsonCreator
	public Data(@JsonProperty("url")String url,@JsonProperty("searchTerms")ArrayList<String> searchTerms)
	{
		this.url=url;
		this.searchTerms=searchTerms;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}


	private String url;

	
	@Override public String toString()
	{
		return "data is "+url+"cool man";
	}
}
