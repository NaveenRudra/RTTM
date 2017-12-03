package org.rts.utilities;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.common.collect.EvictingQueue;
import org.kafkaparser.utilities.HttpUtilities;

public class JsonParserForReddit {
	
	public static JSONParser parser = new JSONParser();
	public static Set<String> previousSet = new HashSet<String>();
	public static Set<String> presentSet;
	public static Set<String> diffSet = new HashSet<String>();
	public static Queue<String> evictingQueue= EvictingQueue.create(100);
	
	public static Set<String> redditUrlFetcher(String url) throws ParseException, InterruptedException
	{
		
		 
		 Object obj = parser.parse(HttpUtilities.sendGet(url));
         JSONArray array = (JSONArray)((JSONObject) ((JSONObject) obj).get("data")).get("children");
         @SuppressWarnings("unchecked")
		 Iterator<JSONObject> iterator = array.iterator();
         presentSet = new HashSet<String>();
         while(iterator.hasNext())
         {
        	 JSONObject eachJsonObject = (JSONObject) iterator.next();
        	 String redditUrl=(String)(((JSONObject)eachJsonObject.get("data")).get("url")); 
        	 if(!evictingQueue.contains(redditUrl))
        	 {
        		 presentSet.add(redditUrl);
        	 }
        	 evictingQueue.add(redditUrl);
         }
         return diffSet;
     }
         
	
		

	
	public static void main(String args []) throws InterruptedException
	{
		try {
			JsonParserForReddit.redditUrlFetcher("https://www.reddit.com/search.json?q=olacabs%20hacked");
			//JsonParser.githubUrlFetcher("https://pastebin.com/archive");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
