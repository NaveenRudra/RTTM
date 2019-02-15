package org.kafkaparser.utilities;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.google.common.io.Resources;




public class HttpUtilities {
	
	private static String User_Agent="Mozilla/5.0";
	private static List<String> userAgents;
	private static Random rand;
	
	  static
	  {
		  rand=new Random();
		  try {
			//userAgents = Files.readAllLines(Paths.get(ConfigData.configDirectory,ConfigData.useragents_listPropertiesFileName),
			 //         Charset.defaultCharset());
			  //static block is initilized before initilaizing variables is causing issue. Danger comment
			  userAgents = Files.readAllLines(Paths.get("/home/n0r00ij/rts/RTS/scrapper_config",ConfigData.useragents_listPropertiesFileName),
			          Charset.defaultCharset());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	  }
	  

	
	
	
	public static void main(String [] args) throws IOException, InterruptedException
	{
		//ConfigParams.initialzie();
		HttpUtilities test=new HttpUtilities();
		System.out.println(test.sendGet("https://pastebin.com/archive"));
		//Search.extractRegexMatches(test.sendGet("https://pastebin.com/archive"), "href=\"/(\\w{8})\">");
		//HttpUtilities.parse("href=\"/(\\w{8})\">", test.sendGet("https://pastebin.com/archive"));
		
	}
	
	
	
	public static String sendGet(String url) throws InterruptedException 
	{
		
		StringBuffer response =null;
		int numberofattempts=0;
		boolean recievedResponse=false;
		
		while(numberofattempts<5 && !recievedResponse)
		{
			
			try {
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("User-Agent", userAgents.get(rand.nextInt(userAgents.size())));
				//con.setRequestProperty("User-Agent", User_Agent);
				BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				String inputLine;
				response = new StringBuffer();
		
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				con.disconnect();
				recievedResponse=true;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					numberofattempts+=1;
					if(numberofattempts==5)
					{
						System.out.println("Tried "+numberofattempts+" times and could not fetch data for :"+url);
					}
					Thread.sleep(10000);
			}
		}
		//print result
		if(response!=null)
		{
		return response.toString();
		}
		else
		{
			return "Failed to fetch response for url :"+url;
		}
	}
	
}
