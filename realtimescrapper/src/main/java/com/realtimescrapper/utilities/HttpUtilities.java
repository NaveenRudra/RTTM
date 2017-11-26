package com.realtimescrapper.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpUtilities {
	
	private String User_Agent="Mozilla/5.0";
	
	public static void main(String [] args) throws IOException
	{
		HttpUtilities test=new HttpUtilities();
		System.out.print(test.sendGet("https://yahoo.com"));
		
	}
	
	public String sendGet(String url) throws IOException
	{
		//check for 3 times for input also check for response code
		//
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", User_Agent);
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}
	
	
	public ArrayList<String> parse(String regex,String response)
	{
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(response);
		ArrayList<String> patternsFound= new ArrayList<String>();
		while(matcher.find())
		{
			 int start = matcher.start(0);
		     int end = matcher.end(0);
		     patternsFound.add(response.substring(start, end));
		     
		}
		return patternsFound;
	}
}
