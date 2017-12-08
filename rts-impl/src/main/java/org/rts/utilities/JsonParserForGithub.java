
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

public class JsonParserForGithub {
	
	public static JSONParser parser = new JSONParser();
	public static Set<String> previousSet = new HashSet<String>();
	public static Set<String> presentSet;
	public static Set<String> diffSet = new HashSet<String>();
	public static Queue<String> evictingQueue= EvictingQueue.create(100);
	
	public static Set<String> githubUrlFetcher(String url) throws ParseException, InterruptedException
	{
		 Object obj = parser.parse(HttpUtilities.sendGet(url));
		 JSONObject jsonObject = (JSONObject) obj;
         JSONArray array = (JSONArray)jsonObject.get("items");
         Iterator<?> iterator = array.iterator();
         presentSet = new HashSet<String>();
         while(iterator.hasNext())
         {
        	 JSONObject eachJsonObject = (JSONObject) iterator.next();
        	 String html_url=(String)eachJsonObject.get("html_url");
        	 if(!evictingQueue.contains(html_url))
        	 {
        		 //System.out.println("Github "+html_url);
        		 presentSet.add(html_url);
        	 }
        	 evictingQueue.add(html_url);
        	 //System.out.println(eachJsonObject.get("html_url"));
         }
         //diffSet=Difference.getDiff(previousSet, presentSet);
         //previousSet=presentSet;
         return presentSet;
     }
         
	
		

	
	public static void main(String args []) throws InterruptedException
	{
		try {
			JsonParserForGithub.githubUrlFetcher("https://api.github.com/search/code?q=olacabs&sort=indexed&order=asc&access_token=ac2536a0beb59624d879e10084fe2d04404451bf&");
			//JsonParser.githubUrlFetcher("https://pastebin.com/archive");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}







}
