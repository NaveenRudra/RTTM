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
import org.rts.utilities.TruffleHog;
import org.sqlite.dataaccess.util.DaoUtil;


public class RedditImpl implements Scrapper{

	//implement array of searchterms needed here man
	private String baseurl;
	private String timetoSleep;
	private ArrayList<String> searchTerms=new ArrayList<String>();
	private String profile ="";
	private String trufflehogregex="false";
	private String trufflehogentropy="false";
	
	
	public void run() {
		// TODO Auto-generated method stub
		kickOffActualWork();
		
	}

	public void initScrapper(Properties prop) {
		this.baseurl=prop.getProperty("baseurl");
		this.timetoSleep=prop.getProperty("timetosleep");
		this.searchTerms=new ArrayList<String>(Arrays.asList(prop.getProperty("searchterms").split("\\s*,\\s*")));
		this.profile= prop.getProperty("profile");
		this.trufflehogregex=prop.getProperty("trufflehogregex").toLowerCase();
		this.trufflehogentropy=prop.getProperty("trufflehogentropy").toLowerCase();
		
	}

	public void kickOffActualWork() {
		while(true)
		{
		try {
			for(String searchterm:searchTerms)
			{
				Set<String> alertSet=JsonParserForReddit.redditUrlFetcher(baseurl.replace("{searchterm}", searchterm.replace(" ", "%20")));
				ArrayList<Thread> threads= new ArrayList<>();
				if(trufflehogregex.equals("true") || trufflehogentropy.equals("true"))
				{
					for(String url:alertSet)
					{
					    
					    {
						
							if(!DaoUtil.searchDuplicateByUrl(url))
							{
								System.out.println("Analyzing url************" + url);
								TruffleHog truffleHogThread = new TruffleHog();
								truffleHogThread.initilaize(url, searchterm,profile,trufflehogregex,trufflehogentropy);
								Thread t = new Thread(truffleHogThread);
								threads.add(t);							
								t.start();
							}
					    }  
					    
					}
				}
				
				else if(alertSet.size()>0)
				{
					Set<String> filteredalertSet = new HashSet<String>(); 
					for(String url:alertSet)
					{
						if(!DaoUtil.searchDuplicateByUrl(url))
						{
							//System.out.println("Comparing url" + url);
							filteredalertSet.add(url);
						}
					}
					EmailUtility.sendEmailUsingGmail(profile, filteredalertSet, searchterm);
					for(String url:filteredalertSet)
					{
						if(!DaoUtil.searchDuplicateByUrl(url))
						{
							ArrayList<String> temp=new ArrayList<String>();
							temp.add(searchterm);
							DbUtil.addNewEntry(temp, url,profile);
						
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
