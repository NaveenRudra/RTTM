package org.rts.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import org.apache.log4j.Logger;
import org.rts.base.Scrapper;
import org.kafkaparser.utilities.DbUtil;
import org.kafkaparser.utilities.EmailUtility;
import org.rts.utilities.JsonParserForGithub;
import org.rts.utilities.TruffleHog;
import org.sqlite.dataaccess.util.DaoUtil;

public class GithubImpl implements Scrapper {

	private String baseurl;
	private String access_token;
	private String timetoSleep;
	private ArrayList<String> searchTerms=new ArrayList<String>();
	final static Logger logger = Logger.getLogger(GithubImpl.class);
	private String profile ="";
	private String trufflehogregex="false";
	private String trufflehogentropy="false";
	
	public void run() {
		// TODO Auto-generated method stub
		kickOffActualWork();
		
	}

	public void initScrapper(Properties prop)
	{
		// TODO Auto-generated method stub
		this.baseurl=prop.getProperty("baseurl");
		this.access_token=prop.getProperty("access_token");
		this.timetoSleep=prop.getProperty("timetosleep");	
		this.searchTerms=new ArrayList<String>(Arrays.asList(prop.getProperty("searchterms").split("\\s*,\\s*")));
		this.profile= prop.getProperty("profile");
		this.trufflehogregex=prop.getProperty("trufflehogregex").toLowerCase();
		this.trufflehogentropy=prop.getProperty("trufflehogentropy").toLowerCase();
	}

	public void kickOffActualWork() {
		System.out.println("Kicked off github");
		
		
		while(true)
		{
			try {
				for(String searchterm:searchTerms)
				{
					Set<String> alertSet=JsonParserForGithub.githubUrlFetcher(baseurl.replace("{searchTerm}", searchterm.replace(" ", "%20"))+"&access_token="+access_token);
					System.out.println("Got url" + alertSet);
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
									//while(t.isAlive());//This is a bad idea waiting for every thread man
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
					
					
					
					
					/**if(filteredalertSet.size()>0)
					{
						//System.out.println("Reuqired terms have been found");
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
				    }**/
				}
				Thread.sleep(Integer.parseInt(timetoSleep));
			} catch (Exception e) {
				logger.error("kickOffActualWork function in GithubImpl class has thrown exception",e);
			}
			
			System.gc();
		
		}
		
	}

	public boolean stopScrapper() {
		// TODO Auto-generated method stub
		//enable multiple threads to speed up the process man
		return false;
	}

	public boolean restart() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static void main(String args[])
	{
		Thread t=new Thread(new GithubImpl());
		t.run();
	}

}
