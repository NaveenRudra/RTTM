package com.realtimescrapperpastie.realtimescrapperpastie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import com.realtimescrapper.base.Scrapper;
import com.realtimescrapper.utilities.EmailUtility;
import com.realtimescrapperpastie.utilities.JsonParserForReddit;


public class RedditImpl implements Scrapper{

	//implement array of searchterms needed here man
	private String baseurl;
	private String timetoSleep;
	private ArrayList<String> searchTerms=new ArrayList<String>();
	
	public void run() {
		// TODO Auto-generated method stub
		kickOffActualWork();
		
	}

	public void initScrapper(Properties prop) {
		this.baseurl=prop.getProperty("baseurl");
		this.timetoSleep=prop.getProperty("timetosleep");
		this.searchTerms=new ArrayList<String>(Arrays.asList(prop.getProperty("searchterms").split("\\s*,\\s*")));
		
	}

	public void kickOffActualWork() {
		while(true)
		{
		try {
			for(String searchterm:searchTerms)
			{
				Set<String> alertSet=JsonParserForReddit.redditUrlFetcher(baseurl.replace("{searchTerm}", searchterm));
				Iterator<String>  i = alertSet.iterator();
				while (i.hasNext())
				  {
					String name = (String) i.next();
				    //System.out.println(name);
				  }
				if(!(alertSet.size()==0))
				{
					//System.out.println("no diff found");
					EmailUtility.sendEmailUsingGmail("Reddit", alertSet, searchterm);
				}
			}
			Thread.sleep(Integer.parseInt(timetoSleep));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		 
		
	}

	public boolean stopScrapper() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean restart() {
		// TODO Auto-generated method stub
		return false;
	}

}
