package com.realtimescrapper.utilities;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;


import com.realtimescrapper.dao.entity.Result;
import com.realtimescrapper.dao.util.DaoUtil;
import com.relatimescrapper.pojo.Data;

import java.util.regex.Matcher;

public class Search 
{
	
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private static Date date = new Date();
	private static DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	private static Date dateobj = new Date();
	
	
	public static void find(Data data) throws IOException
	{
		SearchThread searchThread=new SearchThread();
		searchThread.initialize(data);
		new Thread(searchThread).start();
		/**
		String response=HttpUtilities.sendGet(data.getUrl());
		ArrayList<String> termsFound = new ArrayList<String>();
		for(String s:data.getSearchTerms())
		{
			if(response.contains(s))
			{
				termsFound.add(s);
			}
		}
		if(termsFound.size()>0)
		{
			//check if multiple threads are resulting in reading same data again and again over ok
  		    System.out.println(df.format(dateobj)+"found in **************************************************"+data.getUrl()+" data found is "+termsFound.get(0));**/				
			
  		    /**if(!DaoUtil.searchDuplicateByUrl(data.getUrl()))
			{
				EmailUtility.sendEmailUsingGmail("Later", data.getUrl(), termsFound);
				Result result = new Result();
				result.setSearchedTerms(termsFound);
	     		result.setSearchedtext("This is for future implementation");
				result.setUrl(data.getUrl());
				result.setTime(dateFormat.format(date).toString());
				DaoUtil.insert(result);
			}
		}**/
		//return termsFound;
	}
	
	public static ArrayList<String> extractRegexMatches(String response,String regex)
	{
		ArrayList<String> matches= new ArrayList<String>();
		Pattern pattern = Pattern.compile(regex,Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(response);
		while (matcher.find()) {
			matches.add(matcher.group(1));
		    }
		
		return matches;
	}
	


}
