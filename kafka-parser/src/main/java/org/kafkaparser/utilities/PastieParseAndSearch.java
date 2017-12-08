package org.kafkaparser.utilities;

import java.io.IOException;
import java.util.ArrayList;

import org.kafkaparser.pojo.Data;

public class PastieParseAndSearch {
	
	static
	{
		try {
			ConfigParams.initialzie();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ArrayList<String> fetchids(String archiveUrl,String regex) throws IOException, InterruptedException
	{
		//ConfigParams.initialzie();
		System.out.println(archiveUrl);
		System.out.println(HttpUtilities.sendGet(archiveUrl));
		ArrayList<String> ids= Search.extractRegexMatches(HttpUtilities.sendGet(archiveUrl), regex);
		for (String each:ids)
		{
			System.out.println(each);
		}
		return ids;
	}
	
	public static void searchEachid(Data data) throws IOException
	{
		Search.find(data);
		/**
		if(termsFound.size()<=0)
			return false;
		else
			return true;
		*/
	}
	
	public static void main (String [] args) throws InterruptedException
	{
		ArrayList<String> ids;
		try {
			//ids = PastieParseAndSearch.fetchids("https://pastebin.com/archive", "href=\"/(\\w{8})\">");
			ids = PastieParseAndSearch.fetchids("https://slexy.org/recent", "<a href=\"/view/([a-zA-Z0-9]+)\">View paste</a>");
			
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("method434");
		for(String id:ids)
		{
			System.out.println("Testing currently"+id);
//			Data temp=PastieParseAndSearch.searchEachid(new Data("https://pastebin.com/raw/{id}".replace("{id}", id),list));
//			if(temp!=null)
//			{
//				System.out.print("found in "+temp.getUrl());
//			}
//			else
//			{
//				System.out.println("notfound");
//			}
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
