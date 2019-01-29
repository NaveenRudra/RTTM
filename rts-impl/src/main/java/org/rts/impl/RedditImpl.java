package org.rts.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import org.rts.base.Scrapper;
import org.kafkaparser.utilities.DbUtil;
import org.kafkaparser.utilities.EmailUtility;
import org.rts.utilities.JsonParserForReddit;
import org.sqlite.dataaccess.util.DaoUtil;


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
				Set<String> alertSet=JsonParserForReddit.redditUrlFetcher(baseurl.replace("{searchterm}", searchterm.replace(" ", "%20")));
				Set<String> filteredalertSet = new HashSet<String>(); 
				
				for(String url:alertSet)
				{
					if(!DaoUtil.searchDuplicateByUrl(url))
					{
						filteredalertSet.add(url);
					}
				}
				if(!(filteredalertSet.size()==0))
				{
					//System.out.println("Reuqired terms have been found");
					EmailUtility.sendEmailUsingGmail("Reddit", filteredalertSet, searchterm);
					for(String url:filteredalertSet)
					{
						if(!DaoUtil.searchDuplicateByUrl(url))
						{
							ArrayList<String> temp=new ArrayList<String>();
							temp.add(searchterm);
							DbUtil.addNewEntry(temp, url);
						
					     }
				    }
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
